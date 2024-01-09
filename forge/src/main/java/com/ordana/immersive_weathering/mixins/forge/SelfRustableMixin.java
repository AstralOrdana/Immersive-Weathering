package com.ordana.immersive_weathering.mixins.forge;

import com.ordana.immersive_weathering.blocks.rusty.Rustable;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Rustable.class)
public interface SelfRustableMixin extends IForgeBlock, ChangeOverTimeBlock<Rustable.RustLevel>, Rustable {

    @Nullable
    @Override
    default BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (this.getAge() != Rustable.RustLevel.RUSTED &&
                this.getAge() != RustLevel.WEATHERED && ToolActions.AXE_SCRAPE.equals(toolAction)) {
            return this.getPrevious(state).orElse(null);
        } else if (ToolActions.AXE_WAX_OFF.equals(toolAction)) {
            var v = ModWaxables.getUnWaxed(state);
            if (v.isPresent()) {
                return v.get();
            }
        }
        return IForgeBlock.super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
