package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
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
        BlockState blockState = world.getBlockState(pos.up());
        if (blockState.isIn(BlockTags.DIRT)) {
            world.setBlockState(pos, state.getBlock().getDefaultState().with(PermafrostBlock.DIRTY, true));
        } else {
            world.setBlockState(pos, state.getBlock().getDefaultState().with(PermafrostBlock.DIRTY, false));
        }
    }

    public static boolean isWearingBoots(Entity entity) {
        return entity instanceof LivingEntity && ((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isOf(Items.LEATHER_BOOTS);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!isWearingBoots(entity) && entity instanceof LivingEntity) {
            entity.setFrozenTicks(300);
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
