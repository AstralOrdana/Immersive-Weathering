package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Mixin(CoralBlockBlock.class)
public class CoralBlockMixin extends Block {
    public CoralBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var targetPos = pos.up();
        int rand = random.nextInt(4);
        Direction coralDir = Direction.fromHorizontal(rand);
        BlockPos coralPos = pos.offset(coralDir);
        BlockState testBlock = world.getBlockState(coralPos);
        Optional<RegistryKey<Biome>> j = world.getBiomeKey(pos);
        if (Objects.equals(j, Optional.of(BiomeKeys.WARM_OCEAN))) {
            if (random.nextFloat() < 0.001f) {
                if (world.getBlockState(pos).isOf(Blocks.FIRE_CORAL_BLOCK)) {
                    if (BlockPos.streamOutwards(pos, 4, 4, 4)
                            .map(world::getBlockState)
                            .map(BlockState::getBlock)
                            .filter(ImmersiveWeathering.CORALS::contains)
                            .toList().size() <= 8) {
                        if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.FIRE_CORAL.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.FIRE_CORAL_FAN.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (testBlock.isOf(Blocks.WATER)) {
                                world.setBlockState(coralPos, Blocks.FIRE_CORAL_WALL_FAN.getDefaultState().with(DeadCoralWallFanBlock.FACING, (coralDir)).with(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.NOTIFY_LISTENERS);
                            }
                        }
                    }
                }
                if (world.getBlockState(pos).isOf(Blocks.HORN_CORAL_BLOCK)) {
                    if (BlockPos.streamOutwards(pos, 4, 4, 4)
                            .map(world::getBlockState)
                            .map(BlockState::getBlock)
                            .filter(ImmersiveWeathering.CORALS::contains)
                            .toList().size() <= 8) {
                        if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.HORN_CORAL.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.HORN_CORAL_FAN.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (testBlock.isOf(Blocks.WATER)) {
                                world.setBlockState(coralPos, Blocks.HORN_CORAL_WALL_FAN.getDefaultState().with(DeadCoralWallFanBlock.FACING, (coralDir)).with(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.NOTIFY_LISTENERS);
                            }
                        }
                    }
                }
                if (world.getBlockState(pos).isOf(Blocks.BUBBLE_CORAL_BLOCK)) {
                    if (BlockPos.streamOutwards(pos, 4, 4, 4)
                            .map(world::getBlockState)
                            .map(BlockState::getBlock)
                            .filter(ImmersiveWeathering.CORALS::contains)
                            .toList().size() <= 8) {
                        if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.BUBBLE_CORAL.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.BUBBLE_CORAL_FAN.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (testBlock.isOf(Blocks.WATER)) {
                                world.setBlockState(coralPos, Blocks.BUBBLE_CORAL_WALL_FAN.getDefaultState().with(DeadCoralWallFanBlock.FACING, (coralDir)).with(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.NOTIFY_LISTENERS);
                            }
                        }
                    }
                }
                if (world.getBlockState(pos).isOf(Blocks.BRAIN_CORAL_BLOCK)) {
                    if (BlockPos.streamOutwards(pos, 4, 4, 4)
                            .map(world::getBlockState)
                            .map(BlockState::getBlock)
                            .filter(ImmersiveWeathering.CORALS::contains)
                            .toList().size() <= 8) {
                        if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.BRAIN_CORAL.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.BRAIN_CORAL_FAN.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (testBlock.isOf(Blocks.WATER)) {
                                world.setBlockState(coralPos, Blocks.BRAIN_CORAL_WALL_FAN.getDefaultState().with(DeadCoralWallFanBlock.FACING, (coralDir)).with(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.NOTIFY_LISTENERS);
                            }
                        }
                    }
                }
                if (world.getBlockState(pos).isOf(Blocks.TUBE_CORAL_BLOCK)) {
                    if (BlockPos.streamOutwards(pos, 4, 4, 4)
                            .map(world::getBlockState)
                            .map(BlockState::getBlock)
                            .filter(ImmersiveWeathering.CORALS::contains)
                            .toList().size() <= 8) {
                        if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.TUBE_CORAL.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (world.getBlockState(targetPos).isOf(Blocks.WATER)) {
                                world.setBlockState(targetPos, Blocks.TUBE_CORAL_FAN.getDefaultState().with(CoralBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        } else if (random.nextFloat() < 0.4f) {
                            if (testBlock.isOf(Blocks.WATER)) {
                                world.setBlockState(coralPos, Blocks.TUBE_CORAL_WALL_FAN.getDefaultState().with(DeadCoralWallFanBlock.FACING, (coralDir)).with(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.NOTIFY_LISTENERS);
                            }
                        }
                    }
                }
            }
        }
    }
}
