package com.ordana.immersive_weathering.mixins.fabric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ordana.immersive_weathering.items.FlowerCrownItem;
import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorLayerMixin <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    @Shadow @Final private static Map<String, ResourceLocation> ARMOR_LOCATION_CACHE;

    public ArmorLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "renderArmorPiece", at =@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;ZLnet/minecraft/client/model/HumanoidModel;ZFFFLjava/lang/String;)V",
            ordinal = 2, shift = At.Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    public void renderFlowerCrown(PoseStack poseStack, MultiBufferSource multiBufferSource, T livingEntity,
                                      EquipmentSlot equipmentSlot, int i, A humanoidModel, CallbackInfo ci,
                                      ItemStack itemStack, ArmorItem armorItem, boolean bl, boolean bl2) {
        if(armorItem == ModItems.FLOWER_CROWN.get()){
            var texture = FlowerCrownItem.getModelTexture(itemStack);
            if(texture != null){

                ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(texture);

                if (resourcelocation == null) {
                    resourcelocation = new ResourceLocation(texture);
                    ARMOR_LOCATION_CACHE.put(texture, resourcelocation);
                }

                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource, RenderType.armorCutoutNoCull(
                        resourcelocation), false, bl);
                humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0F);


                ci.cancel();
            }
        }
    }
}
