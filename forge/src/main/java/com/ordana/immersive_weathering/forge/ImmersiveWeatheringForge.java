package com.ordana.immersive_weathering.forge;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

/**
 * Authors: MehVahdJukaar, Ordana, Keybounce,
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeatheringForge {
    public static final String MOD_ID = ImmersiveWeathering.MOD_ID;

    public ImmersiveWeatheringForge() {

        ImmersiveWeathering.commonInit();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerOverrides);
        MinecraftForge.EVENT_BUS.register(this);


        /**
         * Update stuff:
         * Configs
         * sand later
         * ash layer
         * leaf layer
         */

        //TODO: fix layers texture generation
        //TODO: fix grass growth replacing double plants and add tag
    }

    public void registerOverrides(RegisterEvent event) {
        //override
        if (event.getRegistryKey() == ForgeRegistries.ITEMS.getRegistryKey())
            event.getForgeRegistry().register(new ResourceLocation("minecraft:hanging_roots"),
                    new CeilingAndWallBlockItem(Blocks.HANGING_ROOTS, ModBlocks.HANGING_ROOTS_WALL.get(),
                            new Item.Properties()));
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        var ret = com.ordana.immersive_weathering.events.ModEvents.onBlockCLicked(event.getItemStack(),
                event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        if (ret != InteractionResult.PASS) {
            event.setCanceled(true);
            event.setCancellationResult(ret);
        }
    }


    //TODO: add back on setup
    private static void registerWaxables() {
        try {
            var oldWaxables = HoneycombItem.WAXABLES.get();
            HoneycombItem.WAXABLES = Suppliers.memoize(() -> ImmutableBiMap.<Block, Block>builder()
                    .putAll(oldWaxables)
                    .putAll(ModWaxables.getValues()).build());

            HoneycombItem.WAX_OFF_BY_BLOCK = Suppliers.memoize(() -> (HoneycombItem.WAXABLES.get()).inverse());

        } catch (Exception e) {
            ImmersiveWeathering.LOGGER.error("Failed to register Waxables: ", e);
        }

    }


}
