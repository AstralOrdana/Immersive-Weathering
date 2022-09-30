package com.ordana.immersive_weathering.forge;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.client.ImmersiveWeatheringClient;
import com.ordana.immersive_weathering.forge.dynamic.ModDynamicRegistry;
import com.ordana.immersive_weathering.mixins.forge.AccessorBlockColor;
import com.ordana.immersive_weathering.mixins.forge.AccessorItemColor;
import com.ordana.immersive_weathering.reg.LeafPilesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IRegistryDelegate;
import vazkii.botania.client.render.ColorHandler;
import vazkii.quark.content.client.module.GreenerGrassModule;

import java.util.Map;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = ImmersiveWeatheringForge.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ImmersiveWeatheringForgeClient {

    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        ImmersiveWeatheringClient.setup();

    }

    @Mod.EventBusSubscriber(modid = ImmersiveWeathering.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientTicker {

        private static boolean clientTicked = false;

        @SubscribeEvent
        public static void firstClientTick(TickEvent.ClientTickEvent event) {
            if (!clientTicked && event.phase == TickEvent.Phase.END) {
                // if(ModList.get().isLoaded("quark")) QuarkPlugin.onFirstClientTick();
                try {
                    BlockColors bc = Minecraft.getInstance().getBlockColors();
                    Map<IRegistryDelegate<Block>, BlockColor> blockColorMap =((AccessorBlockColor) bc).getBlockColors();

                    LeafPilesRegistry.LEAF_PILES.get().forEach((key, value) -> {
                        BlockState leafState = key.defaultBlockState();
                        BlockColor color = blockColorMap.get(key.delegate);
                        if (color != null) {
                            BlockColor newBc = (s, t, p, i) -> color.getColor(leafState, t, p, i);
                            bc.register(newBc, value);
                        }
                    });

                } catch (Exception ignored) {
                }

                try {
                    ItemColors ic = Minecraft.getInstance().getItemColors();
                    Map<IRegistryDelegate<Item>, ItemColor> itemColorMap = ((AccessorItemColor) ic).getItemColors();

                    LeafPilesRegistry.LEAF_PILES.get().forEach((key, value) -> {
                        ItemStack leafItem = new ItemStack(key);
                        ItemColor baseColor = itemColorMap.get(leafItem.getItem().delegate);
                        if (baseColor != null) {
                            ItemColor newIc = (s, i) -> baseColor.getColor(leafItem, i);
                            ic.register(newIc, value);
                        }
                    });

                } catch (Exception ignored) {
                }

                clientTicked = true;
            }

        }
    }
}
