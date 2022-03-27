package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import java.util.List;
import java.util.Random;

public class ModGrassBlock extends GrassBlock implements Fertilizable {
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
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(state, world, pos)) {
            world.setBlockState(pos, Blocks.DIRT.getDefaultState());
        }
        if (state.isOf(Blocks.DIRT)) return;
        else if (world.getLightLevel(pos.up()) >= 9) {
            BlockState blockState = this.getDefaultState();
            for(int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockPos upPos = blockPos.up();
                BlockState upState = world.getBlockState(upPos);
                if ((world.getBlockState(blockPos).isOf(Blocks.DIRT) || (world.getBlockState(blockPos).isOf(Blocks.MYCELIUM))) && canSpread(blockState, world, blockPos) && (upState.isAir())) {
                    world.setBlockState(blockPos, blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
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
        if (state.get(FERTILE)) {
            if (state.isOf(Blocks.GRASS_BLOCK) && random.nextFloat() < 0.001f) {
                if (!world.isChunkLoaded(pos)) return;
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, world,
                        b -> b.isIn(ModTags.SMALL_PLANTS), 7)) {

                    BlockPos targetPos = pos.up();
                    BlockPos tallPos = targetPos.up();

                    RegistryEntry<Biome> biome = world.getBiome(pos);

                    if (world.getBlockState(targetPos).isIn(ModTags.GRASS_GROWTH_REPLACEABLE)) {
                        if (biome.matchesKey(BiomeKeys.PLAINS)) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            } else if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            } else if (random.nextFloat() < 0.001f) {
                                world.setBlockState(targetPos, Blocks.PUMPKIN.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.SUNFLOWER_PLAINS)) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            } else if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.2f) {
                                    world.setBlockState(targetPos, Blocks.SUNFLOWER.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.SUNFLOWER.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }

                        } else if (biome.matchesKey(BiomeKeys.SWAMP)) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BLUE_ORCHID.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.FLOWER_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.RED_TULIP.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.WHITE_TULIP.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ORANGE_TULIP.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.PINK_TULIP.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ALLIUM.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                world.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ROSE_BUSH.getDefaultState());
                                world.setBlockState(tallPos, Blocks.ROSE_BUSH.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            } else if (random.nextFloat() < 0.05f) {
                                world.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                            } else if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.02f) {
                                    world.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        } else if (biome.matchesKey(BiomeKeys.SWAMP)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.4f) {
                                world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ALLIUM.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            } else if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.TAIGA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.4f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            } else if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.SPRUCE_SAPLING.getDefaultState());
                            } else if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.SWEET_BERRY_BUSH.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.OLD_GROWTH_PINE_TAIGA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.4f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.4f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.BADLANDS)) {
                            if (random.nextFloat() > 0.6f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.DEAD_BUSH.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.ERODED_BADLANDS)) {
                            if (random.nextFloat() > 0.6f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.DEAD_BUSH.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.WOODED_BADLANDS)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.WINDSWEPT_SAVANNA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.SAVANNA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.SAVANNA_PLATEAU)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            } else if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                            } else if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        } else if (biome.matchesKey(BiomeKeys.WINDSWEPT_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            } else if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                            } else if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        } else if (biome.matchesKey(BiomeKeys.BIRCH_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            } else if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.BIRCH_SAPLING.getDefaultState());
                            } else if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        } else if (biome.matchesKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.005f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            } else if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        } else if (biome.matchesKey(BiomeKeys.DARK_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            } else if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.ROSE_BUSH.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.ROSE_BUSH.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                } else if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.CARVED_PUMPKIN.getDefaultState()
                                            .with(CarvedPumpkinBlock.FACING, Direction.Type.HORIZONTAL.random(random)));
                                } else if (random.nextFloat() < 0.0015f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.JACK_O_LANTERN.getDefaultState()
                                            .with(CarvedPumpkinBlock.FACING, Direction.Type.HORIZONTAL.random(random)));
                                }
                            }
                        } else if (biome.matchesKey(BiomeKeys.BAMBOO_JUNGLE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.BAMBOO_SAPLING.getDefaultState());
                            } else if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            } else if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_LEAVES.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.JUNGLE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.05f) {
                                world.setBlockState(targetPos, Blocks.MELON.getDefaultState());
                            } else if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            } else if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_SAPLING.getDefaultState());
                            } else if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_LEAVES.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.SPARSE_JUNGLE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.001f) {
                                world.setBlockState(targetPos, Blocks.MELON.getDefaultState());
                            } else if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            } else if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            } else if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_SAPLING.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.GROVE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                        } else if (biome.matchesKey(BiomeKeys.LUSH_CAVES)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            } else if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.AZALEA.getDefaultState());
                            } else if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.FLOWERING_AZALEA.getDefaultState());
                            }
                        } else if (random.nextFloat() > 0.1f) {
                            world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        }
                    }
                }
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