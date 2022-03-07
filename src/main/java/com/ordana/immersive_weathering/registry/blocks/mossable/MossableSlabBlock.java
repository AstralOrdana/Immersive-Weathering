package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class MossableSlabBlock extends SlabBlock implements Mossable{

    public MossableSlabBlock(Mossable.MossLevel mossLevel, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WEATHERABLE, false));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld serverWorld, BlockPos pos, Random random) {
        float weatherChance = 0.1f;
        if (random.nextFloat() < weatherChance) {
            var opt = this.getDegradationResult(state);
            opt.ifPresent(b -> serverWorld.setBlockState(pos, b, 3));
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Mossable.getIncreasedMossBlock(state.getBlock()).isPresent();
    }

    @Override
    public float getDegradationChanceMultiplier() {
        return 0;
    }

    //-----weathereable-start---


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        super.appendProperties(stateManager);
        stateManager.add(WEATHERABLE);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        if (world instanceof ServerWorld serverWorld) {
            boolean weathering = this.shouldStartWeathering(state, pos, serverWorld);
            if (state.get(WEATHERABLE) != weathering) {
                //update weathering state
                serverWorld.setBlockState(pos, state.with(WEATHERABLE, weathering), 3);
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        if (state != null) {
            boolean weathering = this.shouldStartWeathering(state, ctx.getBlockPos(), ctx.getWorld());
            state = state.with(WEATHERABLE, weathering);
        }
        return state;
    }

    @Override
    public float getInterestForDirection() {
        return 0.5f;
    }

    @Override
    public float getHighInterestChance() {
        return 0.5f;
    }

    @Override
    public boolean isWeatherable(BlockState state) {
        return state.get(WEATHERABLE);
    }

    @Override
    public WeatheringAgent getWeatheringEffect(BlockState state, World world, BlockPos pos) {
        if (world.getBlockState(pos).isIn(ModTags.MOSS_SOURCE)) {
            return WeatheringAgent.WEATHER;
        }
        return WeatheringAgent.NONE;
    }

    @Override
    public Mossable.MossLevel getDegradationLevel() {
        return MossLevel.UNAFFECTED;
    }

    //-----weathereable-end---

}

