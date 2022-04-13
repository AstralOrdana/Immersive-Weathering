package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerWorld.class)
public abstract class SnowMixin {

    @Redirect(method = {"tickChunk"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;precipitationTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/Biome$Precipitation;)V"))
            public void tickChunk(Block instance, BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        WeatheringHelper.tryPlacingIcicle(state, world, pos, precipitation);
        instance.precipitationTick(state, world, pos, precipitation);
    }
}