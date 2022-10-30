package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.blocks.FulguriteBlock;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.items.LeafPileBlockItem;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LightningGrowth extends BuiltinBlockGrowth {


    public LightningGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    //TODO: move to data
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
        if (rec == 0 && !CommonConfigs.VITRIFIED_LIGHTNING.get()) return;


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

    private boolean isValidTarget(Level level, BlockPos pos) {
        var state = level.getBlockState(pos);
        return state.is(ModTags.CRACKABLE) || state.is(BlockTags.SAND);
    }

    public static final SimpleWeightedRandomList<Direction> list = SimpleWeightedRandomList.<Direction>builder().add(Direction.UP, 7)
            .add(Direction.DOWN, 1).add(Direction.NORTH, 1)
            .add(Direction.EAST, 1).add(Direction.WEST, 1)
            .add(Direction.SOUTH, 1).build();

    private void convert(Level level, BlockPos pos) {

        var state = level.getBlockState(pos);
        if (state instanceof Crackable) {
            level.setBlock(pos, Crackable.getCrackedBlock(state), 3);
        } else if (state.is(BlockTags.SAND)) {
            level.setBlock(pos, ModBlocks.VITRIFIED_SAND.get().defaultBlockState(), 3);
            if (level.random.nextFloat() < CommonConfigs.FULGURITE_CHANCE.get()) {
                var dir = list.getRandom(level.random).get().getData();
                var offset = pos.relative(dir);
                if(level.getBlockState(offset).isAir()){
                    level.setBlock(offset, ModBlocks.FULGURITE.get().defaultBlockState()
                            .setValue(FulguriteBlock.FACING, dir).setValue(FulguriteBlock.POWERED, true),3);
                };
            }
        }
    }

}
