package com.ordana.immersive_weathering.forge;

import com.ordana.immersive_weathering.blocks.soil.ModFarmlandBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class CeilingAndWallBlockItem extends BlockItem {
    protected final Block wallBlock;

    public CeilingAndWallBlockItem(Block ceilingBlock, Block wallBlock, Properties properties) {
        super(ceilingBlock, properties);
        this.wallBlock = wallBlock;
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockstate = this.wallBlock.getStateForPlacement(context);
        BlockState blockstate1 = null;
        LevelReader levelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction != Direction.DOWN) {
                BlockState blockstate2 = direction == Direction.UP ? this.getBlock().getStateForPlacement(context) : blockstate;
                if (blockstate2 != null && blockstate2.canSurvive(levelreader, blockpos)) {
                    blockstate1 = blockstate2;
                    break;
                }
            }
        }

        return blockstate1 != null && levelreader.isUnobstructed(blockstate1, blockpos, CollisionContext.empty()) ? blockstate1 : null;
    }

    @Override
    public void registerBlocks(Map<Block, Item> map, Item item) {
        super.registerBlocks(map, item);
        map.put(this.wallBlock, item);
    }

    @Override
    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(this.wallBlock);
    }
}
