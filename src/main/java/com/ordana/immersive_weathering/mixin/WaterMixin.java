package com.ordana.immersive_weathering.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class WaterMixin extends Block implements BucketPickup {

    @Shadow
    @Final
    private FlowingFluid fluid;

    @Shadow
    @Final
    public static ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS;

    @Shadow
    protected abstract void fizz(LevelAccessor p_54701_, BlockPos p_54702_);

    public WaterMixin(Properties settings, FlowingFluid fluid) {
        super(settings);
    }

    @Inject(method = "shouldSpreadLiquid", at = @At("TAIL"), cancellable = true)
    public void shouldSpreadLiquid(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid.is(FluidTags.LAVA)) {
            boolean bl = world.getBlockState(pos.below()).is(Blocks.NETHERRACK);
            for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.relative(direction.getOpposite());
                if (bl && world.getBlockState(blockPos).is(Blocks.PACKED_ICE)) {
                    world.setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
                    this.fizz(world, pos);
                    cir.setReturnValue(false);
                }
            }
        }
        cir.setReturnValue(true);
    }
}
