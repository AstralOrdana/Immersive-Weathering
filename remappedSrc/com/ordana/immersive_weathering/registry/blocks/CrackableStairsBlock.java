package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Random;

public class CrackableStairsBlock extends StairsBlock implements Crackable{
    private final Crackable.CrackLevel crackLevel;

    public CrackableStairsBlock(Crackable.CrackLevel crackLevel, BlockState baseBlockState, AbstractBlock.Settings settings) {
        super(baseBlockState, settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.offset(direction);
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .anyMatch(Blocks.FIRE::equals)) {
                float f = 0.5F;
                if (random.nextFloat() < 0.5F) {
                    this.tryDegrade(state, world, pos, random);
                }
            }
            if (BlockPos.streamOutwards(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .map(BlockState::getBlock)
                    .filter(ModTags.CRACKABLE::contains)
                    .toList().size() >= 20) {
                if (BlockPos.streamOutwards(pos, 2, 2, 2)
                        .map(world::getBlockState)
                        .map(BlockState::getBlock)
                        .filter(ModTags.CRACKED::contains)
                        .toList().size() <= 8) {
                    float f = 0.0000625F;
                    if (random.nextFloat() < 0.0000625F) {
                        this.tryDegrade(state, world, pos, random);
                    }
                    if (world.getBlockState(pos.offset(direction)).isIn(ModTags.CRACKED)) {
                        float g = 0.02F;
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
