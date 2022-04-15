package com.ordana.immersive_weathering.integration.dynamic_stuff;

import com.google.common.collect.ImmutableMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.common.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.common.blocks.ModBlocks;
import com.ordana.immersive_weathering.common.items.LeafPileBlockItem;
import net.mehvahdjukaar.selene.block_set.BlockSetManager;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesType;
import net.mehvahdjukaar.selene.block_set.leaves.LeavesTypeRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModDynamicRegistry {

    public static final Map<LeafPileBlock, LeavesType> LEAF_TO_TYPE = new LinkedHashMap<>();
    public static final Map<LeavesType, LeafPileBlock> TYPE_TO_LEAF = new LinkedHashMap<>();

    public static final Map<Item, LeavesType> LEAF_PILES_ITEMS = new LinkedHashMap<>();


    public static Map<Block, LeafPileBlock> getLeafToLeafPileMap() {
        var builder = ImmutableMap.<Block, LeafPileBlock>builder();
        LEAF_TO_TYPE.forEach((key, value) -> builder.put(value.leaves, key));
        return builder.build();
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
                        false, false, true, List.of(ModParticles.OAK_LEAF)
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

    public static void init() {
        BlockSetManager.addBlockSetRegistrationCallback(ModDynamicRegistry::registerLeafPiles, Block.class, LeavesType.class);
        BlockSetManager.addBlockSetRegistrationCallback(ModDynamicRegistry::registerLeafPilesItems, Item.class, LeavesType.class);
    }

    public static void initClient(FMLClientSetupEvent bus){

    }

}
