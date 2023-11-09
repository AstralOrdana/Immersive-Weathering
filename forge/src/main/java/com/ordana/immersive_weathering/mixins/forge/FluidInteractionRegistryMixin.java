package com.ordana.immersive_weathering.mixins.forge;

import com.ordana.immersive_weathering.data.fluid_generators.FluidGeneratorsHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//cant use these directly as they depend on tags, and we cant load them statically. sorry forge
@Mixin(FluidInteractionRegistry.class)
public class FluidInteractionRegistryMixin {

    @Inject(method = "canInteract", at = @At("HEAD"), cancellable = true, remap = false)
    private static void immersiveWeatheringDataFluidInteraction(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = level.getBlockState(pos);
        var f = ((LiquidBlock) state.getBlock()).getFluid();
        boolean lava = state.getFluidState().is(FluidTags.LAVA);
        var successPos = FluidGeneratorsHandler.applyGenerators(f,
                FluidGeneratorsHandler.POSSIBLE_FLOW_DIRECTIONS, pos, level);
        if (successPos.isPresent()) {
            if (lava) {
                level.levelEvent(1501, pos, 0);
                // FluidBlo.fizz(level, successPos.get().getFirst());
            }
            cir.setReturnValue(false);
        }
    }

}
