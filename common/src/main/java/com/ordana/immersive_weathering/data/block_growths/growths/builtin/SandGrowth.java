package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.util.WeatheringHelper;
import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SandGrowth extends BuiltinBlockGrowth {

    protected SandGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    @Override
    public @Nullable Iterable<Block> getOwners() {
        List<Block> blocks = new ArrayList<>();
        Registry.BLOCK.getTag(ModTags.SANDABLE).get().stream().forEach(h -> blocks.add(h.value()));
        return blocks;
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        if (level.isRaining() && biome.is(BiomeTags.HAS_DESERT_PYRAMID)) {
            BlockPos downPos = pos.below();
            BlockState downBlock = level.getBlockState(downPos);
            var sandyBlock = WeatheringHelper.getSandyBlock(state).orElse(null);
            level.setBlockAndUpdate(pos, sandyBlock.getBlock().withPropertiesOf(state));
            if (WeatheringHelper.isRandomWeatheringPos(downPos) && downBlock.is(ModTags.SANDABLE)) {
                var sandyDownBlock = WeatheringHelper.getSandyBlock(downBlock).orElse(null);
                assert sandyDownBlock != null;
                level.setBlockAndUpdate(pos.above(), ModBlocks.SAND_LAYER_BLOCK.get().defaultBlockState());
                level.setBlockAndUpdate(pos, sandyBlock.getBlock().withPropertiesOf(state).setValue(ModBlockProperties.SANDINESS, 1));
                level.setBlockAndUpdate(downPos, sandyDownBlock.getBlock().withPropertiesOf(downBlock));
            }
        }
    }
}
