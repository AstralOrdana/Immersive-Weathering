package com.ordana.immersive_weathering.mixins.forge;

import com.ordana.immersive_weathering.blocks.rusty.Rustable;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Rustable.class)
public interface SelfRustableMixin extends IBlockExtension, ChangeOverTimeBlock<Rustable.RustLevel>, Rustable {

    @Nullable
    @Override
    default BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility toolAction, boolean simulate) {
        if (this.getAge() != Rustable.RustLevel.RUSTED &&
                this.getAge() != RustLevel.WEATHERED && ItemAbilities.AXE_SCRAPE.equals(toolAction)) {
            return this.getPrevious(state).orElse(null);
        } else if (ItemAbilities.AXE_WAX_OFF.equals(toolAction)) {
            var v = ModWaxables.getUnWaxed(state);
            if (v.isPresent()) {
                return v.get();
            }
        }
        return IBlockExtension.super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
