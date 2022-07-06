package com.ordana.immersive_weathering.events;

import com.ordana.immersive_weathering.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;


public class ModEvents {

    @FunctionalInterface
    public interface InteractionEvent {
        InteractionResult run(Item i, ItemStack stack,
                              BlockPos pos,
                              BlockState state,
                              Player player, Level level,
                              InteractionHand hand,
                              BlockHitResult hit);
    }

    private static final List<InteractionEvent> EVENTS = new ArrayList<>();

    static {
        EVENTS.add(ModEvents::shearMoss);
        EVENTS.add(ModEvents::slimePistons);
        EVENTS.add(ModEvents::pickaxeCracking);
        EVENTS.add(ModEvents::brickRepair);
    }


    public static InteractionResult invokeEvents(ItemStack stack, Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        Item i = stack.getItem();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        for (var event : EVENTS) {
            var result = event.run(i, stack, pos, state, player, level, hand, hitResult);
            if (result != InteractionResult.PASS) return result;
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult shearMoss(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                               Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {

        return InteractionResult.PASS;
    }

    private static InteractionResult slimePistons(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                  Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item == Items.SLIME_BALL && CommonConfigs.PISTON_SLIMING.get()) {
            if (state.is(Blocks.PISTON) && !state.getValue(PistonBaseBlock.EXTENDED)) {
                level.playSound(player, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.ITEM_SLIME, UniformInt.of(3, 5));
                if (player instanceof ServerPlayer) {
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                    level.setBlockAndUpdate(pos, Blocks.STICKY_PISTON.withPropertiesOf(state));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }


    //maybe put this into crackable blocks. has the advantage here that it can work on modded blocks we dont own
    private static InteractionResult pickaxeCracking(Item item, ItemStack stack, BlockPos pos, BlockState state,
                                                     Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (item instanceof PickaxeItem && CommonConfigs.PICKAXE_CRACKING.get()) {
            if (!player.isSecondaryUseActive() && CommonConfigs.PICKAXE_CRACKING_SHIFT.get()) return InteractionResult.PASS;

            BlockState newBlock = Crackable.getCrackedBlock(state);
            if (newBlock != state) {
                if (IntegrationHandler.quark) newBlock = QuarkPlugin.fixVerticalSlab(newBlock, state);
                if (!player.getAbilities().instabuild) {
                    if (newBlock instanceof Crackable crackable) {
                        Block.popResourceFromFace(level, pos, hitResult.getDirection(), crackable.getRepairItem(state).getDefaultInstance());
                    }
                }
                level.playSound(player, pos, newBlock.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.BLOCK, state), UniformInt.of(3, 5));

                if (player instanceof ServerPlayer) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));

                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, newBlock);
                }
            }
        }
        return InteractionResult.PASS;
    }

    if(BRICK_REPAIR.containsKey(heldItem.getItem())&&targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR.get(heldItem.getItem()))))

    {
        Block fixedBlock = BRICK_REPAIR.get(heldItem.getItem());
        SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
        world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
            if (!player.isCreative()) heldItem.shrink(1);
            world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
        }
        return InteractionResult.SUCCESS;
    }
                if(BRICK_REPAIR_SLABS.containsKey(heldItem.getItem())&&targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_SLABS.get(heldItem.getItem()))))

    {
        Block fixedBlock = BRICK_REPAIR_SLABS.get(heldItem.getItem());
        SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
        world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
            if (!player.isCreative()) heldItem.shrink(1);
            world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
        }
        return InteractionResult.SUCCESS;
    }
                if(BRICK_REPAIR_STAIRS.containsKey(heldItem.getItem())&&targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_STAIRS.get(heldItem.getItem()))))

    {
        Block fixedBlock = BRICK_REPAIR_STAIRS.get(heldItem.getItem());
        SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
        world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
            if (!player.isCreative()) heldItem.shrink(1);
            world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
        }
        return InteractionResult.SUCCESS;
    }
                if(BRICK_REPAIR_WALLS.containsKey(heldItem.getItem())&&targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_WALLS.get(heldItem.getItem()))))

    {
        Block fixedBlock = BRICK_REPAIR_WALLS.get(heldItem.getItem());
        SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
        world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
            if (!player.isCreative()) heldItem.shrink(1);
            world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
        }
        return InteractionResult.SUCCESS;
    }


}
