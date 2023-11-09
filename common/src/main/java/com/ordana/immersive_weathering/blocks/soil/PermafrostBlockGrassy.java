package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class PermafrostBlockGrassy extends BaseSoilBlock {
    public PermafrostBlockGrassy(Properties properties) {
        super(properties);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canBeGrass(state, level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.PERMAFROST.get().defaultBlockState());
        }
        super.randomTick(state, level, pos, random);
    }
}
