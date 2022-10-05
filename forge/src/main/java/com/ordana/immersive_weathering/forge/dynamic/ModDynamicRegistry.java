package com.ordana.immersive_weathering.forge.dynamic;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.client.particles.LeafParticle;
import com.ordana.immersive_weathering.dynamicpack.ClientDynamicResourcesHandler;
import com.ordana.immersive_weathering.dynamicpack.ServerDynamicResourcesHandler;
import com.ordana.immersive_weathering.items.LeafPileBlockItem;
import com.ordana.immersive_weathering.reg.ModParticles;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.mehvahdjukaar.selene.block_set.BlockSetManager;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesTypeRegistry;
import net.mehvahdjukaar.selene.block_set.wood.WoodType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.*;

public class ModDynamicRegistry {

    public static final Map<LeafPileBlock, LeavesType> LEAF_TO_TYPE = new LinkedHashMap<>();
    public static final Map<LeavesType, LeafPileBlock> TYPE_TO_LEAF = new LinkedHashMap<>();

    public static final Map<LeavesType, SimpleParticleType> TYPE_TO_LEAF_PARTICLE = new LinkedHashMap<>();

    public static final Map<Item, LeavesType> LEAF_PILES_ITEMS = new LinkedHashMap<>();

    public static final Map<WoodType, Item> MODDED_BARK = new LinkedHashMap<>();

    public static Map<Block, LeafPileBlock> getLeafToLeafPileMap() {
        var builder = ImmutableMap.<Block, LeafPileBlock>builder();
        LEAF_TO_TYPE.forEach((key, value) -> builder.put(value.leaves, key));
        return builder.build();
    }

    public static Map<Block, SimpleParticleType> getLeavesToParticleMap() {
        var builder = ImmutableMap.<Block, SimpleParticleType>builder();
        TYPE_TO_LEAF_PARTICLE.forEach((key, value) -> builder.put(key.leaves, value));
        return builder.build();
    }

    public static Map<Block, Pair<Item, Block>> getBarkMap() {
        Map<Block, Pair<Item, Block>> map = new HashMap<>();
        MODDED_BARK.forEach((wood, item) -> {
            Block log = wood.log;
            Block stripped = wood.getBlockOfThis("stripped_log");
            if (stripped != null) {
                map.put(stripped, Pair.of(item, log));
            }
            Block stripped_wood = wood.getBlockOfThis("stripped_wood");
            if (stripped_wood != null) {
                map.put(stripped_wood, Pair.of(item, wood.getBlockOfThis("wood")));
            }
        });
        return map;
    }



    private static void registerLeafPilesItems(RegistryEvent.Register<Item> event, Collection<LeavesType> leavesTypes) {

        for (LeavesType type : leavesTypes) {
            if (!type.isVanilla()) {
                Item i = new LeafPileBlockItem(TYPE_TO_LEAF.get(type),
                        new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS));
                i.setRegistryName(TYPE_TO_LEAF.get(type).getRegistryName());
                event.getRegistry().register(i);
            }
        }
    }

    private static void registerLeafPilesParticles(RegistryEvent.Register<ParticleType<?>> event) {
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("oak"), ModParticles.OAK_LEAF.get());
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("birch"), ModParticles.BIRCH_LEAF.get());
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("spruce"), ModParticles.SPRUCE_LEAF.get());
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("jungle"), ModParticles.JUNGLE_LEAF.get());
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("acacia"), ModParticles.ACACIA_LEAF.get());
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("dark_oak"), ModParticles.DARK_OAK_LEAF.get());
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("azalea"), ModParticles.AZALEA_LEAF.get());
        TYPE_TO_LEAF_PARTICLE.put(LeavesTypeRegistry.fromNBT("flowering_azalea"), ModParticles.AZALEA_FLOWER.get());

        for (LeavesType type : LeavesTypeRegistry.LEAVES_TYPES.values()) {
            if (!type.isVanilla()) {
                String name = type.getNamespace() + "/" + type.getTypeName() + "_leaf";
                var o = new SimpleParticleType(true);
                event.getRegistry().register(o.setRegistryName(name));
                TYPE_TO_LEAF_PARTICLE.put(type, o);
            }
        }
    }

    public static void init() {


        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addGenericListener(ParticleType.class, ModDynamicRegistry::registerLeafPilesParticles);

        var serverRes = new ServerDynamicResourcesHandler();
        serverRes.register(bus);

        PlatformHelper.getEnv().ifClient(() -> {
            var clientRes = new ClientDynamicResourcesHandler();

            clientRes.register(bus);


            ClientPlatformHelper.addParticleRegistration(ModDynamicRegistry::registerParticlesRenderers);
        });
    }

    private static void registerParticlesRenderers(ClientPlatformHelper.ParticleEvent particleEvent) {
        for (var e : TYPE_TO_LEAF_PARTICLE.entrySet()) {
            if (!e.getKey().isVanilla()) {
                particleEvent.register(e.getValue(), LeafParticle.ColoredLeafParticle::new);
            }
        }
    }

}
