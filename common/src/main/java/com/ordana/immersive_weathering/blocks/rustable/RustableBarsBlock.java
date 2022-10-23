package com.ordana.immersive_weathering.blocks.rustable;

import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import net.minecraft.util.RandomSource;

public class RustableBarsBlock extends IronBarsBlock implements Rustable {
    private final RustLevel rustLevel;

    public RustableBarsBlock(RustLevel rustLevel, Properties settings) {
        super(settings);
        this.rustLevel = rustLevel;
    }

    @Override
    public RustLevel getAge() {
        return this.rustLevel;
    }

}
