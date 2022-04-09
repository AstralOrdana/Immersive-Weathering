package com.ordana.immersive_weathering.mixin.snowrealmagic;

import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//live by the mixin, die by the mixin
@Pseudo
@Mixin(targets = "snownee.snow.WorldTickHandler", remap = false)
public abstract class SnowMixin {


    @Redirect(method = "tick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;handlePrecipitation(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/biome/Biome$Precipitation;)V"),
            require = 0)
    private static void tickChunk(Block instance, BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        WeatheringHelper.tryPlacingIcicle(state, level, pos, precipitation);

        instance.handlePrecipitation(state, level, pos, precipitation);
    }


}
