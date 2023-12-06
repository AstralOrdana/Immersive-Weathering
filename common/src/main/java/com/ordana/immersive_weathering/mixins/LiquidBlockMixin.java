package com.ordana.immersive_weathering.mixins;

import com.google.common.collect.ImmutableList;
import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.data.fluid_generators.FluidGeneratorsHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin extends Block implements BucketPickup {

    @Shadow
    @Final
    public static ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS;

    @Shadow
    protected abstract void fizz(LevelAccessor levelAccessor, BlockPos pos);

    @Shadow
    public abstract FluidState getFluidState(BlockState blockState);

    @Shadow
    @Final
    protected FlowingFluid fluid;

    protected LiquidBlockMixin(Properties settings, FlowingFluid fluid) {
        super(settings);
    }

    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    private void shouldSpreadLiquid(Level level, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        var f = this.getOwnFluid();
        var successPos = FluidGeneratorsHandler.applyGenerators(f, POSSIBLE_FLOW_DIRECTIONS, pos, level);
        if(successPos.isPresent()){
            if(f.is(FluidTags.LAVA)) {
                this.fizz(level, successPos.get().getFirst());
            }
            cir.setReturnValue(false);
        }
    }

    @Unique
    public FlowingFluid getOwnFluid() {
        var f = IWPlatformStuff.getFlowingFluid((LiquidBlock) (Object) this);
        if (f == null)
            return this.fluid;
        return f;
    }

}
