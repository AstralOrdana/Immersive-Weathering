package com.ordana.immersive_weathering.data.block_growths;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

//a pair of 2 blocks, useful for double grass
public class BlockPair extends Pair<BlockState, BlockState> {

    private static final Codec<Block> BLOCK_CODEC = ResourceLocation.CODEC.flatXmap(
            (resourceLocation) -> Registry.BLOCK.getOptional(resourceLocation).map(DataResult::success).orElseGet(
                    () -> DataResult.error("Unknown registry key in " + Registry.BLOCK.key() + ": " + resourceLocation)
            ),
            (block) -> Registry.BLOCK.getResourceKey(block).map(ResourceKey::location).map(DataResult::success).orElseGet(
                    () -> DataResult.error("Unknown registry element in " + Registry.BLOCK.key() + ":" + block)
            )
    );
    private static final Codec<BlockState> BLOCK_STATE_CODEC = BlockStateAccessor.getCodec(BLOCK_CODEC, Block::defaultBlockState).stable();

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
        return of(first.defaultBlockState(), null);
    }

    public boolean isDouble(){
        return this.getSecond()!=null;
    }


    public static class BlockStateAccessor extends BlockState {

        public BlockStateAccessor(Block block, ImmutableMap<Property<?>, Comparable<?>> map, MapCodec<BlockState> mapCodec) {
            super(block, map, mapCodec);
        }

        public static Codec<BlockState> getCodec(Codec<Block> oCodec, Function<Block, BlockState> osFunction) {
            return codec(oCodec, osFunction);
        }
    }
}
