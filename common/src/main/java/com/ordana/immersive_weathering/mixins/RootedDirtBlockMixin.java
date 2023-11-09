package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RootedDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RootedDirtBlock.class)
public abstract class RootedDirtBlockMixin extends Block implements BonemealableBlock {

    protected RootedDirtBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, RandomSource p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        WeatheringHelper.growHangingRoots(world, random, pos);
    }


}
