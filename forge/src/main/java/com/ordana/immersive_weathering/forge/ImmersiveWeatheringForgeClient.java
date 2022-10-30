package com.ordana.immersive_weathering.forge;

import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.client.ImmersiveWeatheringClient;
import com.ordana.immersive_weathering.mixins.forge.AccessorBlockColor;
import com.ordana.immersive_weathering.mixins.forge.AccessorItemColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IRegistryDelegate;

import java.util.Map;

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

                    IWPlatformStuffImpl.COPY_BLOCK_COLORS.forEach((block, colorFrom) -> {
                        BlockState leafState = colorFrom.defaultBlockState();
                        BlockColor color = blockColorMap.get(colorFrom.delegate);
                        if (color != null) {
                            BlockColor newBc = (s, t, p, i) -> color.getColor(leafState, t, p, i);
                            bc.register(newBc, block);
                        }
                    });

                } catch (Exception ignored) {
                }

                try {
                    ItemColors ic = Minecraft.getInstance().getItemColors();
                    Map<IRegistryDelegate<Item>, ItemColor> itemColorMap = ((AccessorItemColor) ic).getItemColors();

                    IWPlatformStuffImpl.COPY_ITEM_COLORS.forEach((item, colorFrom) -> {
                        ItemStack leafItem = new ItemStack(colorFrom);
                        ItemColor baseColor = itemColorMap.get(leafItem.getItem().delegate);
                        if (baseColor != null) {
                            ItemColor newIc = (s, i) -> baseColor.getColor(leafItem, i);
                            ic.register(newIc, item);
                        }
                    });

                } catch (Exception ignored) {
                }

                clientTicked = true;
            }

        }
    }
}
