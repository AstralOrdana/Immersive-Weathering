package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.block_growth.IBlockGrowth;
import com.ordana.immersive_weathering.block_growth.TickSource;
import com.ordana.immersive_weathering.common.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.List;

public class GrassGrowth implements IBlockGrowth {

    @Override
    public Collection<TickSource> getTickSources() {
        return List.of(TickSource.BLOCK_TICK);
    }

    @Override
    public Iterable<Block> getOwners() {
        return List.of();
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        /*
        Random random = level.getRandom();
        if ((level.getLightEmission(pos.above()) >= 9) && (level.getBlockState(pos.above()).is(Blocks.AIR))) {
            BlockState blockState = state.getBlock().defaultBlockState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (level.getBlockState(blockPos).is(Blocks.GRASS_BLOCK) || level.getBlockState(blockPos).is(Blocks.MYCELIUM) &&
                        ((SpreadingSnowyDirtBlock) state.getBlock()).canPropagate(blockState, level, blockPos)) {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(SpreadingSnowyDirtBlock.SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    return;
                }
            }
        }*/

        //fire turns this to dirt
        //gets the block again because we are injecting at tail and it could already be dirt

        //TODO: this can be added and converted to data
        if (level.random.nextFloat() < 0.1f) {
            if (!level.isAreaLoaded(pos, 1)) return;
            if (WeatheringHelper.hasEnoughBlocksFacingMe(pos, level, b -> b.is(BlockTags.FIRE), 1)) {
                level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
        }
    }


}
