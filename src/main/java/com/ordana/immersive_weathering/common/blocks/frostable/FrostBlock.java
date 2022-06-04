package com.ordana.immersive_weathering.common.blocks.frostable;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import java.util.Random;

public class FrostBlock extends MultifaceBlock implements Frosty {
    public FrostBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(NATURAL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            if (!this.isFaceSupported(direction)) continue;
            builder.add(MultifaceBlock.getFaceProperty(direction));
        }
        builder.add(NATURAL);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.getItemInHand().is(ModItems.FROST) || super.canBeReplaced(state, context);
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(NATURAL)) {
            if (world.dimensionType().ultraWarm() || (!world.isRaining() && world.isDay()) || (world.getBrightness(LightLayer.BLOCK, pos) > 7 - state.getLightBlock(world, pos))) {
                world.removeBlock(pos, false);
            }
        }
    }
}
