package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LayerLightEngine;
import java.util.List;
import java.util.Random;


public class RootedGrassBlock extends ModGrassBlock implements BonemealableBlock, IConditionalGrowingBlock {
    public RootedGrassBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    //TODO: check this whole class
    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {

        WeatheringHelper.growHangingRoots(world, random, pos);

        BlockPos blockPos = pos.above();
        BlockState blockState = Blocks.GRASS.defaultBlockState();
        world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(FERTILE, true));
        label46:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos2 = blockPos;

            for(int j = 0; j < i / 16; ++j) {
                blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!world.getBlockState(blockPos2.below()).is(this) || world.getBlockState(blockPos2).isCollisionShapeFullBlock(world, blockPos2)) {
                    continue label46;
                }
            }

            BlockState blockState2 = world.getBlockState(blockPos2);
            if (blockState2.is(blockState.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock)blockState.getBlock()).performBonemeal(world, random, blockPos2, blockState2);
            }

            if (blockState2.isAir()) {
                Holder registryEntry;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = ((Biome)world.getBiome(blockPos2).value()).getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    registryEntry = ((RandomPatchConfiguration)((ConfiguredFeature)list.get(0)).config()).feature();
                } else {
                    registryEntry = VegetationPlacements.GRASS_BONEMEAL;
                }

                ((PlacedFeature)registryEntry.value()).place(world, world.getChunkSource().getGenerator(), random, blockPos2);
            }
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return canBeGrass(state, world, pos) && !world.getFluidState(blockPos).is(FluidTags.WATER);
    }

    private static boolean canBeGrass(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LayerLightEngine.getLightBlockInto(world, state, pos, blockState, blockPos, Direction.UP, blockState.getLightBlock(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.getValue(FERTILE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(FERTILE)) {
            if (!canBeGrass(state, world, pos)) {
                world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
            if (state.is(Blocks.DIRT)) return;
            else if (world.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockState = this.defaultBlockState();
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if ((world.getBlockState(blockPos).is(Blocks.DIRT) || (world.getBlockState(blockPos).is(Blocks.MYCELIUM))) && canPropagate(blockState, world, blockPos)) {
                        world.setBlockAndUpdate(blockPos, Blocks.GRASS_BLOCK.defaultBlockState().setValue(SNOWY, world.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    }
                    else if ((world.getBlockState(blockPos).is(Blocks.ROOTED_DIRT)) && canPropagate(blockState, world, blockPos)) {
                        world.setBlockAndUpdate(blockPos, this.defaultBlockState().setValue(SNOWY, world.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    }
                }
            }
        }
        //fire turns this to dirt
        //gets the block again because we are injecting at tail and it could already be dirt
        state = world.getBlockState(pos);
        if (state.is(Blocks.DIRT)) return;
        if (world.random.nextFloat() < 0.1f) {
            if (!world.hasChunkAt(pos)) return;
            if (WeatheringHelper.hasEnoughBlocksFacingMe(pos, world, b -> b.is(BlockTags.FIRE), 1)) {
                world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                return;
            }
        }
    }
}
