package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralBlock;
import net.minecraft.world.level.block.CoralPlantBlock;
import net.minecraft.world.level.block.CoralWallFanBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CoralBlock.class)
public class CoralBlockMixin extends Block {

    public CoralBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        var targetPos = pos.above();
        int rand = random.nextInt(4);
        Direction coralDir = Direction.from2DDataValue(rand);
        BlockPos coralPos = pos.relative(coralDir);
        BlockState testBlock = world.getBlockState(coralPos);
        Holder<Biome> j = world.getBiome(pos);
        if (j.is(Biomes.WARM_OCEAN)) {
            if (random.nextFloat() < 0.01f) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.CORALS))
                        .toList().size() <= 8) {
                    if (world.getBlockState(pos).is(Blocks.FIRE_CORAL_BLOCK)) {
                        if (random.nextFloat() < 0.5f) {
                            if (random.nextFloat() < 0.5f) {
                                if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                    world.setBlockAndUpdate(targetPos, Blocks.FIRE_CORAL.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                                }
                            }
                            else if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                world.setBlockAndUpdate(targetPos, Blocks.FIRE_CORAL_FAN.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        }
                        else if (testBlock.is(Blocks.WATER)) {
                            world.setBlock(coralPos, Blocks.FIRE_CORAL_WALL_FAN.defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, (coralDir)).setValue(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.UPDATE_CLIENTS);
                        }
                    }
                    if (world.getBlockState(pos).is(Blocks.HORN_CORAL_BLOCK)) {
                        if (random.nextFloat() < 0.5f) {
                            if (random.nextFloat() < 0.5f) {
                                if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                    world.setBlockAndUpdate(targetPos, Blocks.HORN_CORAL.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                                }
                            }
                            else if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                world.setBlockAndUpdate(targetPos, Blocks.HORN_CORAL_FAN.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        }
                        else if (testBlock.is(Blocks.WATER)) {
                            world.setBlock(coralPos, Blocks.HORN_CORAL_WALL_FAN.defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, (coralDir)).setValue(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.UPDATE_CLIENTS);
                        }
                    }
                    if (world.getBlockState(pos).is(Blocks.BRAIN_CORAL_BLOCK)) {
                        if (random.nextFloat() < 0.5f) {
                            if (random.nextFloat() < 0.5f) {
                                if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                    world.setBlockAndUpdate(targetPos, Blocks.BRAIN_CORAL.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                                }
                            }
                            else if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                world.setBlockAndUpdate(targetPos, Blocks.BRAIN_CORAL_FAN.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        }
                        else if (testBlock.is(Blocks.WATER)) {
                            world.setBlock(coralPos, Blocks.BRAIN_CORAL_WALL_FAN.defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, (coralDir)).setValue(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.UPDATE_CLIENTS);
                        }
                    }
                    if (world.getBlockState(pos).is(Blocks.BUBBLE_CORAL_BLOCK)) {
                        if (random.nextFloat() < 0.5f) {
                            if (random.nextFloat() < 0.5f) {
                                if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                    world.setBlockAndUpdate(targetPos, Blocks.BUBBLE_CORAL.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                                }
                            }
                            else if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                world.setBlockAndUpdate(targetPos, Blocks.BUBBLE_CORAL_FAN.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        }
                        else if (testBlock.is(Blocks.WATER)) {
                            world.setBlock(coralPos, Blocks.BUBBLE_CORAL_WALL_FAN.defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, (coralDir)).setValue(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.UPDATE_CLIENTS);
                        }
                    }
                    if (world.getBlockState(pos).is(Blocks.TUBE_CORAL_BLOCK)) {
                        if (random.nextFloat() < 0.5f) {
                            if (random.nextFloat() < 0.5f) {
                                if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                    world.setBlockAndUpdate(targetPos, Blocks.TUBE_CORAL.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                                }
                            }
                            else if (world.getBlockState(targetPos).is(Blocks.WATER)) {
                                world.setBlockAndUpdate(targetPos, Blocks.TUBE_CORAL_FAN.defaultBlockState().setValue(CoralPlantBlock.WATERLOGGED, Boolean.TRUE));
                            }
                        }
                        else if (testBlock.is(Blocks.WATER)) {
                            world.setBlock(coralPos, Blocks.TUBE_CORAL_WALL_FAN.defaultBlockState().setValue(BaseCoralWallFanBlock.FACING, (coralDir)).setValue(CoralWallFanBlock.WATERLOGGED, Boolean.TRUE), Block.UPDATE_CLIENTS);
                        }
                    }
                }
            }
        }
    }
}
