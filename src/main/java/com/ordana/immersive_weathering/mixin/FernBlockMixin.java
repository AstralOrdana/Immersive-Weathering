package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(FernBlock.class)
public class FernBlockMixin extends Block {

    public FernBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var targetPos = pos.up();
        if (world.getBlockState(pos).isOf(Blocks.GRASS)) {
            if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 3, world, b -> b.isOf(Blocks.TALL_GRASS), 2)) {
                    if (random.nextFloat() < 0.001f) {
                        world.setBlockState(pos, Blocks.TALL_GRASS.getDefaultState());
                        world.setBlockState(targetPos, Blocks.TALL_GRASS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        }
        if (world.getBlockState(pos).isOf(Blocks.FERN)) {
            if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 3, world, b -> b.isOf(Blocks.LARGE_FERN), 2)) {
                    if (random.nextFloat() < 0.001f) {
                        world.setBlockState(pos, Blocks.LARGE_FERN.getDefaultState());
                        world.setBlockState(targetPos, Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                    }
                }
            }
        }
    }
}
