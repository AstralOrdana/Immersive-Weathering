package com.ordana.immersive_weathering.platform.fabric;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.fabric.ModRegistry;
import com.ordana.immersive_weathering.fabric.MulchBlock;
import com.ordana.immersive_weathering.fabric.NulchBlock;
import com.ordana.immersive_weathering.fabric.rustable.*;
import com.ordana.immersive_weathering.platform.RegistryPlatform;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.function.Supplier;

public class RegistryPlatformImpl {

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        return ModRegistry.BLOCKS.add(() -> Registry.register(Registry.BLOCK, ImmersiveWeathering.res(name), block.get()));
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        return ModRegistry.ITEMS.add(() -> Registry.register(Registry.ITEM, ImmersiveWeathering.res(name), item.get()));
    }

    public static <T extends BlockEntityType<E>, E extends BlockEntity> Supplier<T> registerBlockEntityType(String name, Supplier<T> blockEntity) {
        return ModRegistry.BLOCK_ENTITIES.add(() -> Registry.register(Registry.BLOCK_ENTITY_TYPE, ImmersiveWeathering.res(name), blockEntity.get()));
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange, int updateInterval) {
        return ModRegistry.ENTITIES.add(() -> Registry.register(Registry.ENTITY_TYPE, ImmersiveWeathering.res(name),
                EntityType.Builder.of(factory, category).sized(width, height).build(name)));
    }

    public static <T extends Feature<?>> Supplier<T> registerFeature(String name, Supplier<T> feature) {
        return ModRegistry.FEATURES.add(() -> Registry.register(Registry.FEATURE, ImmersiveWeathering.res(name), feature.get()));
    }

    public static Supplier<SimpleParticleType> registerParticle(String name) {
        return ModRegistry.PARTICLES.add(() -> Registry.register(Registry.PARTICLE_TYPE,
                ImmersiveWeathering.res(name), FabricParticleTypes.simple()));
    }

    @SuppressWarnings("unchecked")
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
            case RUSTABLE_VERTICAL_SLAB ->
                    new RustableVerticalSlabBlock((Rustable.RustLevel) extraParams[0], properties);
        };
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(RegistryPlatform.BlockEntitySupplier<T> blockEntitySupplier, Block... validBlocks) {
        return FabricBlockEntityTypeBuilder.create(blockEntitySupplier::create, validBlocks).build();
    }

    public static void registerItemBurnTime(Item item, int burnTime) {
        FuelRegistry.INSTANCE.add(item, burnTime);
    }

    public static void registerBlockFlammability(Block item, int fireSpread, int flammability) {
        FlammableBlockRegistry.getDefaultInstance().add(item, fireSpread, flammability);
    }

    public static void registerCompostable(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item, chance);
    }


}
