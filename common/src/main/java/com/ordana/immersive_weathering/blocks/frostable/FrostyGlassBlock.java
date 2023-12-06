package com.ordana.immersive_weathering.blocks.frostable;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class FrostyGlassBlock extends AbstractGlassBlock implements Frosty {

    public FrostyGlassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(NATURAL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NATURAL);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        tryUnFrost(state, level, pos);
    }

    @Override
    public boolean skipRendering(BlockState blockState, BlockState neighborState, Direction direction) {
        if (neighborState.is(Blocks.GLASS)) return true;
        return super.skipRendering(blockState, neighborState, direction);
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        if (neighborState.is(this) || neighborState.is(Blocks.GLASS)) return true;
        return false;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        InteractionResult success = interactWithPlayer(state, level, pos, player, hand);
        if (success != InteractionResult.PASS) return success;

        return super.use(state, level, pos, player, hand, hit);
    }

}
