package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.blocks.snowy.Snowy;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.reg.ModTags;
import com.ordana.immersive_weathering.util.TemperatureManager;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
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
        return new ArrayList<>(Snowy.NORMAL_TO_SNOWY.get().keySet());
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        if (TemperatureManager.snowGrowthCanGrowSnowyBlock(pos, biome)) {
            var snowyBlock = Snowy.getSnowy(state);
            if (snowyBlock.isPresent()) {
                level.setBlockAndUpdate(pos, snowyBlock.get());
                BlockPos downPos = pos.below();
                BlockState downBlock = level.getBlockState(downPos);

                if (WeatheringHelper.isRandomWeatheringPos(downPos)) {
                    var snowyBlock2 = Snowy.getSnowy(downBlock);
                    if(downBlock.is(ModTags.DOUBLE_SNOWABLE) && snowyBlock2.isPresent()){
                        level.setBlockAndUpdate(pos.below(), snowyBlock2.get());
                    }
                }
            }
        }
    }


}
