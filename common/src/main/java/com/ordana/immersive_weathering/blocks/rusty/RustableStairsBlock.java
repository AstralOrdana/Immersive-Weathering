package com.ordana.immersive_weathering.blocks.rusty;

import net.mehvahdjukaar.moonlight.api.block.ModStairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class RustableStairsBlock extends ModStairBlock implements Rustable {

    private final RustLevel rustLevel;

    public RustableStairsBlock(RustLevel rustLevel, Supplier<Block> baseBlockState, Properties settings) {
        super(baseBlockState, Rustable.setRandomTicking(settings, rustLevel));
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
