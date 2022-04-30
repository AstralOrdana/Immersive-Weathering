package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class ModMyceliumBlock extends MyceliumBlock implements BonemealableBlock, IConditionalGrowingBlock {
    public static final BooleanProperty FERTILE = SoilBlock.FERTILE;

    public ModMyceliumBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return SoilBlock.isFertile(state) && super.isRandomlyTicking(state);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return SoilBlock.isFertile(state);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        return ! SoilBlock.isFertile(state)  && level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        if(! SoilBlock.isFertile(state) ) {
            level.setBlock(pos, state.setValue(FERTILE, true), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if( SoilBlock.isFertile(state) ) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.getItem() instanceof ShearsItem) {
                if (!level.isClientSide) {
                    level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pos, state.setValue(FERTILE, false), 11);

                    itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
                } else {
                    level.addDestroyBlockEffect(pos, Blocks.BROWN_MUSHROOM.defaultBlockState());
                    // int p = level.random.nextInt(3)+3;

                    //  ModParticles.spawnParticleOnFace(level, pos,Direction.UP, BlockParticleOption.);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.use(state,level,pos,player,hand,hitResult);
    }

}
