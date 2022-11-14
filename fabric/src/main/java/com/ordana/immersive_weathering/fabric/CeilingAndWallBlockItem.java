package com.ordana.immersive_weathering.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import org.jetbrains.annotations.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public class CeilingAndWallBlockItem extends BlockItem {
    protected final Supplier<Block> wallBlock;

    public CeilingAndWallBlockItem(Block ceilingBlock, Supplier<Block> wallBlock, Properties properties) {
        super(ceilingBlock, properties);
        this.wallBlock = wallBlock;
    }


    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.wallBlock.get().getStateForPlacement(context);
        BlockState state = null;
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction != Direction.DOWN) {
                BlockState blockstate2 = direction == Direction.UP ? this.getBlock().getStateForPlacement(context) : blockstate;
                if (blockstate2 != null && blockstate2.canSurvive(levelreader, blockpos)) {
                    state = blockstate2;
                    break;
                }
            }
        }

        return state != null && levelreader.isUnobstructed(state, blockpos, CollisionContext.empty()) ? state : null;
    }

    @Override
    public void registerBlocks(Map<Block, Item> map, Item item) {
        super.registerBlocks(map, item);
        //cant do...
        //map.put(this.wallBlock.get(), item);
    }

}
