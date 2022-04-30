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
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin  extends Level{


    protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> key, Holder<DimensionType> typeHolder, Supplier<ProfilerFiller> supplier, boolean aSuper, boolean aSuper1, long aSuper2) {
        super(levelData, key, typeHolder, supplier, aSuper, aSuper1, aSuper2);
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
    private BlockPos grabPos(BlockPos value) {
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

}
