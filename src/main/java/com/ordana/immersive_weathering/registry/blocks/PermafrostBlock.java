package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class PermafrostBlock extends Block {

    public PermafrostBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(DIRTY, false));
    }

    public static final BooleanProperty DIRTY = BooleanProperty.of("dirty");

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState upState = world.getBlockState(pos.up());
        BlockState downState = world.getBlockState(pos.down());
        if (upState.isIn(BlockTags.DIRT)) {
            world.setBlockState(pos, ModBlocks.PERMAFROST.getDefaultState().with(PermafrostBlock.DIRTY, true));
        }
        if (downState.isIn(BlockTags.DIRT)) {
            world.setBlockState(pos.down(), ModBlocks.CRYOSOL.getDefaultState());
        }
    }

    public static boolean isWearingBoots(Entity entity) {
        return entity instanceof LivingEntity && ((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isOf(Items.LEATHER_BOOTS);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEquipmentLevel(Enchantments.FROST_WALKER, (LivingEntity) entity) > 0 || isWearingBoots(entity) || entity.getType() == EntityType.VILLAGER || entity.getType().isIn(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES) || entity.getType() == EntityType.FOX || entity.getType() == EntityType.RABBIT || entity.getType() == EntityType.SHEEP || entity.getType() == EntityType.STRAY || entity.getType() == EntityType.GOAT) {
            return;
        }
        if (ImmersiveWeathering.getConfig().fireAndIceConfig.permafrostFreezing) {
            entity.setFrozenTicks(ImmersiveWeathering.getConfig().fireAndIceConfig.freezingPermafrostSeverity);
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP ? state.with(DIRTY, isDirt(neighborState)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().up());
        return this.getDefaultState().with(DIRTY, isDirt(blockState));
    }

    private static boolean isDirt(BlockState state) {
        return state.isIn(BlockTags.DIRT);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(DIRTY);
    }
}
