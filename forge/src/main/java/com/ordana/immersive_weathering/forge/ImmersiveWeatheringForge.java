package com.ordana.immersive_weathering.forge;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.network.NetworkHandler;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

/**
 * Authors: MehVahdJukaar, Ordana, Keybounce,
 */
@Mod(ImmersiveWeathering.MOD_ID)
public class ImmersiveWeatheringForge {
    public static final String MOD_ID = ImmersiveWeathering.MOD_ID;
    // Keep these in sync with gradle.properties and neoforge.mods.toml so loader errors and runtime errors agree.
    private static final String REQUIRED_MINECRAFT_VERSION = "1.21.1";
    private static final String MIN_MOONLIGHT_VERSION = "1.21.1-3.0.5";

    public ImmersiveWeatheringForge(IEventBus modEventBus, ModContainer modContainer) {
        // NeoForge already enforces dependency metadata, but this gives a clearer startup error before common code touches Moonlight classes.
        verifyRuntimeDependencies();

        try {
            ImmersiveWeathering.commonInit();
        } catch (NoClassDefFoundError error) {
            var missingClass = String.valueOf(error.getMessage());
            // Convert an opaque classloading failure into a release-friendly message when Moonlight is missing or too old.
            if (missingClass.contains("moonlight") || missingClass.contains("selene")) {
                throw new IllegalStateException(
                    "Immersive Weathering could not initialize because Moonlight Lib is missing or incompatible. "
                        + "Expected Moonlight Lib " + MIN_MOONLIGHT_VERSION + " or newer on NeoForge "
                        + REQUIRED_MINECRAFT_VERSION + ".",
                    error);
            }
            throw error;
        }
        NetworkHandler.init(modEventBus);

        modEventBus.addListener(this::registerOverrides);
        NeoForge.EVENT_BUS.register(this);


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

    private static void verifyRuntimeDependencies() {
        // This is intentionally Forge-side only: commonInit imports Moonlight classes directly and cannot report this cleanly on its own.
        var moonlightContainer = ModList.get().getModContainerById("moonlight")
            .orElseThrow(() -> new IllegalStateException(
                "Immersive Weathering requires Moonlight Lib " + MIN_MOONLIGHT_VERSION
                    + " or newer on NeoForge " + REQUIRED_MINECRAFT_VERSION + "."));

        var detectedVersion = moonlightContainer.getModInfo().getVersion();
        var minimumVersion = new DefaultArtifactVersion(MIN_MOONLIGHT_VERSION);
        if (detectedVersion.compareTo(minimumVersion) < 0) {
            // Keep the version comparison explicit so users get a targeted error instead of a later API mismatch.
            throw new IllegalStateException(
                "Immersive Weathering requires Moonlight Lib " + MIN_MOONLIGHT_VERSION
                    + " or newer, but found: " + detectedVersion + '.');
        }
    }

    public void registerOverrides(RegisterEvent event) {
        var hangingRootsId = ResourceLocation.fromNamespaceAndPath("minecraft", "hanging_roots");
        event.register(Registries.ITEM, hangingRootsId, () -> new CeilingAndWallBlockItem(
            Blocks.HANGING_ROOTS,
            ModBlocks.HANGING_ROOTS_WALL.get(),
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
}
