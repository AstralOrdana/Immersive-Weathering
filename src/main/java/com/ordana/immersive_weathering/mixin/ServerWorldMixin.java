package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

	@Inject(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FluidState;hasRandomTicks()Z", shift = Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
	private void tickBlocks(WorldChunk chunk, int randomTickSpeed, CallbackInfo ci, ChunkPos chunkPos, boolean bl, int i, int j, Profiler profiler, ChunkSection var8[], int var9, int var10, ChunkSection chunkSection, int k, int l, BlockPos blockPos3) {
		if(ImmersiveWeathering.getConfig().blockGrowthConfig.blockGrowth) {
			BlockGrowthHandler.tickBlock(blockPos3, (ServerWorld) (Object) this);
		}
	}
}
