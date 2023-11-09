package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;

public class MossMultifaceBlock extends GlowLichenBlock {
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public MossMultifaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return !useContext.getItemInHand().is(ModItems.MOSS_CLUMP.get()) || super.canBeReplaced(state, useContext);
    }
}
