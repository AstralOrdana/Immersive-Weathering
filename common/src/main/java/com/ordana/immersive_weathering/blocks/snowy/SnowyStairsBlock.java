package com.ordana.immersive_weathering.blocks.snowy;

import net.mehvahdjukaar.moonlight.api.block.ModStairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class SnowyStairsBlock extends ModStairBlock implements Snowy{

    public SnowyStairsBlock(Supplier<Block> baseBlock, Properties settings) {
        super(baseBlock, settings);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(interactWithPlayer(state, level, pos, player, hand, hit)){
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, hand, hit);
    }

}
