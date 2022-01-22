package com.ordana.mossier_moss.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResult;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(ShearsItem.class)
public abstract class ShearsMixin {

    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/item/ShearsItem;useOnBlock:Ljava/util/Map;",
                    opcode = Opcodes.PUTSTATIC,
                    ordinal = 0,
                    shift = At.Shift.AFTER)
    )

    private ActionResult useOnBlock (ItemUsageContext context) {
        Optional<BlockState> optional = this.getStrippedState(blockState);

    }

}
