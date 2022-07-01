package com.ordana.immersive_weathering.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RegistryPlatform {

    @ExpectPlatform
    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Feature<?>> Supplier<T> registerFeature(String name, Supplier<T> feature) {
        throw new AssertionError();
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory,
                                                                                MobCategory category, float width, float height) {
        return registerEntityType(name, factory, category, width, height, 5);
    }


    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory,
                                                                                MobCategory category, float width,
                                                                                float height, int clientTrackingRange) {
        return registerEntityType(name, factory, category, width, height, clientTrackingRange, 3);
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory,
                                                                                MobCategory category, float width, float height,
                                                                                int clientTrackingRange, int updateInterval) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntityType<E>, E extends BlockEntity> Supplier<T> registerBlockEntityType(String name, Supplier<T> blockEntity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntitySupplier<T> blockEntitySupplier, Block... validBlocks) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface BlockEntitySupplier<T extends BlockEntity> {
        @NotNull T create(BlockPos pos, BlockState state);
    }

    @ExpectPlatform
    public static Supplier<SimpleParticleType> registerParticle(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerCompostable(ItemLike name, float chance) {
        throw new AssertionError();
    }

    public enum BlockType {
        MULCH, NULCH, RUSTABLE_BARS, RUSTABLE_BLOCK, RUSTABLE_DOOR, RUSTABLE_SLAB,
        RUSTABLE_STAIRS, RUSTABLE_TRAPDOOR, RUSTABLE_VERTICAL_SLAB;
    }

    @ExpectPlatform
    public static Block createSpecialBlock(BlockType type, BlockBehaviour.Properties properties, Object ...extraParams) {
        throw new AssertionError();
    }


    @ExpectPlatform //fabric
    public static void registerItemBurnTime(Item item, int burnTime) {
        throw new AssertionError();
    }

    @ExpectPlatform //fabric
    public static void registerBlockFlammability(Block item, int fireSpread, int flammability) {
        throw new AssertionError();
    }

}



