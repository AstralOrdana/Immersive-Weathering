package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class CrackableWallBlock extends WallBlock implements Crackable{
    private final Crackable.CrackLevel crackLevel;

    public CrackableWallBlock(Crackable.CrackLevel crackLevel, AbstractBlock.Settings settings) {
        super(settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        for (Direction direction : Direction.values()) {
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .anyMatch(Blocks.FIRE::equals)) {
                if (random.nextFloat() < 0.5F) {
                    this.tryDegrade(state, world, pos, random);
                }
            }
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .filter(b->b.isIn(ModTags.CRACKABLE))
                    .toList().size() >= 20) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .filter(b->b.isIn(ModTags.CRACKED))
                        .toList().size() <= 8) {
                    if (random.nextFloat() < 0.0000625F) {
                        this.tryDegrade(state, world, pos, random);
                    }
                    if (world.getBlockState(pos.offset(direction)).isIn(ModTags.CRACKED)) {
                        if (random.nextFloat() < 0.02F) {
                            this.tryDegrade(state, world, pos, random);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).isPresent();
    }

    @Override
    public CrackLevel getDegradationLevel() {
        return this.crackLevel;
    }
}
