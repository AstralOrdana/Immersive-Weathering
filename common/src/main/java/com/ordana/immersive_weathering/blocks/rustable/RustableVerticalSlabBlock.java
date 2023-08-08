package com.ordana.immersive_weathering.blocks.rustable;

import net.mehvahdjukaar.moonlight.api.block.VerticalSlabBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class RustableVerticalSlabBlock extends VerticalSlabBlock implements Rustable {

    private final RustLevel rustLevel;

    public RustableVerticalSlabBlock(Rustable.RustLevel rustLevel, Properties properties) {
        super(properties);
        this.rustLevel = rustLevel;
    }

    @Override
    public RustLevel getAge() {
        return this.rustLevel;
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
