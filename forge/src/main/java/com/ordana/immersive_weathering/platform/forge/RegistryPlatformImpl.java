package com.ordana.immersive_weathering.platform.forge;

import com.ordana.immersive_weathering.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.forge.ModRegistry;
import com.ordana.immersive_weathering.forge.MulchBlock;
import com.ordana.immersive_weathering.forge.NulchBlock;
import com.ordana.immersive_weathering.forge.rustable.*;
import com.ordana.immersive_weathering.platform.RegistryPlatform;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.function.Supplier;

public class RegistryPlatformImpl {

    public static void registerCompostable(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item, chance);
    }

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        return ModRegistry.BLOCKS.register(name, block);
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
       return ModRegistry.ITEMS.register(name,item);
    }

    public static <T extends BlockEntityType<E>, E extends BlockEntity> Supplier<T> registerBlockEntityType(String name, Supplier<T> blockEntity) {
        return ModRegistry.BLOCK_ENTITIES.register(name, blockEntity);
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange, int updateInterval) {
        return ModRegistry.ENTITIES.register(name, () -> EntityType.Builder.of(factory, category)
                .sized(width, height).build(name));
    }

    public static <T extends Feature<?>>  Supplier<T> registerFeature(String name, Supplier<T> feature) {
        return ModRegistry.FEATURES.register(name, feature);
    }

    public static Supplier<SimpleParticleType> registerParticle(String name) {
        return ModRegistry.PARTICLES.register(name, ()->new SimpleParticleType(true));

    }

    public static Block createSpecialBlock(RegistryPlatform.BlockType type, BlockBehaviour.Properties properties, Object... extraParams) {
        return switch (type) {
            case MULCH -> new MulchBlock(properties);
            case NULCH -> new NulchBlock(properties);
            case RUSTABLE_BLOCK -> new RustableBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_STAIRS ->
                    new RustableStairsBlock((Rustable.RustLevel) extraParams[0], (Supplier<Block>) extraParams[1], properties);
            case RUSTABLE_BARS -> new RustableBarsBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_DOOR -> new RustableDoorBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_SLAB -> new RustableSlabBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_TRAPDOOR -> new RustableTrapdoorBlock((Rustable.RustLevel) extraParams[0], properties);
            case RUSTABLE_VERTICAL_SLAB -> new RustableVerticalSlabBlock((Rustable.RustLevel) extraParams[0], properties);
        };
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(RegistryPlatform.BlockEntitySupplier<T> blockEntitySupplier, Block... validBlocks) {
        return BlockEntityType.Builder.of(blockEntitySupplier::create, validBlocks).build(null);
    }


    public static void registerItemBurnTime(Item item, int burnTime) {
    }

    public static void registerBlockFlammability(Block item, int fireSpread, int flammability) {
    }
}
