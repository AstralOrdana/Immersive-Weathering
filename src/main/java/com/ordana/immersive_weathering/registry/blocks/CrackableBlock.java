package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.RandomChance;
import com.ordana.immersive_weathering.registry.ModTags;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CrackableBlock extends Block implements Crackable{
    private final Crackable.CrackLevel crackLevel;

    public CrackableBlock(Crackable.CrackLevel crackLevel, BlockBehaviour.Properties settings) {
        super(settings);
        this.crackLevel = crackLevel;
    }

    public static void spreadWeathering(BlockState state, ServerLevel level, BlockPos pos, Random random) {

        RandomChance chance = new RandomChance();

        //checks all blocks in a square around this
        BlockPos.withinManhattanStream(pos, 1, 1, 1)
                .map(level::getBlockState)
                .filter(b -> b.is(ModTags.CRACKED))
                .toList().size() < 5;
        if(condition){
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockState neighbor = level.getBlockState(neighborPos);
                if (random.nextFloat() < 0.5F) {
                    CRACKED_BLOCKS.forEach((solid, cracked) -> {
                        if (neighbor.isOf(solid)) {
                            world.setBlockState(neighborPos, cracked.getStateWithProperties(neighbor));
                        }
                    });
                }
            }
        }
    }





    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        //why is it looping through all directions

        AtomicDouble chance = new AtomicDouble(0);

        for (Direction direction : Direction.values()) {
            if (world.getBlockState(pos.relative(direction)).is(ModTags.CRACKED)) {
                chance.getAndAdd(0.02);
            }
        }

        var blocks = BlockPos.withinManhattanStream(pos, 2, 2, 2)
                .map(world::getBlockState);

        blocks.forEach(b->{
            if(b.is(BlockTags.FIRE)){
                chance.getAndAdd(0.5);
            }
            else if(b.is(ModTags.CRACKABLE)){
                chance.getAndAdd(0.0001);
            }
            else if(b.is(ModTags.CRACKED)){
                chance.getAndAdd(-0.0001);
            }

        });

        if (random.nextFloat() < chance.get()) {
            this.applyChangeOverTime(state, world, pos, random);
        }

        //this means it wants to have at least 20 crackable & 8 cracked
        if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                .map(world::getBlockState)
                .filter(b->b.is(ModTags.CRACKABLE))
                .toList().size() >= 20) {
            if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                    .map(world::getBlockState)
                    .filter(b->b.is(ModTags.CRACKED))
                    .toList().size() <= 8) {
                if (random.nextFloat() < 0.0000625F) {
                    //thing is this calls does the same calculation as we are doing here...
                    this.applyChangeOverTime(state, world, pos, random);
                }

            }
        }

    }


    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Crackable.getIncreasedCrackBlock(state.getBlock()).isPresent();
    }

    @Override
    public Crackable.CrackLevel getAge() {
        return this.crackLevel;
    }
}
