package com.ordana.immersive_weathering.fabric;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.events.ModEvents;
import com.ordana.immersive_weathering.reg.ModWaxables;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;

public class ImmersiveWeatheringFabric implements ModInitializer {


    @Override
    public void onInitialize() {

        //loads registries
        ImmersiveWeathering.commonInit();

        //fabric only stuff here
        if (CommonConfigs.FLAMMABLE_COBWEBS.get()) {
            FlammableBlockRegistry.getDefaultInstance().add(Blocks.COBWEB, 100, 100);
            FlammableBlockRegistry.getDefaultInstance().add(Blocks.WHEAT, 100, 100);
        }
        //if (PlatHelper.isModLoaded("trinkets")) TrinketRendererRegistry.registerRenderer(ModItems.FLOWER_CROWN.get(), new TrinketsImpl());

        //events
        UseBlockCallback.EVENT.register(ImmersiveWeatheringFabric::onRightClickBlock);
        PlatHelper.addCommonSetup(ImmersiveWeatheringFabric::onSetup);
    }

    public static void onSetup() {
        ModWaxables.getValues().forEach(OxidizableBlocksRegistry::registerWaxableBlockPair);
    }

    public static InteractionResult onRightClickBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        return ModEvents.onBlockCLicked(player.getItemInHand(hand), player, level, hand, hitResult);
    }

}
