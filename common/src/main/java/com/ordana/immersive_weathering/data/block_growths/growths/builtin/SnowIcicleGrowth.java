package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.blocks.IcicleBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnowIcicleGrowth extends BuiltinBlockGrowth {

    public SnowIcicleGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        if (WeatheringHelper.isIciclePos(pos)) {
            BlockPos p = pos.below(state.is(BlockTags.SNOW) ? 2 : 1);
            BlockState placement = ModBlocks.ICICLE.get().defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, Direction.DOWN);
            if (level.getBlockState(p).isAir() && placement.canSurvive(level, p)) {
                if (Direction.Plane.HORIZONTAL.stream().anyMatch(d -> {
                    BlockPos rel = p.relative(d);
                    return level.canSeeSky(rel) && level.getBlockState(rel).isAir();
                })) {
                    level.setBlockAndUpdate(p, placement);
                }
            }
        }
    }


}
