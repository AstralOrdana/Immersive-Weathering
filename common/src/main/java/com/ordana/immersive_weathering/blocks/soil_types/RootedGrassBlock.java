package com.ordana.immersive_weathering.blocks.soil_types;

import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.BlockHitResult;


public class RootedGrassBlock extends GrassBlock implements BonemealableBlock {
    public RootedGrassBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected net.minecraft.world.ItemInteractionResult useItemOn(net.minecraft.world.item.ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof ShovelItem && !state.getValue(SNOWY)) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            stack.hurtAndBreak(1, player, net.minecraft.world.entity.LivingEntity.getSlotForHand(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, Blocks.DIRT_PATH.defaultBlockState());
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (item instanceof HoeItem) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            stack.hurtAndBreak(1, player, net.minecraft.world.entity.LivingEntity.getSlotForHand(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, Blocks.GRASS_BLOCK.withPropertiesOf(state));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                Block.popResourceFromFace(level, pos, hitResult.getDirection(), Items.HANGING_ROOTS.getDefaultInstance());
            }
            return net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(heldStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        boolean space = false;
        for (Direction dir : Direction.values()) {
            var targetState = level.getBlockState(pos.relative(dir));
            if (dir == Direction.UP) {if (level.getBlockState(pos.above()).isAir()) space = true;}
            else if (targetState.canBeReplaced() &&
                !targetState.is(Blocks.HANGING_ROOTS) &&
                !targetState.is(ModBlocks.HANGING_ROOTS_WALL.get())) space = true;
        }
        return space;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        WeatheringHelper.growHangingRoots(level, random, pos);
        super.performBonemeal(level, random, pos, state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canRemainRootedGrass(state, level, pos)) {
            level.setBlockAndUpdate(pos, Blocks.ROOTED_DIRT.defaultBlockState());
        }
        else super.randomTick(state, level, pos, random);
    }

    private static boolean canRemainRootedGrass(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }
        if (level.getFluidState(abovePos).is(FluidTags.WATER) && level.getFluidState(abovePos).getAmount() == 8) {
            return false;
        }
        int light = LightEngine.getLightBlockInto(level, state, pos, aboveState, abovePos, Direction.UP, aboveState.getLightBlock(level, abovePos));
        return light < level.getMaxLightLevel();
    }
}
