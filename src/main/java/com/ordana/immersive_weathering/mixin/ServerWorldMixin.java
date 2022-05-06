package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.Supplier;

@Debug(export=true)
@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, registryEntry, profiler, isClient, debugWorld, seed);
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
        gx = value.getX();
        gy = value.getY();
        gz = value.getZ();
        return value;
    }

    @Unique
    private int gx;
    @Unique
    private int gy;
    @Unique
    private int gz;

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
        BlockGrowthHandler.tickBlock(gx,gy,gz, ((ServerWorld) ((Object) this)));
        return value;
    }

}

/*@Debug(export=true)
@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World {

    protected ServerWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, registryEntry, profiler, isClient, debugWorld, seed);
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
        BlockGrowthHandler.tickBlock(this.getBlockState(grabbedPos), ((ServerWorld) ((Object) this)), grabbedPos);
        return value;
    }

}*/
