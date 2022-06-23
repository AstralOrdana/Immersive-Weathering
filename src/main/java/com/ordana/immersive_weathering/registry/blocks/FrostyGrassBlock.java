package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Random;

public class FrostyGrassBlock extends PlantBlock implements Frostable {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public FrostyGrassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(NATURAL, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(NATURAL);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(NATURAL)) {
            for (Direction direction : Direction.values()) {
                var targetPos = pos.offset(direction);
                if (world.getDimension().ultrawarm() || (!world.isRaining() && world.isDay() && world.isSkyVisible(targetPos.offset(direction))) || (world.getLightLevel(LightType.BLOCK, pos) > 7 - state.getOpacity(world, pos))) {
                    if (state.isOf(ModBlocks.FROSTY_GRASS)) {
                        world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                    } else if (state.isOf(ModBlocks.FROSTY_FERN)) {
                        world.setBlockState(pos, Blocks.FERN.getDefaultState());
                    }
                }
            }
        }
    }
}
