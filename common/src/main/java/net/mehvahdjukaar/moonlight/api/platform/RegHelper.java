package net.mehvahdjukaar.moonlight.api.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.mehvahdjukaar.moonlight.api.block.ModStairBlock;
import net.mehvahdjukaar.moonlight.api.block.VerticalSlabBlock;
import net.mehvahdjukaar.moonlight.api.misc.RegSupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Helper class dedicated to platform independent registration methods
 */
public class RegHelper {

    @ExpectPlatform
    public static <T, E extends T> RegSupplier<E> register(ResourceLocation name, Supplier<E> supplier, Registry<T> reg) {
        throw new AssertionError();
    }

    /**
     * Registers stuff immediately on fabric. Normal behavior for forge
     */
    @ExpectPlatform
    public static <T, E extends T> RegSupplier<E> registerAsync(ResourceLocation name, Supplier<E> supplier, Registry<T> reg) {
        throw new AssertionError();
    }

    public static <T extends Block> RegSupplier<T> registerBlock(ResourceLocation name, Supplier<T> block) {
        return register(name, block, Registry.BLOCK);
    }

    public static <T extends Item> RegSupplier<T> registerItem(ResourceLocation name, Supplier<T> item) {
        return register(name, item, Registry.ITEM);
    }

    public static <T extends Feature<?>> RegSupplier<T> registerFeature(ResourceLocation name, Supplier<T> feature) {
        return register(name, feature, Registry.FEATURE);
    }


    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegSupplier<PlacedFeature> registerPlacedFeature(
            ResourceLocation name, RegSupplier<ConfiguredFeature<FC, F>> feature, Supplier<List<PlacementModifier>> modifiers) {
        return registerPlacedFeature(name, () -> new PlacedFeature(Holder.hackyErase(feature.getHolder()), modifiers.get()));
    }

    public static RegSupplier<PlacedFeature> registerPlacedFeature(ResourceLocation name, Supplier<PlacedFeature> featureSupplier) {
        return register(name, featureSupplier, BuiltinRegistries.PLACED_FEATURE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegSupplier<ConfiguredFeature<FC, F>> registerConfiguredFeature(
            ResourceLocation name, Supplier<F> feature, Supplier<FC> featureConfiguration) {
        return registerConfiguredFeature(name, () -> new ConfiguredFeature<>(feature.get(), featureConfiguration.get()));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> RegSupplier<ConfiguredFeature<FC, F>> registerConfiguredFeature(
            ResourceLocation name, Supplier<ConfiguredFeature<FC, F>> featureSupplier) {
        return register(name, featureSupplier, BuiltinRegistries.CONFIGURED_FEATURE);
    }

    public static <T extends SoundEvent> RegSupplier<T> registerSound(ResourceLocation name, Supplier<T> feature) {
        return register(name, feature, Registry.SOUND_EVENT);
    }

    public static <T extends MobEffect> RegSupplier<T> registerEffect(ResourceLocation name, Supplier<T> effect) {
        return register(name, effect, Registry.MOB_EFFECT);
    }

    public static <T extends Enchantment> RegSupplier<T> registerEnchantment(ResourceLocation name, Supplier<T> enchantment) {
        return register(name, enchantment, Registry.ENCHANTMENT);
    }


    public static <T extends RecipeSerializer<?>> RegSupplier<T> registerRecipeSerializer(ResourceLocation name, Supplier<T> recipe) {
        return register(name, recipe, Registry.RECIPE_SERIALIZER);
    }

    public static <T extends BlockEntityType<E>, E extends BlockEntity> RegSupplier<T> registerBlockEntityType(ResourceLocation name, Supplier<T> blockEntity) {
        return register(name, blockEntity, Registry.BLOCK_ENTITY_TYPE);
    }

    @ExpectPlatform
    public static RegSupplier<SimpleParticleType> registerParticle(ResourceLocation name) {
        throw new AssertionError();
    }

    public static <T extends Entity> RegSupplier<EntityType<T>> registerEntityType(ResourceLocation name, EntityType.EntityFactory<T> factory,
                                                                                   MobCategory category, float width, float height) {
        return registerEntityType(name, factory, category, width, height, 5);
    }

    //not needed?
    public static <T extends Entity> RegSupplier<EntityType<T>> registerEntityType(ResourceLocation name, EntityType.EntityFactory<T> factory,
                                                                                   MobCategory category, float width,
                                                                                   float height, int clientTrackingRange) {
        return registerEntityType(name, factory, category, width, height, clientTrackingRange, 3);
    }

    @ExpectPlatform
    public static <T extends Entity> RegSupplier<EntityType<T>> registerEntityType(ResourceLocation name, EntityType.EntityFactory<T> factory,
                                                                                   MobCategory category, float width, float height,
                                                                                   int clientTrackingRange, int updateInterval) {
        throw new AssertionError();
    }

    public static <T extends Entity> RegSupplier<EntityType<T>> registerEntityType(ResourceLocation name, Supplier<EntityType<T>> type) {
        return register(name, type, Registry.ENTITY_TYPE);
    }

    public static void registerCompostable(ItemLike name, float chance) {
        ComposterBlock.COMPOSTABLES.put(name, chance);
    }

    @ExpectPlatform //fabric
    public static void registerItemBurnTime(Item item, int burnTime) {
        throw new AssertionError();
    }

    @ExpectPlatform //fabric
    public static void registerBlockFlammability(Block item, int fireSpread, int flammability) {
        throw new AssertionError();
    }

    @FunctionalInterface
    public interface AttributeEvent {
        void register(EntityType<? extends LivingEntity> type, AttributeSupplier.Builder builder);
    }

    @ExpectPlatform
    public static void addAttributeRegistration(Consumer<AttributeEvent> eventListener) {
        throw new AssertionError();
    }

    public enum VariantType {
        BLOCK(Block::new),
        SLAB(SlabBlock::new),
        VERTICAL_SLAB(VerticalSlabBlock::new),
        WALL(WallBlock::new),
        STAIRS(ModStairBlock::new);
        private final BiFunction<Supplier<Block>, BlockBehaviour.Properties, Block> constructor;

        VariantType(BiFunction<Supplier<Block>, BlockBehaviour.Properties, Block> constructor) {
            this.constructor = constructor;
        }

        VariantType(Function<BlockBehaviour.Properties, Block> constructor) {
            this.constructor = (b, p) -> constructor.apply(p);
        }

        private Block create(BlockBehaviour.Properties properties, @Nullable Supplier<Block> parent) {
            return this.constructor.apply(parent, properties);
        }
    }

    public static EnumMap<VariantType, Supplier<Block>> registerFullBlockSet(ResourceLocation baseName,
                                                                             Block parentBlock, boolean isHidden) {
        return registerFullBlockSet(baseName, BlockBehaviour.Properties.copy(parentBlock), isHidden);
    }

    /**
     * Utility to register a full block set
     *
     * @return registry object map
     */
    public static EnumMap<VariantType, Supplier<Block>> registerFullBlockSet(
            ResourceLocation baseName, BlockBehaviour.Properties properties, boolean isHidden) {

        EnumMap<VariantType, Supplier<Block>> map = new EnumMap<>(VariantType.class);
        for (VariantType type : VariantType.values()) {
            String modId = baseName.getNamespace();
            String name = baseName.getPath();
            if (!type.equals(VariantType.BLOCK)) name += "_" + type.name().toLowerCase(Locale.ROOT);
            Supplier<Block> base = type != VariantType.BLOCK ? map.get(VariantType.BLOCK) : null;
            Supplier<Block> block = registerBlock(new ResourceLocation(modId, name), () -> type.create(properties, base));
            CreativeModeTab tab = switch (type) {
                case VERTICAL_SLAB ->
                        !isHidden && PlatformHelper.isModLoaded("quark") ? CreativeModeTab.TAB_BUILDING_BLOCKS : null;
                case WALL -> !isHidden ? CreativeModeTab.TAB_DECORATIONS : null;
                default -> !isHidden ? CreativeModeTab.TAB_BUILDING_BLOCKS : null;
            };
            registerItem(new ResourceLocation(modId, name), () -> new BlockItem(block.get(), (new Item.Properties()).tab(tab)));
            map.put(type, block);
        }
        return map;
    }

}



