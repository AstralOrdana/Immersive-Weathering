package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
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

@Mixin(FarmlandBlock.class)
public class FarmlandMixin extends Block {
    public FarmlandMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("TAIL"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        var targetPos = pos.up();
        if (BlockPos.streamOutwards(pos, 2, 2, 2)
                .map(world::getBlockState)
                .map(BlockState::getBlock)
                .filter(ModBlocks.WEEDS::equals)
                .toList().size() <= 9) {
            if (random.nextFloat() < 0.0002f) {
                world.setBlockState(targetPos, ModBlocks.WEEDS.getDefaultState());
            }
        }
    }
}
