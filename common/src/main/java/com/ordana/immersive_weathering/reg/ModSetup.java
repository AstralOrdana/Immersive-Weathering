package com.ordana.immersive_weathering.reg;

import com.google.common.base.Stopwatch;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.fluid_generators.ModFluidGenerators;
import com.ordana.immersive_weathering.data.position_tests.ModPositionRuleTests;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;

import java.util.ArrayList;
import java.util.List;

public class ModSetup {

    private static int setupStage = 0;

    private static final List<Runnable> MOD_SETUP_WORK = List.of(
            ModSetup::registerCompostables,
            ModSetup::registerFabricFlammable,
            ModPositionRuleTests::register,
            ModFluidGenerators::register
    );

    public static void setup() {
        var list = new ArrayList<Long>();
        try {
            Stopwatch watch = Stopwatch.createStarted();

            for (int i = 0; i < MOD_SETUP_WORK.size(); i++) {
                setupStage = i;
                MOD_SETUP_WORK.get(i).run();
                list.add(watch.elapsed().toMillis());
                watch.reset();
                watch.start();
            }

            ImmersiveWeathering.LOGGER.info("Finished mod setup in: {} ms", list);

        } catch (Exception e) {
            ImmersiveWeathering.LOGGER.error(e);
            terminateWhenSetupFails();
        }
    }

    private static void terminateWhenSetupFails() {
        //if setup fails crash the game. idk why it doesn't do that on its own wtf
        throw new IllegalStateException("Mod setup has failed to complete (" + setupStage + ").\n" +
                " This might be due to some mod incompatibility or outdated dependencies (check if everything is up to date).\n" +
                " Refusing to continue loading with a broken modstate. Next step: crashing this game, no survivors");
    }

    private static void registerFabricFlammable() {
        if (CommonConfigs.FLAMMABLE_COBWEBS.get()) {
            RegHelper.registerBlockFlammability(Blocks.COBWEB, 100, 100);
        }
        if (CommonConfigs.FLAMMABLE_CROPS.get()) {
            RegHelper.registerBlockFlammability(Blocks.WHEAT, 100, 100);
            RegHelper.registerBlockFlammability(Blocks.CARROTS, 100, 100);
            RegHelper.registerBlockFlammability(Blocks.POTATOES, 100, 100);
            RegHelper.registerBlockFlammability(Blocks.BEETROOTS, 100, 100);
        }
    }

    private static void registerCompostables() {
        ModItems.BARK.values().forEach(b -> ComposterBlock.COMPOSTABLES.put(b, 0.8f));
        ModBlocks.LEAF_PILES.values().forEach(b -> ComposterBlock.COMPOSTABLES.put(b.asItem(), 0.3f));
        ComposterBlock.COMPOSTABLES.put(ModItems.MOSS_CLUMP.get(), 0.5f);
        ComposterBlock.COMPOSTABLES.put(ModItems.AZALEA_FLOWERS.get(), 0.5f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.MULCH_BLOCK.get().asItem(), 1f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.NULCH_BLOCK.get().asItem(), 1f);
        ComposterBlock.COMPOSTABLES.put(ModItems.FLOWER_CROWN.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.WEEDS.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.IVY.get(), 0.3f);
    }
}
