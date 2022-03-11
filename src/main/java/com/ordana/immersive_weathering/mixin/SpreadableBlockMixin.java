package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SpreadableBlock.class)
public abstract class SpreadableBlockMixin extends Block {
    public SpreadableBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random, CallbackInfo ci) {
        //fire turns this to dirt
        //gets the block again because we are injecting at tail and it could already be dirt
        state = level.getBlockState(pos);
        if (state.isOf(Blocks.DIRT)) return;
        if (level.random.nextFloat() < 0.1f) {
            if (!level.isChunkLoaded(pos)) return;
            if (WeatheringHelper.hasEnoughBlocksFacingMe(pos, level, b -> b.isIn(BlockTags.FIRE), 1)) {
                level.setBlockState(pos, Blocks.DIRT.getDefaultState());
                return;
            }
        }
        if (state.isOf(Blocks.GRASS_BLOCK) && random.nextFloat() < 0.001f) {
            if (!level.isChunkLoaded(pos)) return;
            if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, level,
                    b -> b.isIn(ModTags.SMALL_PLANTS), 7)) {

                BlockPos targetPos = pos.up();
                BlockPos tallPos = targetPos.up();

                RegistryEntry<Biome> biome = level.getBiome(pos);

                if (level.getBlockState(targetPos).isIn(ModTags.GRASS_GROWTH_REPLACEABLE)) {
                    if (biome.matchesKey(BiomeKeys.PLAINS)) {
                        if (random.nextFloat() > 0.2f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                        } else if (random.nextFloat() < 0.001f) {
                            level.setBlockState(targetPos, Blocks.PUMPKIN.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.SUNFLOWER_PLAINS)) {
                        if (random.nextFloat() > 0.2f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                        } else if (level.getBlockState(targetPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() < 0.2f) {
                                level.setBlockState(targetPos, Blocks.SUNFLOWER.getDefaultState());
                                level.setBlockState(tallPos, Blocks.SUNFLOWER.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }

                    } else if (biome.matchesKey(BiomeKeys.SWAMP)) {
                        if (random.nextFloat() > 0.2f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        }
                        if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.BLUE_ORCHID.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.FLOWER_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.RED_TULIP.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.WHITE_TULIP.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.ORANGE_TULIP.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.PINK_TULIP.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.ALLIUM.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                            level.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.ROSE_BUSH.getDefaultState());
                            level.setBlockState(tallPos, Blocks.ROSE_BUSH.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                        } else if (random.nextFloat() < 0.05f) {
                            level.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                        } else if (level.getBlockState(targetPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() < 0.02f) {
                                level.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                level.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.matchesKey(BiomeKeys.SWAMP)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.ALLIUM.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.TAIGA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockState(targetPos, Blocks.SPRUCE_SAPLING.getDefaultState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockState(targetPos, Blocks.SWEET_BERRY_BUSH.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.OLD_GROWTH_PINE_TAIGA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.BADLANDS)) {
                        if (random.nextFloat() > 0.6f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockState(targetPos, Blocks.DEAD_BUSH.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.ERODED_BADLANDS)) {
                        if (random.nextFloat() > 0.6f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockState(targetPos, Blocks.DEAD_BUSH.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.WOODED_BADLANDS)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.WINDSWEPT_SAVANNA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.SAVANNA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.SAVANNA_PLATEAU)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                        } else if (level.getBlockState(tallPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                level.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.matchesKey(BiomeKeys.WINDSWEPT_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                        } else if (level.getBlockState(tallPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                level.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.matchesKey(BiomeKeys.BIRCH_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockState(targetPos, Blocks.BIRCH_SAPLING.getDefaultState());
                        } else if (level.getBlockState(tallPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                level.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.matchesKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.005f) {
                            level.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState().with(CropBlock.AGE, Properties.AGE_7_MAX));
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                        } else if (level.getBlockState(tallPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                level.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.matchesKey(BiomeKeys.DARK_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                        } else if (level.getBlockState(tallPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockState(targetPos, Blocks.ROSE_BUSH.getDefaultState());
                                level.setBlockState(tallPos, Blocks.ROSE_BUSH.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            } else if (random.nextFloat() < 0.0025f) {
                                level.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                level.setBlockState(tallPos, Blocks.CARVED_PUMPKIN.getDefaultState()
                                        .with(CarvedPumpkinBlock.FACING, Direction.Type.HORIZONTAL.random(random)));
                            } else if (random.nextFloat() < 0.0015f) {
                                level.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                level.setBlockState(tallPos, Blocks.JACK_O_LANTERN.getDefaultState()
                                        .with(CarvedPumpkinBlock.FACING, Direction.Type.HORIZONTAL.random(random)));
                            }
                        }
                    } else if (biome.matchesKey(BiomeKeys.BAMBOO_JUNGLE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockState(targetPos, Blocks.BAMBOO_SAPLING.getDefaultState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockState(targetPos, Blocks.JUNGLE_LEAVES.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.JUNGLE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.05f) {
                            level.setBlockState(targetPos, Blocks.MELON.getDefaultState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockState(targetPos, Blocks.JUNGLE_SAPLING.getDefaultState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockState(targetPos, Blocks.JUNGLE_LEAVES.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.SPARSE_JUNGLE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.001f) {
                            level.setBlockState(targetPos, Blocks.MELON.getDefaultState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockState(targetPos, Blocks.JUNGLE_SAPLING.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.GROVE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                        }
                    } else if (biome.matchesKey(BiomeKeys.LUSH_CAVES)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockState(targetPos, Blocks.AZALEA.getDefaultState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockState(targetPos, Blocks.FLOWERING_AZALEA.getDefaultState());
                        }
                    } else if (random.nextFloat() > 0.1f) {
                        level.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                    }
                }
            }
        } else if (state.isOf(Blocks.MYCELIUM)) {
            BlockPos targetPos = pos.up();
            if (random.nextFloat() < 0.001f && level.getBlockState(targetPos).isAir()) {
                if (!level.isChunkLoaded(pos)) return;
                if (WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 3, level,
                        b -> b.isIn(ModTags.SMALL_MUSHROOMS), 2)) {

                    level.setBlockState(targetPos, (random.nextFloat() > 0.5f ?
                            Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM).getDefaultState());

                }
            }
        }
    }
}
