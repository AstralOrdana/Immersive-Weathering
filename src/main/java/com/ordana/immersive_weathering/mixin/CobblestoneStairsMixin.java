package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.CleanMossableStairsBlock;
import com.ordana.immersive_weathering.registry.blocks.Mossable;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class CobblestoneStairsMixin {
    @Shadow @Final public static Block COBBLESTONE;

    @Redirect
            (
                    method = "<clinit>", // Internal name of static {} block
                    at = @At
                            (
                                    value = "NEW", // Target constructor calls
                                    target = "net/minecraft/block/StairsBlock", // Internal name of TorchBlock
                                    ordinal = 0 // Target only the first TorchBlock constructor call found
                            ),
                    slice = @Slice // Restrict search scope
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT", // Target a literal
                                                    args="stringValue=cobblestone_stairs"
                                            )
                                    // No Slice.to bound, we use At.ordinal = 0 for Redirect.at
                                    // so only the first call is targeted
                            )
            )
    /* Constructor redirect handlers take the same parameters as the constructor,
     * and return the constructed type. This one must be static because <clinit> is static. */
    private static StairsBlock cobblestoneStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CleanMossableStairsBlock(Mossable.MossLevel.UNAFFECTED, COBBLESTONE.getDefaultState(), settings);
    }
}
