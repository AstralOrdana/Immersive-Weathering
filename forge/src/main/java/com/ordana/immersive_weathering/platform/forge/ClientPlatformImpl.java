package com.ordana.immersive_weathering.platform.forge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;

import java.util.function.Supplier;

public class ClientPlatformImpl {

    public static void registerRenderType(Block block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block,type);
    }
}
