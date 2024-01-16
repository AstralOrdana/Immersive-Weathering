package com.ordana.immersive_weathering.blocks.rusty;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RustableBlock extends Block implements Rustable {

    private final RustLevel rustLevel;

    public RustableBlock(RustLevel rustLevel, Properties settings) {
        super(Rustable.setRandomTicking(settings, rustLevel));
        this.rustLevel = rustLevel;
    }

    @Override
    public RustLevel getAge() {
        return this.rustLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        this.tryWeather(state, serverLevel, pos, random);
    }

}
