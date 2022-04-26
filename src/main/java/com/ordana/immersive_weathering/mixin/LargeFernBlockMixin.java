package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(TallPlantBlock.class)
public class LargeFernBlockMixin extends PlantBlock {

    public LargeFernBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND) || floor.isIn(ModTags.CRACKED);
    }
}
