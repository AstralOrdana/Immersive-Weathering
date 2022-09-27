package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class MossMultifaceBlock extends GlowLichenBlock {

    public MossMultifaceBlock(Properties properties) {
        super(properties);
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return !useContext.getItemInHand().is(ModItems.MOSS_CLUMP.get()) || super.canBeReplaced(state, useContext);
    }
}
