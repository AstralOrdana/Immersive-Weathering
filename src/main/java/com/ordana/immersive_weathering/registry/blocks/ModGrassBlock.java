package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import java.util.List;
import java.util.Random;

public class ModGrassBlock extends GrassBlock implements Fertilizable, IConditionalGrowingBlock {
    public static final BooleanProperty FERTILE = BooleanProperty.of("fertile");

    public ModGrassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FERTILE, true).with(SNOWY, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FERTILE);
        builder.add(SNOWY);
    }

    private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW) && blockState.get(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.contains(FERTILE) && state.get(FERTILE) && super.hasRandomTicks(state);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.get(FERTILE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(FERTILE)) {
            if (!canSurvive(state, world, pos)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
            if (state.isOf(Blocks.DIRT)) return;
            else if (world.getLightLevel(pos.up()) >= 9) {
                BlockState blockState = this.getDefaultState();
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if ((world.getBlockState(blockPos).isOf(Blocks.DIRT) || (world.getBlockState(blockPos).isOf(Blocks.MYCELIUM))) && canSpread(blockState, world, blockPos)) {
                        world.setBlockState(blockPos, blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                    }
                    else if ((world.getBlockState(blockPos).isOf(Blocks.ROOTED_DIRT)) && canSpread(blockState, world, blockPos)) {
                        world.setBlockState(blockPos, ModBlocks.ROOTED_GRASS_BLOCK.getDefaultState().with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                    }
                }
            }
        }
        //fire turns this to dirt
        //gets the block again because we are injecting at tail and it could already be dirt
        state = world.getBlockState(pos);
        if (state.isOf(Blocks.DIRT)) return;
        if (world.random.nextFloat() < 0.1f) {
            if (!world.isChunkLoaded(pos)) return;
            if (WeatheringHelper.hasEnoughBlocksFacingMe(pos, world, b -> b.isIn(BlockTags.FIRE), 1)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState blockState = Blocks.GRASS.getDefaultState();
        world.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState().with(FERTILE, true));
        label46:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos2 = blockPos;

            for(int j = 0; j < i / 16; ++j) {
                blockPos2 = blockPos2.add(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!world.getBlockState(blockPos2.down()).isOf(this) || world.getBlockState(blockPos2).isFullCube(world, blockPos2)) {
                    continue label46;
                }
            }

            BlockState blockState2 = world.getBlockState(blockPos2);
            if (blockState2.isOf(blockState.getBlock()) && random.nextInt(10) == 0) {
                ((Fertilizable)blockState.getBlock()).grow(world, random, blockPos2, blockState2);
            }

            if (blockState2.isAir()) {
                RegistryEntry registryEntry;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = ((Biome)world.getBiome(blockPos2).value()).getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    registryEntry = ((RandomPatchFeatureConfig)((ConfiguredFeature)list.get(0)).config()).feature();
                } else {
                    registryEntry = VegetationPlacedFeatures.GRASS_BONEMEAL;
                }

                ((PlacedFeature)registryEntry.value()).generateUnregistered(world, world.getChunkManager().getChunkGenerator(), random, blockPos2);
            }
        }
    }
}