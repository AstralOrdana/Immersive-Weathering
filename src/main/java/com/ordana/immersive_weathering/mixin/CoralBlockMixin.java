package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CoralBlockBlock.class)
public abstract class CoralBlockMixin extends Block {

    public CoralBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.01f) {
            if (world.getBiome(pos).matchesKey(BiomeKeys.WARM_OCEAN)) {
                if (!world.isChunkLoaded(pos)) return;
                if (WeatheringHelper.hasEnoughBlocksAround(pos, 2, world, b -> b.isIn(ModTags.CORALS), 6)) {

                    var coralGroup = WeatheringHelper.getCoralGrowth(state);
                    coralGroup.ifPresent(c -> {

                        int rand = random.nextInt(4);
                        Direction coralDir = Direction.fromHorizontal(rand);
                        var abovePos = pos.up();
                        BlockPos sidePos = pos.offset(coralDir);

                        BlockState sideBlock = world.getBlockState(sidePos);
                        BlockState aboveBlock = world.getBlockState(abovePos);

                        if (random.nextFloat() < 0.5f) {
                            if (aboveBlock.isOf(Blocks.WATER)) {
                                Block b = random.nextFloat() < 0.5f ? c.coral() : c.fan();
                                world.setBlockState(abovePos, b.getDefaultState().with(CoralBlock.WATERLOGGED, true));
                            }
                        } else if (sideBlock.isOf(Blocks.WATER)) {
                            world.setBlockState(sidePos, Blocks.FIRE_CORAL_WALL_FAN.getDefaultState()
                                    .with(DeadCoralWallFanBlock.FACING, (coralDir))
                                    .with(CoralWallFanBlock.WATERLOGGED, true));
                        }
                    });
                }
            }
        }
    }
}
