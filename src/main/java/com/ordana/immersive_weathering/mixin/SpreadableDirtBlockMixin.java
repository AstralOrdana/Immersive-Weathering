package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.ModTags;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SpreadingSnowyDirtBlock.class)
public abstract class SpreadableDirtBlockMixin extends Block {

    @Shadow
    private static boolean canPropagate(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
        return false;
    }

    public SpreadableDirtBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci) {

        state = level.getBlockState(pos);
        if (state.is(Blocks.DIRT)) return;

        if ((level.getLightEmission(pos.above()) >= 9) && (level.getBlockState(pos.above()).is(Blocks.AIR))) {
            BlockState blockState = this.defaultBlockState();
            for(int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (level.getBlockState(blockPos).is(Blocks.GRASS_BLOCK) || level.getBlockState(blockPos).is(Blocks.MYCELIUM) && canPropagate(blockState, level, blockPos)) {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(SpreadingSnowyDirtBlock.SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    return;
                }
            }
        }

        //fire turns this to dirt
        //gets the block again because we are injecting at tail and it could already be dirt

        if (level.random.nextFloat() < 0.1f) {
            if (!level.isAreaLoaded(pos, 1)) return;
            if (WeatheringHelper.hasEnoughBlocksFacingMe(pos, level, b -> b.is(BlockTags.FIRE), 1)) {
                level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
                return;
            }
        }




        if (state.is(Blocks.GRASS_BLOCK) && random.nextFloat() < 0.001f) {
            if (!level.isAreaLoaded(pos, 4)) return;
            if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, level,
                    b -> b.is(ModTags.SMALL_PLANTS), 7)) {

                BlockPos targetPos = pos.above();
                BlockPos tallPos = targetPos.above();

                Holder<Biome> biome = level.getBiome(pos);

                if (level.getBlockState(targetPos).is(ModTags.GRASS_GROWTH_REPLACEABLE)) {
                    if (biome.is(Biomes.PLAINS)) {
                        if (random.nextFloat() > 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.get().defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.OXEYE_DAISY.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                        } else if (random.nextFloat() < 0.001f) {
                            level.setBlockAndUpdate(targetPos, Blocks.PUMPKIN.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.SUNFLOWER_PLAINS)) {
                        if (random.nextFloat() > 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.OXEYE_DAISY.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                        } else if (level.getBlockState(targetPos).is(Blocks.AIR)) {
                            if (random.nextFloat() < 0.2f) {
                                level.setBlockAndUpdate(targetPos, Blocks.SUNFLOWER.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.SUNFLOWER.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }

                    } else if (biome.is(Biomes.SWAMP)) {
                        if (random.nextFloat() > 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        }
                        if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BLUE_ORCHID.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.FLOWER_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.RED_TULIP.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.WHITE_TULIP.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ORANGE_TULIP.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.PINK_TULIP.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.LILY_OF_THE_VALLEY.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ALLIUM.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.LILAC.defaultBlockState());
                            level.setBlockAndUpdate(tallPos, Blocks.LILAC.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ROSE_BUSH.defaultBlockState());
                            level.setBlockAndUpdate(tallPos, Blocks.ROSE_BUSH.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                        } else if (random.nextFloat() < 0.05f) {
                            level.setBlockAndUpdate(targetPos, Blocks.OAK_SAPLING.defaultBlockState());
                        } else if (level.getBlockState(targetPos).is(Blocks.AIR)) {
                            if (random.nextFloat() < 0.02f) {
                                level.setBlockAndUpdate(targetPos, Blocks.PEONY.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.PEONY.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.is(Biomes.SWAMP)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.get().defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ALLIUM.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.OXEYE_DAISY.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                        } else if (random.nextFloat() < 0.02f) {
                            level.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.TAIGA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockAndUpdate(targetPos, Blocks.SPRUCE_SAPLING.defaultBlockState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockAndUpdate(targetPos, Blocks.SWEET_BERRY_BUSH.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.OLD_GROWTH_PINE_TAIGA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.RED_MUSHROOM.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.OLD_GROWTH_SPRUCE_TAIGA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.RED_MUSHROOM.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.BADLANDS)) {
                        if (random.nextFloat() > 0.6f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockAndUpdate(targetPos, Blocks.DEAD_BUSH.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.ERODED_BADLANDS)) {
                        if (random.nextFloat() > 0.6f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockAndUpdate(targetPos, Blocks.DEAD_BUSH.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.WOODED_BADLANDS)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.WINDSWEPT_SAVANNA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.SAVANNA)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.SAVANNA_PLATEAU)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.04f) {
                            level.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.LILY_OF_THE_VALLEY.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.OAK_SAPLING.defaultBlockState());
                        } else if (level.getBlockState(tallPos).is(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockAndUpdate(targetPos, Blocks.PEONY.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.PEONY.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.is(Biomes.WINDSWEPT_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.LILY_OF_THE_VALLEY.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.OAK_SAPLING.defaultBlockState());
                        } else if (level.getBlockState(tallPos).is(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockAndUpdate(targetPos, Blocks.PEONY.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.PEONY.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.is(Biomes.BIRCH_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.get().defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BIRCH_SAPLING.defaultBlockState());
                        } else if (level.getBlockState(tallPos).is(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockAndUpdate(targetPos, Blocks.LILAC.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.LILAC.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.is(Biomes.OLD_GROWTH_BIRCH_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.005f) {
                            level.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.get().defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                        } else if (level.getBlockState(tallPos).is(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockAndUpdate(targetPos, Blocks.LILAC.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.LILAC.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                        }
                    } else if (biome.is(Biomes.DARK_FOREST)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.RED_MUSHROOM.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                        } else if (level.getBlockState(tallPos).is(Blocks.AIR)) {
                            if (random.nextFloat() < 0.01f) {
                                level.setBlockAndUpdate(targetPos, Blocks.ROSE_BUSH.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.ROSE_BUSH.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            } else if (random.nextFloat() < 0.0025f) {
                                level.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.CARVED_PUMPKIN.defaultBlockState()
                                        .setValue(CarvedPumpkinBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
                            } else if (random.nextFloat() < 0.0015f) {
                                level.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                level.setBlockAndUpdate(tallPos, Blocks.JACK_O_LANTERN.defaultBlockState()
                                        .setValue(CarvedPumpkinBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random)));
                            }
                        }
                    } else if (biome.is(Biomes.BAMBOO_JUNGLE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.BAMBOO_SAPLING.defaultBlockState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                        } else if (random.nextFloat() < 0.01f) {
                            level.setBlockAndUpdate(targetPos, Blocks.JUNGLE_LEAVES.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.JUNGLE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.05f) {
                            level.setBlockAndUpdate(targetPos, Blocks.MELON.defaultBlockState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.JUNGLE_SAPLING.defaultBlockState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockAndUpdate(targetPos, Blocks.JUNGLE_LEAVES.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.SPARSE_JUNGLE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.001f) {
                            level.setBlockAndUpdate(targetPos, Blocks.MELON.defaultBlockState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockAndUpdate(targetPos, Blocks.JUNGLE_SAPLING.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.GROVE)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.3f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                        }
                    } else if (biome.is(Biomes.LUSH_CAVES)) {
                        if (random.nextFloat() > 0.4f) {
                            level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        } else if (random.nextFloat() < 0.2f) {
                            level.setBlockAndUpdate(targetPos, Blocks.AZALEA.defaultBlockState());
                        } else if (random.nextFloat() < 0.1f) {
                            level.setBlockAndUpdate(targetPos, Blocks.FLOWERING_AZALEA.defaultBlockState());
                        }
                    } else if (random.nextFloat() > 0.1f) {
                        level.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                    }
                }
            }
        } else if (state.is(Blocks.MYCELIUM)) {
            BlockPos targetPos = pos.above();
            if (random.nextFloat() < 0.001f && level.getBlockState(targetPos).isAir()) {
                if (!level.isAreaLoaded(pos, 4)) return;
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 2, level,
                        b -> b.is(ModTags.SMALL_MUSHROOMS), 2)) {

                    level.setBlockAndUpdate(targetPos, (random.nextFloat() > 0.5f ?
                            Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM).defaultBlockState());
                }
            }
        }
    }


}
