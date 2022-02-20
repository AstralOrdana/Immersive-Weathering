package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Mixin(SpreadableBlock.class)
public class SpreadableBlockMixin extends Block {
    public SpreadableBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.up();
        var tallPos = targetPos.up();
        if (BlockPos.streamOutwards(pos, 1, 1, 1)
                .map(world::getBlockState)
                .map(BlockState::getBlock)
                .anyMatch(Blocks.FIRE::equals)) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
        }
        if (world.getBlockState(pos).isOf(Blocks.GRASS_BLOCK)) {
            if (BlockPos.streamOutwards(pos, 5, 5, 5)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(ImmersiveWeathering.SMALL_PLANTS::contains)
                    .toList().size() <= 25) {
                float f = 0.5f;
                if (random.nextFloat() < 0.001f) {
                    Optional<RegistryKey<Biome>> j = world.getBiomeKey(pos);
                    if (world.getBlockState(targetPos).isOf(Blocks.AIR) || world.getBlockState(targetPos).isOf(ModBlocks.ASH_BLOCK) || world.getBlockState(targetPos).isOf(ModBlocks.SOOT)) {
                        if (Objects.equals(j, Optional.of(BiomeKeys.PLAINS))) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            }
                            if (random.nextFloat() < 0.001f) {
                                world.setBlockState(targetPos, Blocks.PUMPKIN.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.SUNFLOWER_PLAINS))) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            }
                            if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.2f) {
                                    world.setBlockState(targetPos, Blocks.SUNFLOWER.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.SUNFLOWER.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }

                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.SWAMP))) {
                            if (random.nextFloat() > 0.2f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BLUE_ORCHID.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.FLOWER_FOREST))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.RED_TULIP.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.WHITE_TULIP.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ORANGE_TULIP.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.PINK_TULIP.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ALLIUM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                world.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ROSE_BUSH.getDefaultState());
                                world.setBlockState(tallPos, Blocks.ROSE_BUSH.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                            }
                            if (random.nextFloat() < 0.05f) {
                                world.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                            }
                            if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.02f) {
                                    world.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.MEADOW))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.ALLIUM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.OXEYE_DAISY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            }
                            if (random.nextFloat() < 0.02f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.TAIGA))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.4f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.SPRUCE_SAPLING.getDefaultState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.SWEET_BERRY_BUSH.getDefaultState());
                            }
                        }
                        else  if (Objects.equals(j, Optional.of(BiomeKeys.OLD_GROWTH_PINE_TAIGA))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.4f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.4f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.BADLANDS))) {
                            if (random.nextFloat() > 0.6f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.ERODED_BADLANDS))) {
                            if (random.nextFloat() > 0.6f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.WOODED_BADLANDS))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.WINDSWEPT_SAVANNA))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.SAVANNA))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.SAVANNA_PLATEAU))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.04f) {
                                world.setBlockState(targetPos, Blocks.ACACIA_SAPLING.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.FOREST))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                            }
                            if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.WINDSWEPT_FOREST))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.LILY_OF_THE_VALLEY.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.AZURE_BLUET.getDefaultState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.OAK_SAPLING.getDefaultState());
                            }
                            if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.PEONY.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.PEONY.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.BIRCH_FOREST))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.BIRCH_SAPLING.getDefaultState());
                            }
                            if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.OLD_GROWTH_BIRCH_FOREST))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.CORNFLOWER.getDefaultState());
                            }
                            if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.LILAC.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.LILAC.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.DARK_FOREST))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.POPPY.getDefaultState());
                            }
                            if (world.getBlockState(tallPos).isOf(Blocks.AIR)) {
                                if (random.nextFloat() < 0.01f) {
                                    world.setBlockState(targetPos, Blocks.ROSE_BUSH.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.ROSE_BUSH.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.NORTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.SOUTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.EAST));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.WEST));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.JACK_O_LANTERN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.NORTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.JACK_O_LANTERN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.SOUTH));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.JACK_O_LANTERN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.EAST));
                                }
                                if (random.nextFloat() < 0.0025f) {
                                    world.setBlockState(targetPos, Blocks.DARK_OAK_FENCE.getDefaultState());
                                    world.setBlockState(tallPos, Blocks.JACK_O_LANTERN.getDefaultState().with(CarvedPumpkinBlock.FACING, Direction.WEST));
                                }
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.BAMBOO_JUNGLE))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.BAMBOO_SAPLING.getDefaultState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                            if (random.nextFloat() < 0.01f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_LEAVES.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.JUNGLE))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.05f) {
                                world.setBlockState(targetPos, Blocks.MELON.getDefaultState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_SAPLING.getDefaultState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_LEAVES.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.SPARSE_JUNGLE))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.001f) {
                                world.setBlockState(targetPos, Blocks.MELON.getDefaultState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.DANDELION.getDefaultState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.JUNGLE_SAPLING.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.GROVE))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.3f) {
                                world.setBlockState(targetPos, Blocks.FERN.getDefaultState());
                            }
                        }
                        else if (Objects.equals(j, Optional.of(BiomeKeys.LUSH_CAVES))) {
                            if (random.nextFloat() > 0.4f) {
                                world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                            }
                            if (random.nextFloat() < 0.2f) {
                                world.setBlockState(targetPos, Blocks.AZALEA.getDefaultState());
                            }
                            if (random.nextFloat() < 0.1f) {
                                world.setBlockState(targetPos, Blocks.FLOWERING_AZALEA.getDefaultState());
                            }
                        }
                        else if (random.nextFloat() > 0.1f) {
                            world.setBlockState(targetPos, Blocks.GRASS.getDefaultState());
                        }
                    }
                }
            }
            if (world.getBlockState(pos).isOf(Blocks.MYCELIUM)) {
                if (BlockPos.streamOutwards(pos, 5, 5, 5)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ImmersiveWeathering.SMALL_MUSHROOMS::contains)
                        .toList().size() <= 3) {
                    float f = 0.5f;
                    if (random.nextFloat() < 0.001f) {
                        if (world.getBlockState(targetPos).isOf(Blocks.AIR)) {
                            if (random.nextFloat() > 0.5f) {
                                world.setBlockState(targetPos, Blocks.RED_MUSHROOM.getDefaultState());
                            } else {
                                world.setBlockState(targetPos, Blocks.BROWN_MUSHROOM.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }
}
