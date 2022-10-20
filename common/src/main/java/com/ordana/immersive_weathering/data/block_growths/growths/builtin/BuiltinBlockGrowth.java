package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.block_growths.growths.IBlockGrowth;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BuiltinBlockGrowth implements IBlockGrowth {

    public static final Codec<BuiltinBlockGrowth> CODEC =
            Codec.STRING.partialDispatch("builtin", b -> DataResult.success(b.getName()),
                    n -> {
                        var factory =
                                BuiltinGrowthsRegistry.BUILTIN_GROWTHS.get(n);
                        if (factory == null) {
                            return DataResult.error("Target Block has no properties");
                        }
                        Codec<BuiltinBlockGrowth> codec = RecordCodecBuilder.create(i -> i.group(
                                TickSource.CODEC.listOf().optionalFieldOf("tick_sources",List.of(TickSource.BLOCK_TICK))
                                        .forGetter(b -> b.sources),
                                RegistryCodecs.homogeneousList(Registry.BLOCK_REGISTRY).optionalFieldOf("owners")
                                        .forGetter(b -> Optional.ofNullable(b.owners))
                                ).apply(i, (o, s) -> factory.create(n, s.orElse(null), o)));
                        return DataResult.success(codec);
                    });


    private final HolderSet<Block> owners;
    private final String name;
    private final List<TickSource> sources;

    protected BuiltinBlockGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        this.owners = owners;
        this.name = name;
        this.sources = sources;
    }

    public String getName() {
        return name;
    }

    @Override
    public @Nullable Iterable<? extends Block> getOwners() {
        if (owners == null) return null;
        return this.owners.stream().map(Holder::value).collect(Collectors.toList());
    }

    @Override
    public final Collection<TickSource> getTickSources() {
        return sources;
    }
}
