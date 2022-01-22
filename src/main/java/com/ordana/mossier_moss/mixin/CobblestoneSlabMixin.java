package com.ordana.mossier_moss.mixin;

import com.ordana.mossier_moss.registry.blocks.CleanSlabBlock;
import com.ordana.mossier_moss.registry.blocks.Mossable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class CobblestoneSlabMixin {
    @Redirect
            (
                    method = "<clinit>", // Internal name of static {} block
                    at = @At
                            (
                                    value = "NEW", // Target constructor calls
                                    target = "net/minecraft/block/SlabBlock", // Internal name of TorchBlock
                                    ordinal = 0 // Target only the first TorchBlock constructor call found
                            ),
                    slice = @Slice // Restrict search scope
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT", // Target a literal
                                                    args="stringValue=cobblestone_slab"
                                            )
                                    // No Slice.to bound, we use At.ordinal = 0 for Redirect.at
                                    // so only the first call is targeted
                            )
            )
    /* Constructor redirect handlers take the same parameters as the constructor,
     * and return the constructed type. This one must be static because <clinit> is static. */
    private static SlabBlock cobblestoneSlab(AbstractBlock.Settings settings)
    {
        return new CleanSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }
}
