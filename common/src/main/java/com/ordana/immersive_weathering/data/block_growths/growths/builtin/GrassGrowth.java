package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrassGrowth extends BuiltinBlockGrowth {


    public GrassGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
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

        //TODO: this can be added and converted to data. ALso add this to fire growth instead
        if (level.random.nextFloat() < 0.1f) {
            if (!PlatformHelper.isAreaLoaded(level,pos, 1)) return;
            if (WeatheringHelper.hasEnoughBlocksFacingMe(pos, level, b -> b.is(BlockTags.FIRE), 1)) {
                level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
        }
    }


}
