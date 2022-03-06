package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SpreadingSnowyDirtBlock.class)
public class SpreadableBlockMixin extends Block {
    public SpreadableBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.above();
        var tallPos = targetPos.above();
        if (BlockPos.withinManhattanStream(pos, 1, 1, 1)
                .map(world::getBlockState)
                .map(BlockState::getBlock)
                .anyMatch(Blocks.FIRE::equals)) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());
            }
        }
        if (world.getBlockState(pos).is(Blocks.GRASS_BLOCK)) {
            if (BlockPos.withinManhattanStream(pos, 5, 5, 5)
                    .map(world::getBlockState)
                    .filter(b->b.is(ModTags.SMALL_PLANTS))
                    .toList().size() <= 25) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    var biome = world.getBiome(pos);
                    Holder<Biome> j = world.getBiome(pos);
                    if (world.getBlockState(targetPos).is(Blocks.AIR) || world.getBlockState(targetPos).is(ModBlocks.ASH_BLOCK) || world.getBlockState(targetPos).is(ModBlocks.SOOT)) {
                        if (j.is(Biomes.PLAINS)) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.OXEYE_DAISY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.001f) {
                                world.setBlockAndUpdate(targetPos, Blocks.PUMPKIN.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.SUNFLOWER_PLAINS)) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.OXEYE_DAISY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                            }
                            if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                                if (random.nextFloat() < 0.2f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.SUNFLOWER.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.SUNFLOWER.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }

                        }
                        else if (j.is(Biomes.SWAMP)) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BLUE_ORCHID.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.FLOWER_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.RED_TULIP.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.WHITE_TULIP.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ORANGE_TULIP.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.PINK_TULIP.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.LILY_OF_THE_VALLEY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ALLIUM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.LILAC.defaultBlockState());
                                world.setBlockAndUpdate(tallPos, Blocks.LILAC.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ROSE_BUSH.defaultBlockState());
                                world.setBlockAndUpdate(tallPos, Blocks.ROSE_BUSH.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                            if (random.nextFloat() < 0.05f) {
                                world.setBlockAndUpdate(targetPos, Blocks.OAK_SAPLING.defaultBlockState());
                            }
                            if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                                if (random.nextFloat() < 0.02f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.PEONY.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.PEONY.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (j.is(Biomes.SWAMP)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.4f) {
                                world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ALLIUM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.OXEYE_DAISY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.TAIGA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockAndUpdate(targetPos, Blocks.SPRUCE_SAPLING.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockAndUpdate(targetPos, Blocks.SWEET_BERRY_BUSH.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.OLD_GROWTH_PINE_TAIGA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.RED_MUSHROOM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.OLD_GROWTH_SPRUCE_TAIGA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.RED_MUSHROOM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.BADLANDS)) {
                            if (random.nextFloat() > 0.6f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockAndUpdate(targetPos, Blocks.DEAD_BUSH.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.ERODED_BADLANDS)) {
                            if (random.nextFloat() > 0.6f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockAndUpdate(targetPos, Blocks.DEAD_BUSH.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.WOODED_BADLANDS)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.WINDSWEPT_SAVANNA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.SAVANNA)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.SAVANNA_PLATEAU)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockAndUpdate(targetPos, Blocks.ACACIA_SAPLING.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.LILY_OF_THE_VALLEY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.OAK_SAPLING.defaultBlockState());
                            }
                            if (world.getBlockState(tallPos).is(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.PEONY.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.PEONY.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (j.is(Biomes.WINDSWEPT_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.LILY_OF_THE_VALLEY.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.AZURE_BLUET.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.OAK_SAPLING.defaultBlockState());
                            }
                            if (world.getBlockState(tallPos).is(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.PEONY.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.PEONY.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (j.is(Biomes.BIRCH_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BIRCH_SAPLING.defaultBlockState());
                            }
                            if (world.getBlockState(tallPos).is(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.LILAC.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.LILAC.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (j.is(Biomes.OLD_GROWTH_BIRCH_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.005f) {
                                world.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, ModBlocks.WEEDS.defaultBlockState().setValue(CropBlock.AGE, BlockStateProperties.MAX_AGE_7));
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.CORNFLOWER.defaultBlockState());
                            }
                            if (world.getBlockState(tallPos).is(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.LILAC.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.LILAC.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (j.is(Biomes.DARK_FOREST)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.RED_MUSHROOM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.POPPY.defaultBlockState());
                            }
                            if (world.getBlockState(tallPos).is(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.ROSE_BUSH.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.ROSE_BUSH.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.NORTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.SOUTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.EAST));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.WEST));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.NORTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.SOUTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.EAST));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockAndUpdate(targetPos, Blocks.DARK_OAK_FENCE.defaultBlockState());
                                    world.setBlockAndUpdate(tallPos, Blocks.JACK_O_LANTERN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, Direction.WEST));
                                }
                            }
                        }
                        else if (j.is(Biomes.BAMBOO_JUNGLE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.BAMBOO_SAPLING.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockAndUpdate(targetPos, Blocks.JUNGLE_LEAVES.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.JUNGLE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.05f) {
                                world.setBlockAndUpdate(targetPos, Blocks.MELON.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.JUNGLE_SAPLING.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockAndUpdate(targetPos, Blocks.JUNGLE_LEAVES.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.SPARSE_JUNGLE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.001f) {
                                world.setBlockAndUpdate(targetPos, Blocks.MELON.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockAndUpdate(targetPos, Blocks.DANDELION.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockAndUpdate(targetPos, Blocks.JUNGLE_SAPLING.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.GROVE)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FERN.defaultBlockState());
                            }
                        }
                        else if (j.is(Biomes.LUSH_CAVES)) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockAndUpdate(targetPos, Blocks.AZALEA.defaultBlockState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockAndUpdate(targetPos, Blocks.FLOWERING_AZALEA.defaultBlockState());
                            }
                        }
                        else if (random.nextFloat() > 0.1f) {
                            world.setBlockAndUpdate(targetPos, Blocks.GRASS.defaultBlockState());
                        }
                    }
                }
            }
            if (world.getBlockState(pos).is(Blocks.MYCELIUM)) {
                if (BlockPos.withinManhattanStream(pos, 5, 5, 5)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.SMALL_MUSHROOMS))
                        .toList().size() <= 3) {
                    float f = 0.5f;
                    if (random.nextFloat() < 0.001f) {
                        if (world.getBlockState(targetPos).is(Blocks.AIR)) {
                            if (random.nextFloat() > 0.5f) {
                                world.setBlockAndUpdate(targetPos, Blocks.RED_MUSHROOM.defaultBlockState());
                            } else {
                                world.setBlockAndUpdate(targetPos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }
}
