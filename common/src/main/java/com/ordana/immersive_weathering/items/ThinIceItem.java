package com.ordana.immersive_weathering.items;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class ThinIceItem extends BlockItem {
    public ThinIceItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        if (context.getLevel().getFluidState(context.getClickedPos()).is(Fluids.WATER)) {
            return super.getPlacementState(context);
        }
        return null;
    }
}
