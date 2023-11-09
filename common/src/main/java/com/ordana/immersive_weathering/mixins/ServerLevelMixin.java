package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.data.block_growths.BlockGrowthHandler;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {
    protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    /*

    protected ServerLevelMixin(WritableLevelData writableLevelData, ResourceKey<Level> resourceKey, Holder<DimensionType> holder, Supplier<ProfilerFiller> supplier, boolean bl, boolean bl2, long l, int i) {
        super(writableLevelData, resourceKey, holder, supplier, bl, bl2, l, i);
    }

    @Inject(method = "tickChunk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFluidState()Lnet/minecraft/world/level/material/FluidState;"),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=randomTick"
                    )
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1
    )
    private void callTick(LevelChunk chunk, int randomTickSpeed, CallbackInfo ci,
                          ChunkPos chunkPos, boolean bl, int i, int j, ProfilerFiller profilerFiller,
                          LevelChunkSection[] var8, int var9, int var10, LevelChunkSection levelChunkSection,
                          int k, int l, BlockPos blockPos3, BlockState blockState2) {
        BlockState newState = levelChunkSection.getBlockState(
                blockPos3.getX() - i, blockPos3.getY() - k, blockPos3.getZ() - j);
        BlockGrowthHandler.tickBlock(TickSource.BLOCK_TICK, newState, ((ServerLevel) ((Object) this)),  blockPos3);
    }

    @Inject(method = "tickChunk",
            require = 0,
            at = @At(value = "TAIL"))
    private void precipitationTick(LevelChunk levelChunk, int randomTickSpeed, CallbackInfo ci) {
        var p = this.getProfiler();
        p.push("ImmWeatheringExtraRandomTicks");
        BlockGrowthHandler.performSkyAccessTick((ServerLevel) (Object) this, levelChunk, randomTickSpeed);
        p.pop();
    }

     */
}
