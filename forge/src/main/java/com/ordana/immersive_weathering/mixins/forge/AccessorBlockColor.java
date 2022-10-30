package com.ordana.immersive_weathering.mixins.forge;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IRegistryDelegate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin({BlockColors.class})
public interface AccessorBlockColor {
    @Accessor("f_92571_")
    Map<IRegistryDelegate<Block>, BlockColor> getBlockColors();
}

