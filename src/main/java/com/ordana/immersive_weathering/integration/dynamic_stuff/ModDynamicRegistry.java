package com.ordana.immersive_weathering.integration.dynamic_stuff;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.common.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.common.items.BurnableItem;
import com.ordana.immersive_weathering.common.items.LeafPileBlockItem;
import com.ordana.immersive_weathering.common.particles.LeafParticle;
import net.mehvahdjukaar.selene.block_set.BlockSetManager;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesTypeRegistry;
import net.mehvahdjukaar.selene.block_set.wood.WoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.*;

public class ModDynamicRegistry {

    public static final Map<LeafPileBlock, LeavesType> LEAF_TO_TYPE = new LinkedHashMap<>();
    public static final Map<LeavesType, LeafPileBlock> TYPE_TO_LEAF = new LinkedHashMap<>();

    public static final Map<ParticleType, LeavesType> LEAF_PARTICLE_TO_TYPE = new LinkedHashMap<>();
    public static final Map<LeavesType, ParticleType> TYPE_TO_LEAF_PARTICLE = new LinkedHashMap<>();

    public static final Map<Item, LeavesType> LEAF_PILES_ITEMS = new LinkedHashMap<>();

    public static final Map<WoodType, Item> MODDED_BARK = new LinkedHashMap<>();

    public static Map<Block, LeafPileBlock> getLeafToLeafPileMap() {
        var builder = ImmutableMap.<Block, LeafPileBlock>builder();
        LEAF_TO_TYPE.forEach((key, value) -> builder.put(value.leaves, key));
        return builder.build();
    }

    public static Map<Block, Pair<Item, Block>> getBarkMap() {
        Map<Block, Pair<Item, Block>> map = new HashMap<>();
        MODDED_BARK.forEach((wood, item) -> {
            Block log = wood.log;
            if (wood.strippedLog != null) {
                map.put(wood.strippedLog, Pair.of(item, log));
            }
        });
        return map;
    }

    private static void registerBarks(RegistryEvent.Register<Item> event, Collection<WoodType> woodTypes) {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (WoodType type : woodTypes) {
            if (!type.isVanilla()) {
                String name = type.getNamespace() + "/" + type.getTypeName() + "_bark";

                Item item = new BurnableItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200)
                        .setRegistryName(ImmersiveWeathering.res(name));
                registry.register(item);
                MODDED_BARK.put(type, item);
            }
        }
        LEAF_TO_TYPE.forEach((a, b) -> TYPE_TO_LEAF.put(b, a));
    }

    private static void registerLeafPiles(RegistryEvent.Register<Block> event, Collection<LeavesType> leavesTypes) {
        LEAF_TO_TYPE.put(ModBlocks.OAK_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("oak"));
        LEAF_TO_TYPE.put(ModBlocks.BIRCH_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("birch"));
        LEAF_TO_TYPE.put(ModBlocks.SPRUCE_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("spruce"));
        LEAF_TO_TYPE.put(ModBlocks.JUNGLE_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("jungle"));
        LEAF_TO_TYPE.put(ModBlocks.ACACIA_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("acacia"));
        LEAF_TO_TYPE.put(ModBlocks.DARK_OAK_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("dark_oak"));
        LEAF_TO_TYPE.put(ModBlocks.AZALEA_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("azalea"));
        LEAF_TO_TYPE.put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE.get(), LeavesTypeRegistry.fromNBT("flowering_azalea"));


        IForgeRegistry<Block> registry = event.getRegistry();
        for (LeavesType type : leavesTypes) {
            if (!type.isVanilla()) {
                String name = type.getNamespace() + "/" + type.getTypeName() + "_leaf_pile";

                LeafPileBlock block = (LeafPileBlock) new LeafPileBlock(
                        BlockBehaviour.Properties.copy(ModBlocks.OAK_LEAF_PILE.get()),
                        false, false, true, List.of(() -> (SimpleParticleType) TYPE_TO_LEAF_PARTICLE.get(type))
                ).setRegistryName(ImmersiveWeathering.res(name));
                registry.register(block);
                LEAF_TO_TYPE.put(block, type);
            }
        }
        LEAF_TO_TYPE.forEach((a, b) -> TYPE_TO_LEAF.put(b, a));
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
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.OAK_LEAF.get(), LeavesTypeRegistry.fromNBT("oak"));
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.BIRCH_LEAF.get(), LeavesTypeRegistry.fromNBT("birch"));
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.SPRUCE_LEAF.get(), LeavesTypeRegistry.fromNBT("spruce"));
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.JUNGLE_LEAF.get(), LeavesTypeRegistry.fromNBT("jungle"));
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.ACACIA_LEAF.get(), LeavesTypeRegistry.fromNBT("acacia"));
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.DARK_OAK_LEAF.get(), LeavesTypeRegistry.fromNBT("dark_oak"));
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.AZALEA_LEAF.get(), LeavesTypeRegistry.fromNBT("azalea"));
        LEAF_PARTICLE_TO_TYPE.put(ModParticles.AZALEA_FLOWER.get(), LeavesTypeRegistry.fromNBT("flowering_azalea"));

        for (LeavesType type : LeavesTypeRegistry.LEAVES_TYPES.values()) {
            if (!type.isVanilla()) {
                String name = type.getNamespace() + "/" + type.getTypeName() + "_leaf";
                var o = new SimpleParticleType(true).setRegistryName(name);
                event.getRegistry().register(o);
                LEAF_PARTICLE_TO_TYPE.put(o, type);
            }
        }
        LEAF_PARTICLE_TO_TYPE.forEach((a, b) -> TYPE_TO_LEAF_PARTICLE.put(b, a));
    }

    public static void init(IEventBus bus) {
        BlockSetManager.addBlockSetRegistrationCallback(ModDynamicRegistry::registerLeafPiles, Block.class, LeavesType.class);
        BlockSetManager.addBlockSetRegistrationCallback(ModDynamicRegistry::registerLeafPilesItems, Item.class, LeavesType.class);
        BlockSetManager.addBlockSetRegistrationCallback(ModDynamicRegistry::registerBarks, Item.class, WoodType.class);

        bus.addGenericListener(ParticleType.class, ModDynamicRegistry::registerLeafPilesParticles);


        var serverRes = new ServerDynamicResourcesHandler();
        serverRes.register(bus);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            var clientRes = new ClientDynamicResourcesHandler();

            clientRes.register(bus);

            bus.addListener(ModDynamicRegistry::initClient);
            bus.addListener(ModDynamicRegistry::registerParticlesRenderers);
        });
    }

    public static void initClient(FMLClientSetupEvent bus) {

    }

    public static void registerParticlesRenderers(ParticleFactoryRegisterEvent event) {
        for (var e : LEAF_PARTICLE_TO_TYPE.entrySet()) {
            if (!e.getValue().isVanilla()) {
                Minecraft.getInstance().particleEngine.register(e.getKey(),
                        LeafParticle.ColoredLeafParticle::new);
            }
        }
    }


}
