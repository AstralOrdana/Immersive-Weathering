package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SnowGrowth extends BuiltinBlockGrowth {

    protected SnowGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    @Override
    public @Nullable Iterable<Block> getOwners() {
        List<Block> blocks = new ArrayList<>();
        Registry.BLOCK.getTag(ModTags.SNOWABLE).get().stream().forEach(h -> blocks.add(h.value()));
        return blocks;
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        if (level.isRaining() && biome.is(ModTags.ICY)) {
            BlockPos downPos = pos.below();
            BlockState downBlock = level.getBlockState(downPos);
            var snowyBlock = WeatheringHelper.getSnowyBlock(state).orElse(null);
            level.setBlockAndUpdate(pos, snowyBlock.getBlock().withPropertiesOf(state));
            if (WeatheringHelper.isWeatherPos(downPos) && downBlock.is(ModTags.SNOWABLE) && !downBlock.is(Blocks.STONE)) {
                var snowyDownBlock = WeatheringHelper.getSnowyBlock(downBlock).orElse(null);
                level.setBlockAndUpdate(pos.below(), snowyDownBlock.getBlock().withPropertiesOf(downBlock));
            }
        }
    }
}
