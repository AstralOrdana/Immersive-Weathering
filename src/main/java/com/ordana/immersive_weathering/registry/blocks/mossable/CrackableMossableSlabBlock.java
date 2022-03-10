package com.ordana.immersive_weathering.registry.blocks.mossable;

import com.ordana.immersive_weathering.registry.blocks.crackable.CrackSpreader;
import com.ordana.immersive_weathering.registry.blocks.crackable.Crackable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class CrackableMossableSlabBlock extends MossableSlabBlock implements CrackableMossable {

    private final Supplier<Item> brickItem;
    private final CrackLevel crackLevel;

    public CrackableMossableSlabBlock(MossLevel mossLevel, CrackLevel crackLevel, Supplier<Item> brickItem, Properties settings) {
        super(mossLevel, settings);
        this.crackLevel = crackLevel;
        this.brickItem = brickItem;
    }

    @Override
    public CrackSpreader getCrackSpreader() {
        return CrackSpreader.INSTANCE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel serverLevel, BlockPos pos, Random random) {
        float weatherChance = 0.1f;
        if (random.nextFloat() < weatherChance) {
            boolean isMoss = this.getMossSpreader().getWanderWeatheringState(true, pos, serverLevel);
            Optional<BlockState> opt = Optional.empty();
            if(isMoss) {
                opt = this.getNextMossy(state);
            } else if(this.getCrackSpreader().getWanderWeatheringState(true, pos, serverLevel)){
                opt = this.getNextCracked(state);
            }
            BlockState newState = opt.orElse(state.setValue(WEATHERABLE,false));
            serverLevel.setBlockAndUpdate(pos, newState);
        }
    }



    @Override
    public Item getRepairItem(BlockState state) {
        return brickItem.get();
    }

    @Override
    public CrackLevel getCrackLevel() {
        return crackLevel;
    }

}
