package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin  extends Level{


    protected ServerLevelMixin(WritableLevelData p_204149_, ResourceKey<Level> p_204150_, Holder<DimensionType> p_204151_, Supplier<ProfilerFiller> p_204152_, boolean p_204153_, boolean p_204154_, long p_204155_) {
        super(p_204149_, p_204150_, p_204151_, p_204152_, p_204153_, p_204154_, p_204155_);
    }

    @ModifyVariable(method = "tickChunk",
            at = @At(value = "LOAD"),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=randomTick"
                    )
            ),
            require = 1
    )
    public BlockPos grabPos(BlockPos value) {
        grabbedPos = value;
        return value;
    }

    @Unique
    private BlockPos grabbedPos;

    @ModifyVariable(method = "tickChunk",
            at = @At(value = "LOAD"),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=randomTick"
                    )
            ),
            require = 1
    )
    private FluidState callTick(FluidState value) {
        BlockGrowthHandler.tickBlock(this.getBlockState(grabbedPos), ((ServerLevel) ((Object) this)), grabbedPos);
        return value;
    }

    @Redirect(method = "tickChunk",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;handlePrecipitation(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/biome/Biome$Precipitation;)V"))
    private void spawnIcicles(Block instance, BlockState state, Level level, BlockPos pos, Biome.Precipitation precipitation) {
        WeatheringHelper.tryPlacingIcicle(state, level, pos, precipitation);

        instance.handlePrecipitation(state, level, pos, precipitation);
    }

}
