package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.SootyCampfire;
import com.ordana.immersive_weathering.registry.blocks.SootyFire;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class FireMixin {
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/FireBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=fire"
                                            )

                            )
            )
    private static FireBlock fire(AbstractBlock.Settings settings)
    {
        return new SootyFire(settings.ticksRandomly());
    }
}
