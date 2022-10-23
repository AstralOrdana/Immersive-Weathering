package com.ordana.immersive_weathering.blocks.rustable;

import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.mehvahdjukaar.moonlight.api.block.ModStairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import net.minecraft.util.RandomSource;
import java.util.function.Supplier;

public class RustableStairsBlock extends ModStairBlock implements Rustable {

    private final RustLevel rustLevel;

    public RustableStairsBlock(RustLevel rustLevel, Supplier<Block> baseBlockState, Properties settings) {
        super(baseBlockState, settings);
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
