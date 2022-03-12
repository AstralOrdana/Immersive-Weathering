package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.crackable.CrackSpreader;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class CrackableMossableWallBlock extends MossableWallBlock implements CrackableMossable {

    private final CrackLevel crackLevel;

    public CrackableMossableWallBlock(MossLevel mossLevel, CrackLevel crackLevel, Settings settings) {
        super(mossLevel, settings);
        this.crackLevel = crackLevel;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld serverLevel, BlockPos pos, Random random) {
        float weatherChance = 0.1f;
        if (random.nextFloat() < weatherChance) {
            boolean isMoss = this.getMossSpreader().getWanderWeatheringState(true, pos, serverLevel);
            Optional<BlockState> opt = Optional.empty();
            if(isMoss) {
                opt = this.getNextMossy(state);
            } else if(this.getCrackSpreader().getWanderWeatheringState(true, pos, serverLevel)){
                opt = this.getNextCracked(state);
            }
            BlockState newState = opt.orElse(state.with(WEATHERABLE,false));
            serverLevel.setBlockState(pos, newState);
        }
    }

    @Override
    public CrackLevel getCrackLevel() {
        return crackLevel;
    }

}
