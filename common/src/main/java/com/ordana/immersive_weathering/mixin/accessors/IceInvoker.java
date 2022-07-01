package com.ordana.immersive_weathering.mixin.accessors;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(IceBlock.class)
public interface IceInvoker {

    @Invoker
    void invokeMelt(BlockState state, Level level, BlockPos pos);
}
