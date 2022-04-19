package com.ordana.immersive_weathering.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.registries.IRegistryDelegate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(BlockColors.class)
public interface BlockColorAccessor {

    @Accessor
    Map<IRegistryDelegate<Block>, BlockColor> getBlockColors();
}
