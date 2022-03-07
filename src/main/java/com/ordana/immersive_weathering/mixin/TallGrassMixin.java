package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TallGrassBlock.class)
public abstract class TallGrassMixin extends Block {

    public TallGrassMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {

        //TODO: finish this
        if (state.is(Blocks.GRASS)) {
            if (random.nextFloat() < 0.001f) {
                if (!world.isAreaLoaded(pos, 4)) return;
                var targetPos = pos.above();
                if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 4, 4, world,
                            b -> b.is(Blocks.TALL_GRASS), 1)) {

                        world.setBlockAndUpdate(pos, Blocks.TALL_GRASS.defaultBlockState());
                        world.setBlockAndUpdate(targetPos, Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        } else if (state.is(Blocks.FERN)) {
            if (random.nextFloat() < 0.001f) {
                if (!world.isAreaLoaded(pos, 4)) return;
                var targetPos = pos.above();
                if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 4, 4, world,
                            b -> b.is(Blocks.LARGE_FERN), 1)) {

                        world.setBlockAndUpdate(pos, Blocks.LARGE_FERN.defaultBlockState());
                        world.setBlockAndUpdate(targetPos, Blocks.LARGE_FERN.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        }
    }
}
