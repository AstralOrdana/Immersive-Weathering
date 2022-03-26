package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.ModTags;
import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biomes;
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
            if (world.getBiome(pos).is(Biomes.WARM_OCEAN)) {
                if (!world.isAreaLoaded(pos, 2)) return;
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 2, world, b -> b.is(ModTags.CORALS), 6)) {

                    //TODO: fix something here
                    var coralGroup = WeatheringHelper.getCoralGrowth(state);
                    coralGroup.ifPresent(c -> {

                        int rand = random.nextInt(4);
                        Direction coralDir = Direction.from2DDataValue(rand);
                        var abovePos = pos.above();
                        BlockPos sidePos = pos.relative(coralDir);

                        BlockState sideBlock = world.getBlockState(sidePos);
                        BlockState aboveBlock = world.getBlockState(abovePos);

                        if (random.nextFloat() < 0.5f) {
                            if (aboveBlock.is(Blocks.WATER)) {
                                Block b = random.nextFloat() < 0.5f ? c.coral() : c.fan();
                                world.setBlockAndUpdate(abovePos, b.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, true));
                            }
                        } else if (sideBlock.is(Blocks.WATER)) {
                            world.setBlockAndUpdate(sidePos, Blocks.FIRE_CORAL_WALL_FAN.defaultBlockState()
                                    .setValue(BaseCoralWallFanBlock.FACING, (coralDir))
                                    .setValue(CoralWallFanBlock.WATERLOGGED, true));
                        }
                    });
                }
            }
        }
    }
}
