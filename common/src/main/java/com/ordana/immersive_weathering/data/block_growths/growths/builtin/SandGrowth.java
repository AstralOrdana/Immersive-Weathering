package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.blocks.sandy.Sandy;
import com.ordana.immersive_weathering.blocks.snowy.Snowy;
import com.ordana.immersive_weathering.util.TemperatureManager;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SandGrowth extends BuiltinBlockGrowth {

    protected SandGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    @Override
    public @Nullable Iterable<Block> getOwners() {
        List<Block> blocks = new ArrayList<>();
        Registry.BLOCK.getTag(ModTags.SANDABLE).get().stream().forEach(h -> blocks.add(h.value()));
        Registry.BLOCK.getTag(ModTags.SANDY).get().stream().forEach(h -> blocks.add(h.value()));
        return blocks;
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        if (TemperatureManager.hasSandstorm(level, pos, biome)) {
            var sandyBlock = Sandy.getSandy(state);

            RandomSource random = level.random;
            int rand = random.nextInt(10);
            if (state.is(ModTags.SANDY) && state.getValue(ModBlockProperties.SANDINESS) == 0 && Sandy.isRandomSandyPos(pos)) level.setBlockAndUpdate(pos, state.setValue(ModBlockProperties.SANDINESS, 1).setValue(ModBlockProperties.SAND_AGE, rand));

            else if (sandyBlock.isPresent()) {
                BlockPos downPos = pos.below();
                BlockState downBlock = level.getBlockState(downPos);

                if (WeatheringHelper.isRandomWeatheringPos(downPos)){
                    var sandyBlock2 = Sandy.getSandy(downBlock);
                    if (sandyBlock2.isPresent()) {
                        level.setBlockAndUpdate(pos, sandyBlock.get().setValue(ModBlockProperties.SANDINESS, 1));
                        level.setBlockAndUpdate(pos.below(), sandyBlock2.get().setValue(ModBlockProperties.SANDINESS, 0));
                        level.setBlockAndUpdate(pos.above(), ModBlocks.SAND_LAYER_BLOCK.get().defaultBlockState());
                    }
                }
                else level.setBlockAndUpdate(pos, sandyBlock.get());
            }
        }
    }
}
