package com.ordana.immersive_weathering.common.blocks.frostable;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Random;

public class FrostyGrassBlock extends BushBlock implements Frosty {

    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public FrostyGrassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(NATURAL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NATURAL);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XYZ;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(NATURAL)) {
            if (world.dimensionType().ultraWarm() || (!world.isRaining() && world.isDay()) || (world.getBrightness(LightLayer.BLOCK, pos) > 7 - state.getLightBlock(world, pos))) {
                if (state.is(ModBlocks.FROSTY_GRASS)) {
                    world.setBlockAndUpdate(pos, Blocks.GRASS.defaultBlockState());
                } else if (state.is(ModBlocks.FROSTY_FERN)) {
                    world.setBlockAndUpdate(pos, Blocks.FERN.defaultBlockState());
                }
            }
        }
    }
}
