package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;

import java.util.Random;

public class FrostyGlassPaneBlock extends PaneBlock implements Frostable{
    protected FrostyGlassPaneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NATURAL, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(NATURAL);
    }
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(NATURAL)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.offset(direction);
                if (world.getDimension().ultrawarm() || (!world.isRaining() && world.isDay() && world.isSkyVisible(targetPos.offset(direction))) || (world.getLightLevel(LightType.BLOCK, pos) > 7 - state.getOpacity(world, pos))) {
                    world.setBlockState(pos, Blocks.GLASS_PANE.getStateWithProperties(state));
                    }
            }
        }
    }
}
