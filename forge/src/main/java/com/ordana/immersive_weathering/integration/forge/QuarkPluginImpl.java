package com.ordana.immersive_weathering.integration.forge;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.blocks.VerticalSlabBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.client.module.GreenerGrassModule;

import java.lang.reflect.Method;
import java.util.List;

public class QuarkPluginImpl {

    public static void onFirstClientTick() {

        try {
            if (ModuleLoader.INSTANCE.isModuleEnabled(GreenerGrassModule.class)) {
                Method method = ObfuscationReflectionHelper.findMethod(GreenerGrassModule.class, "registerGreenerColor", Iterable.class, boolean.class);
                List<String> leaves = List.of("immersive_weathering:oak_leaf_pile", "immersive_weathering:dark_oak_leaf_pile",
                        "immersive_weathering:acacia_leaf_pile", "immersive_weathering:jungle_leaf_pile",
                        "immersive_weathering:spruce_leaf_pile", "immersive_weathering:birch_leaf_pile");
                method.setAccessible(true);
                method.invoke(ModuleLoader.INSTANCE.getModuleInstance(GreenerGrassModule.class), leaves, true);
            }
        } catch (Exception ignored) {
        }
    }


    public static void addAllVerticalSlabs(ImmutableBiMap.Builder<Block, Block> builder) {
        var map = builder.build();
        for (var e : map.entrySet()) {
            if (e.getKey() instanceof SlabBlock) {
                String vsName = e.getKey().getRegistryName().getPath().replace("slab", "vertical_slab");
                String vsAltName = e.getValue().getRegistryName().getPath().replace("slab", "vertical_slab");
                Block vs = null;
                Block vsAlt = null;
                ResourceLocation vsRes = new ResourceLocation("quark", vsName);
                if (ForgeRegistries.BLOCKS.containsKey(vsRes)) {
                    vs = ForgeRegistries.BLOCKS.getValue(vsRes);
                } else {
                    vsRes = ImmersiveWeathering.res(vsName);
                    if (ForgeRegistries.BLOCKS.containsKey(vsRes)) {
                        vs = ForgeRegistries.BLOCKS.getValue(vsRes);
                    }
                }
                ResourceLocation vsAltRes = new ResourceLocation("quark", vsAltName);
                if (ForgeRegistries.BLOCKS.containsKey(vsAltRes)) {
                    vsAlt = ForgeRegistries.BLOCKS.getValue(vsAltRes);
                } else {
                    vsAltRes = ImmersiveWeathering.res(vsAltName);
                    if (ForgeRegistries.BLOCKS.containsKey(vsAltRes)) {
                        vsAlt = ForgeRegistries.BLOCKS.getValue(vsAltRes);
                    }
                }
                if (vs != null && vsAlt != null) {
                    builder.put(vs, vsAlt);
                }
            }
        }
    }


    private static final EnumProperty<vazkii.quark.content.building.block.VerticalSlabBlock.VerticalSlabType>
            QUARK_TYPE = vazkii.quark.content.building.block.VerticalSlabBlock.TYPE;

    public static BlockState fixVerticalSlab(BlockState fixedBlock, BlockState original) {
        if (fixedBlock.hasProperty(QUARK_TYPE) && original.hasProperty(VerticalSlabBlock.TYPE)) {
            String name = original.getValue(VerticalSlabBlock.TYPE).toString();
            return fixedBlock.setValue(QUARK_TYPE, QUARK_TYPE.getValue(name).get());
        } else if (fixedBlock.hasProperty(VerticalSlabBlock.TYPE) && original.hasProperty(QUARK_TYPE)) {
            String name = original.getValue(QUARK_TYPE).toString();
            return fixedBlock.setValue(VerticalSlabBlock.TYPE, VerticalSlabBlock.TYPE.getValue(name).get());
        }
        return fixedBlock;
    }

    public static void addVerticalSlabPair(ImmutableBiMap.Builder<Block, Block> builder, java.util.function.Supplier<Block> altered) {
        String name = altered.get().getRegistryName().getPath();
        name = name.replace("cracked_", "");
        name = name.replace("mossy_", "");
        ResourceLocation res = new ResourceLocation("quark", name);
        if (ForgeRegistries.BLOCKS.containsKey(res)) {
            builder.put(ForgeRegistries.BLOCKS.getValue(res), altered.get());
        } else {
            res = new ResourceLocation("minecraft", name);
            if (ForgeRegistries.BLOCKS.containsKey(res)) {
                builder.put(ForgeRegistries.BLOCKS.getValue(res), altered.get());
            }
        }
    }

}
