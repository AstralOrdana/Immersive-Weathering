package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.crackable.CrackableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Random;

public class MossableWallBlock extends MossyWallBlock {

    public static BooleanProperty WEATHERABLE = CrackableBlock.WEATHERABLE;

    private final Mossable.MossLevel mossLevel;

    public MossableWallBlock(Mossable.MossLevel mossLevel, BlockBehaviour.Properties settings) {
        super(mossLevel, settings);
        this.mossLevel = mossLevel;
        //this.registerDefaultState(this.stateDefinition.any().setValue(WEATHERABLE, false));
    }

    //-----weathereable-start---

    @Override
    public boolean isWeathering(BlockState state) {
        return false;//state.getValue(WEATHERABLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        // stateBuilder.add(WEATHERABLE);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighbor, boolean isMoving) {
        super.onNeighborChange(state, level, pos, neighbor);
        if (level instanceof ServerLevel serverLevel) {
            boolean weathering = this.shouldStartWeathering(state, pos, serverLevel);
            if (state.getValue(WEATHERABLE) != weathering) {
                //update weathering state
                serverLevel.setBlockAndUpdate(pos, state.setValue(WEATHERABLE, weathering));
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        BlockState state = super.getStateForPlacement(placeContext);
        if (state != null) {
            boolean weathering = this.shouldStartWeathering(state, placeContext.getClickedPos(), placeContext.getLevel());
            state.setValue(WEATHERABLE, weathering);
        }
        return state;
    }

    //-----weathereable-end---


    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        float weatherChance = 0.1f;
        if (random.nextFloat() < weatherChance) {
            var opt = this.getNext(state);
            opt.ifPresent(b -> serverLevel.setBlockAndUpdate(pos, b));
        }
    }

    @Override
    public Mossable.MossLevel getAge() {
        return this.mossLevel;
    }
}
