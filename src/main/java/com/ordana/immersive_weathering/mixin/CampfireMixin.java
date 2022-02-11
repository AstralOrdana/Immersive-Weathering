package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.SootyCampfire;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class CampfireMixin {
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/CampfireBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=campfire"
                                            )

                            )
            )
    private static CampfireBlock campfire(boolean emitsParticles, int fireDamage, AbstractBlock.Settings settings)
    {
        return new SootyCampfire(true, 1, settings.ticksRandomly());
    }
}
