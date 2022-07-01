package com.ordana.immersive_weathering.reg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class LeafPilesRegistry {

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.empty();
    }

    public static Optional<SimpleParticleType> getFallenLeafParticle(BlockState state) {
        return Optional.empty();
    }
}
