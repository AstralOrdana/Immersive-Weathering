package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Random;

public class CrackableWallBlock extends WallBlock implements Crackable{
    private final Crackable.CrackLevel crackLevel;

    public CrackableWallBlock(Crackable.CrackLevel crackLevel, BlockBehaviour.Properties settings) {
        super(settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        for (Direction direction : Direction.values()) {
            if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .anyMatch(Blocks.FIRE::equals)) {
                if (random.nextFloat() < 0.5F) {
                    this.applyChangeOverTime(state, world, pos, random);
                }
            }
            if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .filter(b->b.is(ModTags.CRACKABLE))
                    .toList().size() >= 20) {
                if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.is(ModTags.CRACKED))
                        .toList().size() <= 8) {
                    if (random.nextFloat() < 0.0000625F) {
                        this.applyChangeOverTime(state, world, pos, random);
                    }
                    if (world.getBlockState(pos.relative(direction)).is(ModTags.CRACKED)) {
                        if (random.nextFloat() < 0.02F) {
                            this.applyChangeOverTime(state, world, pos, random);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).isPresent();
    }

    @Override
    public CrackLevel getAge() {
        return this.crackLevel;
    }
}
