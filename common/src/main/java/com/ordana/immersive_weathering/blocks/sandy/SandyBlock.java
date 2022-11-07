package com.ordana.immersive_weathering.blocks.sandy;


import com.ordana.immersive_weathering.blocks.charred.Charred;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class SandyBlock extends Block implements Sandy {

    public SandyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SANDINESS, 0).setValue(SAND_AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SANDINESS, SAND_AGE);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        this.spawnParticles(state, level, pos, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Sandy.super.randomTick(state, level, pos, random);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (interactWithPlayer(state, level, pos, player, hand, hit)) {
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbour, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Optional<BlockState> unSandy = Sandy.getUnSandy(state);
        BlockState neighborState = level.getBlockState(neighborPos);
        if (neighborState.getFluidState().is(Fluids.FLOWING_WATER) && unSandy.isPresent()) {
            ItemStack stack = new ItemStack(ModBlocks.SAND_LAYER_BLOCK.get());
            Containers.dropItemStack((Level) level, pos.getX(), pos.getY(), pos.getZ(), stack);
            return unSandy.get();
        }

        return super.updateShape(state, direction, neighbour, level, pos, neighborPos);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        Optional<BlockState> unSandy = Sandy.getUnSandy(state);
        BlockState neighborState = level.getBlockState(neighborPos);
        if (neighborState.getFluidState().is(Fluids.FLOWING_WATER) && unSandy.isPresent()) {
            level.setBlockAndUpdate(pos, unSandy.get());
            ItemStack stack = new ItemStack(ModBlocks.SAND_LAYER_BLOCK.get());
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        }

        super.neighborChanged(state, level, pos, block, neighborPos, isMoving);
    }
}