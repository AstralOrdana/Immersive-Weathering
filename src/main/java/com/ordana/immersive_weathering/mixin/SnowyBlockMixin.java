package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(SnowyDirtBlock.class)
public abstract class SnowyBlockMixin extends Block {

    public SnowyBlockMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return BlockGrowthHandler.canRandomTick(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        //TODO: move to data (this isnt doing anything now)
        if (state.is(Blocks.PODZOL)) {
            var targetPos = pos.above();
            if (random.nextFloat() < 0.001f && world.getBlockState(targetPos).isAir()) {
                if (!world.isAreaLoaded(pos, 4)) return;
                if (!WeatheringHelper.hasEnoughBlocksAround(pos, 4, 3, 4, world,
                        p -> p.getBlock() == Blocks.FERN, 8)) {
                    world.setBlock(targetPos, Blocks.FERN.defaultBlockState(), 2);
                }
            }
        }
    }
}
