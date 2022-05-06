package com.ordana.immersive_weathering.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(IceBlock.class)
public interface IceInvoker {

    @Invoker
    void invokeMelt(BlockState state, World level, BlockPos pos);
}
