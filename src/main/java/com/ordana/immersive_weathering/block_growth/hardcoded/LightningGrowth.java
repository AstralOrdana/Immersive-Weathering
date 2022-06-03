package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.block_growth.IBlockGrowth;
import com.ordana.immersive_weathering.block_growth.TickSource;
import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.ModTags;
import com.ordana.immersive_weathering.common.blocks.crackable.Crackable;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LightningGrowth implements IBlockGrowth {

    @Override
    public @Nullable Iterable<Block> getOwners() {
        List<Block> blocks = new ArrayList<>();
        Registry.BLOCK.getTag(BlockTags.SAND).get().stream().forEach(h -> blocks.add(h.value()));
        Registry.BLOCK.getTag(ModTags.CRACKABLE).get().stream().forEach(h -> blocks.add(h.value()));
        return blocks;
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        onLightningHit(pos, level, 0);
    }

    public void onLightningHit(BlockPos centerPos, Level level, int rec) {
        if (rec == 0 && !ServerConfigs.VITRIFIED_LIGHTNING.get()) return;


        this.convert(level, centerPos);
        if (rec >= 5) return;

        rec++;
        float decrement = 0.7f;
        double p = Math.pow(decrement, rec);
        if (rec == 0 || level.random.nextFloat() < 1 * p) {
            BlockPos downPos = centerPos.below();
            if (isValidTarget(level, downPos)) {
                onLightningHit(downPos, level, rec);
            }
        }
        for (BlockPos target : BlockPos.withinManhattan(centerPos, 1, 0, 1)) {
            if (level.random.nextFloat() < 0.3 * p && target != centerPos) {
                if (isValidTarget(level, target)) {
                    onLightningHit(target, level, rec);
                }
            }
        }
    }

    private boolean isValidTarget(Level level, BlockPos pos){
        var state = level.getBlockState(pos);
        return state.is(ModTags.CRACKABLE) || state.is(BlockTags.SAND);
    }

    private void convert(Level level, BlockPos pos){
        var state = level.getBlockState(pos);
        if(state instanceof Crackable){
            level.setBlock(pos, Crackable.getCrackedBlock(state),3);
        }else if(state.is(BlockTags.SAND)){
            level.setBlock(pos, ModBlocks.VITRIFIED_SAND.get().defaultBlockState(),3);
        }
    }

    @Override
    public Collection<TickSource> getTickSources() {
        return List.of(TickSource.LIGHTNING);
    }
}
