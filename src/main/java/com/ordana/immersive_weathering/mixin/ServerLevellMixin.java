package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.block_growth.TickSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevellMixin extends Level {


    protected ServerLevellMixin(WritableLevelData levelData, ResourceKey<Level> key, Holder<DimensionType> typeHolder, Supplier<ProfilerFiller> supplier, boolean aSuper, boolean aSuper1, long aSuper2) {
        super(levelData, key, typeHolder, supplier, aSuper, aSuper1, aSuper2);
    }

    @ModifyVariable(method = "tickChunk",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;getBlockState(III)Lnet/minecraft/world/level/block/state/BlockState;"),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=randomTick"
                    )
            ),
            require = 1
    )
    private BlockPos grabPos(BlockPos value) {
        grabbedPos = value;
        return value;
    }

    @Unique
    private BlockPos grabbedPos;

    @Inject(method = "tickChunk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFluidState()Lnet/minecraft/world/level/material/FluidState;"),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=randomTick"
                    )
            ),
            require = 1
    )
    private void callTick(LevelChunk levelChunk, int i, CallbackInfo ci) {
        BlockGrowthHandler.tickBlock(TickSource.BLOCK_TICK, this.getBlockState(grabbedPos), ((ServerLevel) ((Object) this)), grabbedPos);
    }

}
