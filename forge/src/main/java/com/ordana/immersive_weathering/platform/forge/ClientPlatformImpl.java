package com.ordana.immersive_weathering.platform.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.Random;

public class ClientPlatformImpl {

    public static void registerRenderType(Block block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block, type);
    }

    public static void renderBlock(FallingBlockEntity entity, PoseStack matrixStack, MultiBufferSource buffer, BlockState blockstate, Level world, BlockPos blockpos, BlockRenderDispatcher modelRenderer) {
        for (RenderType type : RenderType.chunkBufferLayers()) {
            //TODO. move to lib
            if (ItemBlockRenderTypes.canRenderInLayer(blockstate, type)) {
                ForgeHooksClient.setRenderType(type);
                modelRenderer.getModelRenderer().tesselateBlock(world, modelRenderer.getBlockModel(blockstate), blockstate, blockpos, matrixStack,
                        buffer.getBuffer(type), false, new Random(), blockstate.getSeed(entity.getStartPos()), OverlayTexture.NO_OVERLAY);
            }
        }
        ForgeHooksClient.setRenderType(null);
    }
}
