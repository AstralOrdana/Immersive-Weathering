package com.ordana.immersive_weathering.data;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Function;

public class ModDataRegistries {
//add back when forge pulls that PR https://github.com/MinecraftForge/MinecraftForge/pull/8522
    /*
    public static final ResourceKey<Registry<BlockGrowthConfiguration>> GROWTH_CONFIG_REGISTRY = ResourceKey.createRegistryKey(
            ImmersiveWeathering.res("growth_configuration"));

    public static final Registry<BlockGrowthConfiguration> RULE_TEST = Registry.registerSimple(GROWTH_CONFIG_REGISTRY, (p_205960_) -> {
        return RuleTestType.ALWAYS_TRUE_TEST;
    });

    static <P extends RuleTest> RuleTestType<P> register(String p_74322_, Codec<P> p_74323_) {
        return Registry.register(Registry.RULE_TEST, p_74322_, () -> {
            return p_74323_;
        });
    }

    @SubscribeEvent
    public static void aa(NewRegistryEvent event){
        var builder = new RegistryBuilder<BlockGrowthConfiguration>();
        RegistryAccess.REGISTRIES.put()
        builder.
        event.create()
    }

    private static <E> void registerJsonCodec(ResourceKey<? extends Registry<E>> resourceKey, Codec<E> eCodec) {
        RegistryAccess.REGISTRIES.put(resourceKey, new RegistryAccess.RegistryData<>(resourceKey, eCodec, null));
    }
*/

}
