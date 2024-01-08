package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.data.block_growths.growths.IBlockGrowth;
import com.ordana.immersive_weathering.util.StrOpt;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class BuiltinBlockGrowth implements IBlockGrowth {

    public static final Codec<BuiltinBlockGrowth> CODEC =
            Codec.STRING.partialDispatch("builtin", b -> DataResult.success(b.getName()),
                    n -> {
                        var factory =
                                BuiltinGrowthsRegistry.BUILTIN_GROWTHS.get(n);
                        if (factory == null) {
                            return DataResult.error(() -> "No builtin growth found with id " + n);
                        }
                        Codec<BuiltinBlockGrowth> codec = RecordCodecBuilder.create(i -> i.group(
                                StrOpt.of(TickSource.CODEC.listOf(), "tick_sources", List.of(TickSource.BLOCK_TICK))
                                        .forGetter(b -> b.sources),
                                StrOpt.of(RegistryCodecs.homogeneousList(Registries.BLOCK), "owners")
                                        .forGetter(b -> Optional.ofNullable(b.owners)),
                                StrOpt.of(Codec.FLOAT, "growth_chance", 1f).forGetter(b -> b.growthChance)
                        ).apply(i, (o, s, c) -> factory.create(n, s.orElse(null), o, c)));
                        return DataResult.success(codec);
                    });


    private final HolderSet<Block> owners;
    private final String name;
    private final List<TickSource> sources;
    protected final float growthChance;

    protected BuiltinBlockGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources, float chance) {
        this.owners = owners;
        this.name = name;
        this.sources = sources;
        this.growthChance = chance;
    }

    public String getName() {
        return name;
    }

    @Override
    public @Nullable Iterable<? extends Block> getOwners() {
        if (owners == null) return null;
        return this.owners.stream().map(Holder::value).toList();
    }

    @Override
    public final Collection<TickSource> getTickSources() {
        return sources;
    }
}
