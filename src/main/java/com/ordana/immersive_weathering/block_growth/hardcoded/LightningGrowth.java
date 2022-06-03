package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.block_growth.IBlockGrowth;
import com.ordana.immersive_weathering.block_growth.TickSource;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.WeatheringHelper;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LightningGrowth implements IBlockGrowth {
    @Override
    public @Nullable Iterable<Block> getOwners() {
        return Registry.BLOCK.getTag(BlockTags.SAND).get().stream().map(Holder::value).collect(Collectors.toList());
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        onLightningHit(pos, level, 0);
    }

    public static void onLightningHit(BlockPos centerPos, Level level, int rec) {
        if (rec == 0 && !ServerConfigs.VITRIFIED_LIGHTNING.get()) return;

        BlockState vitrified = ModBlocks.VITRIFIED_SAND.get().defaultBlockState();
        level.setBlockAndUpdate(centerPos, vitrified);
        if (rec >= 5) return;

        rec++;
        float decrement = 0.7f;
        double p = Math.pow(decrement, rec);
        if (rec == 0 || level.random.nextFloat() < 1 * p) {
            BlockPos downPos = centerPos.below();
            if (level.getBlockState(downPos).is(BlockTags.SAND)) {
                onLightningHit(downPos, level, rec);
            }
        }
        for (BlockPos target : BlockPos.withinManhattan(centerPos, 1, 0, 1)) {
            if (level.random.nextFloat() < 0.3 * p && target != centerPos) {
                if (level.getBlockState(target).is(BlockTags.SAND)) {
                    onLightningHit(target, level, rec);
                }
            }
        }

    }

    @Override
    public Collection<TickSource> getTickSources() {
        return List.of(TickSource.LIGHTNING);
    }
}
