package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.FrostyGrassBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(FernBlock.class)
public class FernBlockMixin extends PlantBlock {
    public FernBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND) || floor.isIn(ModTags.CRACKED);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(ImmersiveWeathering.getConfig().fireAndIceConfig.grassFrosting) {
            if (state.isOf(Blocks.GRASS)) {
                if ((world.isRaining() || world.isNight()) && world.getBiome(pos).isIn(ModTags.ICY) && (world.getLightLevel(LightType.BLOCK, pos) < 7 - state.getOpacity(world, pos))) {
                    world.setBlockState(pos, ModBlocks.FROSTY_GRASS.getDefaultState().with(FrostyGrassBlock.NATURAL, true));
                }
            }
            else if (state.isOf(Blocks.FERN)) {
                if ((world.isRaining() || world.isNight()) && world.getBiome(pos).isIn(ModTags.ICY) && (world.getLightLevel(LightType.BLOCK, pos) < 7 - state.getOpacity(world, pos))) {
                    world.setBlockState(pos, ModBlocks.FROSTY_FERN.getDefaultState().with(FrostyGrassBlock.NATURAL, true));
                }
            }
        }
    }
}
