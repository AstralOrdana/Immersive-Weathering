package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = {"net.minecraft.server.level.ServerLevel", "snownee.snow.WorldTickHandler"})
public abstract class ServerLevelSnowMixin {

    @ModifyArg(method = {"tickChunk",
            "Lnet/minecraft/server/level/ServerLevel;m_8714_(Lnet/minecraft/world/level/chunk/LevelChunk;I)V"},
            require = 0,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;handlePrecipitation(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/biome/Biome$Precipitation;)V"))
    private BlockState spawnIcicles(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        WeatheringHelper.tryPlacingIcicle(state, level, pos, precipitation);
        return state;
    }

    @ModifyArg(method = "tick",
            require = 0,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;handlePrecipitation(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/biome/Biome$Precipitation;)V"))
    private static BlockState spawnIciclesSRM(BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        WeatheringHelper.tryPlacingIcicle(state, level, pos, precipitation);
        return state;
    }

}
