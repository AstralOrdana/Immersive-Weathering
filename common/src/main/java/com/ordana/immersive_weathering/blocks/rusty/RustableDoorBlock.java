package com.ordana.immersive_weathering.blocks.rusty;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class RustableDoorBlock extends RustAffectedDoorBlock implements Rustable {


    public RustableDoorBlock(RustLevel rustLevel, Properties properties) {
        super(rustLevel, Rustable.setRandomTicking(properties, rustLevel));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
        this.tryWeather(state, serverLevel, pos, random);
    }

}