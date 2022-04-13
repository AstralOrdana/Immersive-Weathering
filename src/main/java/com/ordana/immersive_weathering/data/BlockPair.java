package com.ordana.immersive_weathering.data;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

//a pair of 2 blocks, useful for double grass
public class BlockPair extends Pair<BlockState, BlockState> {

    private static final Codec<Block> BLOCK_CODEC = Identifier.CODEC.flatXmap(
            (resourceLocation) -> Registry.BLOCK.getOrEmpty(resourceLocation).map(DataResult::success).orElseGet(
                    () -> DataResult.error("Unknown registry key in " + Registry.BLOCK.getKey() + ": " + resourceLocation)
            ),
            (block) -> Registry.BLOCK.getKey(block).map(RegistryKey::getValue).map(DataResult::success).orElseGet(
                    () -> DataResult.error("Unknown registry element in " + Registry.BLOCK.getKey() + ":" + block)
            )
    );

    private static final Codec<BlockState> BLOCK_STATE_CODEC = BlockStateAccessor.getCodec(BLOCK_CODEC, Block::getDefaultState).stable();

    public static final Codec<BlockPair> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BLOCK_STATE_CODEC.fieldOf("block").forGetter(Pair::getFirst),
            BLOCK_STATE_CODEC.optionalFieldOf("above_block").forGetter(p -> Optional.ofNullable(p.getSecond()))
    ).apply(instance, (f, s) -> new BlockPair(f, s.orElse(null))));


    public BlockPair(@Nonnull BlockState first, @Nullable BlockState second) {
        super(first, second);
    }

    public static BlockPair of(@Nonnull final BlockState first,@Nullable final BlockState second) {
        return new BlockPair(first, second);
    }

    public static BlockPair of(@Nonnull final BlockState first) {
        return of(first, null);
    }

    public static BlockPair of(@Nonnull final Block first) {
        return of(first.getDefaultState(), null);
    }

    public boolean isDouble(){
        return this.getSecond()!=null;
    }


    public static class BlockStateAccessor extends BlockState {

        public BlockStateAccessor(Block block, ImmutableMap<Property<?>, Comparable<?>> map, MapCodec<BlockState> mapCodec) {
            super(block, map, mapCodec);
        }

        public static Codec<BlockState> getCodec(Codec<Block> oCodec, Function<Block, BlockState> osFunction) {
            return createCodec(oCodec, osFunction);
        }
    }
}