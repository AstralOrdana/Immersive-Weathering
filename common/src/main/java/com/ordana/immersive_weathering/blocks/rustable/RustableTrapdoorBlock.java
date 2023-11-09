package com.ordana.immersive_weathering.blocks.rustable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RustableTrapdoorBlock extends RustAffectedTrapdoorBlock implements Rustable {


    public RustableTrapdoorBlock(RustLevel rustLevel, Properties properties) {
        super(rustLevel, properties, BlockSetType.IRON);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return Rustable.getIncreasedRustBlock(state.getBlock()).isPresent();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        this.tryWeather(state, serverLevel, pos, random);
    }
}
