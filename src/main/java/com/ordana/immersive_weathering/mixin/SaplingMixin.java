package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Mixin(SaplingBlock.class)
public class SaplingMixin extends Block {
    public SaplingMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        Optional<RegistryKey<Biome>> j = world.getBiomeKey(pos);
        if (Objects.equals(j, Optional.of(BiomeKeys.DESERT))) {
            if (world.random.nextFloat() < 0.08f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.BADLANDS))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.ERODED_BADLANDS))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.WOODED_BADLANDS))) {
            if (world.random.nextFloat() < 0.01f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.NETHER_WASTES))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.CRIMSON_FOREST))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.BASALT_DELTAS))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.WARPED_FOREST))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.SOUL_SAND_VALLEY))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            }
        }
    }
}
