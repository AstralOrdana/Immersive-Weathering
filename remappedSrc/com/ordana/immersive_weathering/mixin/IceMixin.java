package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Mixin(IceBlock.class)
public class IceMixin extends Block {
    public IceMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockPos icePos = pos.down();
        if (random.nextFloat() < 0.01f) {
            if (world.getBlockState(icePos).isOf(Blocks.AIR)) {
                if (world.getFluidState(pos.up()).isOf(Fluids.WATER) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                    world.setBlockState(icePos, ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN).with(IcicleBlock.THICKNESS, Thickness.TIP));
                }
                Optional<RegistryKey<Biome>> j = world.getBiomeKey(pos);
                if (Objects.equals(j, Optional.of(BiomeKeys.JAGGED_PEAKS)) || Objects.equals(j, Optional.of(BiomeKeys.FROZEN_PEAKS)) || Objects.equals(j, Optional.of(BiomeKeys.ICE_SPIKES)) || Objects.equals(j, Optional.of(BiomeKeys.SNOWY_SLOPES)) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                    world.setBlockState(icePos, ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN).with(IcicleBlock.THICKNESS, Thickness.TIP));
                }
            }
        }
        Optional<RegistryKey<Biome>> j = world.getBiomeKey(pos);
        if ((world.getRegistryKey() == World.NETHER) || ((Objects.equals(j, Optional.of(BiomeKeys.DESERT)) || Objects.equals(j, Optional.of(BiomeKeys.BADLANDS)) || Objects.equals(j, Optional.of(BiomeKeys.ERODED_BADLANDS)) || Objects.equals(j, Optional.of(BiomeKeys.WOODED_BADLANDS)) || Objects.equals(j, Optional.of(BiomeKeys.SAVANNA_PLATEAU)) || Objects.equals(j, Optional.of(BiomeKeys.SAVANNA)) || Objects.equals(j, Optional.of(BiomeKeys.WINDSWEPT_SAVANNA))) && world.isDay())) {
            this.melt(state, world, pos);
        }
    }

    public void melt(BlockState state, World world, BlockPos pos) {
        if (world.getDimension().isUltrawarm()) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            world.updateNeighbor(pos, Blocks.WATER, pos);
        }
    }
}
