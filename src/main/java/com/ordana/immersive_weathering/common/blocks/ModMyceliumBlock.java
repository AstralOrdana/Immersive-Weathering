package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;

public class ModMyceliumBlock extends MyceliumBlock implements BonemealableBlock {
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
        return state.getValue(FERTILE) && super.isRandomlyTicking(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        super.randomTick(state, level, pos, random);
        if (state.getValue(FERTILE)) {
            BlockGrowthHandler.tickBlock(state, level, pos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        return !state.getValue(FERTILE) && level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        if(!state.getValue(FERTILE)) {
            level.setBlock(pos, state.setValue(FERTILE, true), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(state.getValue(FERTILE)) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (!level.isClientSide) {
                level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, state.setValue(FERTILE, false), 11);

                itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            } else {
                level.addDestroyBlockEffect(pos, state);
               // int p = level.random.nextInt(3)+3;

              //  ModParticles.spawnParticleOnFace(level, pos,Direction.UP, BlockParticleOption.);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }return super.use(state,level,pos,player,hand,hitResult);
    }

}
