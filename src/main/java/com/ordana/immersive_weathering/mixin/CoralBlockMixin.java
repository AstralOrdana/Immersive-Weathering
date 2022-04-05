package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CoralBlock.class)
public abstract class CoralBlockMixin extends Block {

    public CoralBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.01f) {
            if (world.getBiome(pos).value().getBaseTemperature() > 0.45) {
                if (!world.isAreaLoaded(pos, 2)) return;

                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 2, world,
                        b -> b.is(BlockTags.CORALS) || b.getBlock() instanceof SpongeBlock, 6)) {

                    var coralGroup = WeatheringHelper.getCoralGrowth(state);
                    coralGroup.ifPresent(c -> {

                        Direction coralDir = WeatheringHelper.ROOT_DIRECTIONS.getRandomValue(random).get();
                        BlockPos targetPos = pos.relative(coralDir);
                        BlockState targetState = world.getBlockState(targetPos);
                        if (targetState.is(Blocks.WATER)) {
                            if (random.nextFloat() > 0.005) {
                                world.setBlockAndUpdate(targetPos, Blocks.WET_SPONGE.defaultBlockState());
                            } else if (coralDir == Direction.UP) {
                                Block b = random.nextFloat() < 0.5f ? c.coral() : c.fan();
                                world.setBlockAndUpdate(targetPos, b.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, true));
                            } else {
                                world.setBlockAndUpdate(targetPos, c.wallFan().defaultBlockState()
                                        .setValue(BaseCoralWallFanBlock.FACING, (coralDir))
                                        .setValue(CoralWallFanBlock.WATERLOGGED, true));
                            }
                        }
                    });
                }
            }
        }
    }
}
