package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Random;

public class RootedGrassBlock extends ModGrassBlock implements Fertilizable {
    public RootedGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Direction rootDir = Direction.values()[1 + random.nextInt(5)].getOpposite();
        BlockPos rootPos = pos.offset(rootDir);
        BlockState targetState = world.getBlockState(rootPos);
        BlockState toPlace = Blocks.HANGING_ROOTS.getDefaultState();
        if(targetState.isOf(Blocks.WATER)) {
            toPlace = toPlace.with(ModHangingRootsBlock.WATERLOGGED, true);
        }
        else if(!targetState.isAir())return;
        if (rootDir == Direction.DOWN) {
            world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.HANGING, true), 3);
        }
        else {
            world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.FACING, (rootDir)).with(ModHangingRootsBlock.HANGING, Boolean.FALSE), 3);
        }
        BlockPos blockPos = pos.up();
        BlockState blockState = Blocks.GRASS.getDefaultState();
        world.setBlockState(pos, ModBlocks.ROOTED_GRASS_BLOCK.getDefaultState().with(FERTILE, true));
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

    private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
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

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(FERTILE)) {
            BlockGrowthHandler.tickBlock(state, world, pos);
            if (!canSurvive(state, world, pos)) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
            if (state.isOf(Blocks.DIRT)) return;
            else if (world.getLightLevel(pos.up()) >= 9) {
                BlockState blockState = this.getDefaultState();
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if ((world.getBlockState(blockPos).isOf(Blocks.DIRT) || (world.getBlockState(blockPos).isOf(Blocks.MYCELIUM))) && canSpread(blockState, world, blockPos)) {
                        world.setBlockState(blockPos, Blocks.GRASS_BLOCK.getDefaultState().with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
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
                return;
            }
        }
    }
}
