package com.ordana.immersive_weathering.common.blocks.soil;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class PermafrostBlock extends Block {

    public PermafrostBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(DIRTY, false));
    }

    public static final BooleanProperty DIRTY = BooleanProperty.create("dirty");

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        BlockState upState = world.getBlockState(pos.above());
        BlockState downState = world.getBlockState(pos.below());
        if (upState.is(BlockTags.DIRT)) {
            world.setBlockAndUpdate(pos, ModBlocks.PERMAFROST.defaultBlockState().setValue(PermafrostBlock.DIRTY, true));
        }
        if (downState.is(BlockTags.DIRT)) {
            world.setBlockAndUpdate(pos.below(), ModBlocks.CRYOSOL.defaultBlockState());
        }
    }

    public static boolean isWearingBoots(Entity entity) {
        return entity instanceof LivingEntity && ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS);
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, (LivingEntity) entity) > 0 || isWearingBoots(entity) || entity.getType() == EntityType.FOX || entity.getType() == EntityType.RABBIT || entity.getType() == EntityType.SHEEP || entity.getType() == EntityType.STRAY || entity.getType() == EntityType.GOAT) {
            return;
        }
        if (ImmersiveWeathering.getConfig().fireAndIceConfig.permafrostFreezing) {
            entity.setTicksFrozen(300);
        }

        super.stepOn(world, pos, state, entity);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP ? state.setValue(DIRTY, isDirt(neighborState)) : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().above());
        return this.defaultBlockState().setValue(DIRTY, isDirt(blockState));
    }

    private static boolean isDirt(BlockState state) {
        return state.is(BlockTags.DIRT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(DIRTY);
    }
}
