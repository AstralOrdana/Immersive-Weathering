package com.ordana.immersive_weathering.registry;


import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import com.ordana.immersive_weathering.registry.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.registry.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = ImmersiveWeathering.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Item i = stack.getItem();
        BlockPos pos = event.getPos();
        Level level = event.getWorld();
        BlockState state = level.getBlockState(pos);
        Player player = event.getPlayer();

        //shear azalea
        if (i instanceof ShearsItem) {
            var newState = WeatheringHelper.getAzaleaSheared(state).orElse(null);
            if (newState != null) {
                Block.popResourceFromFace(level, pos, event.getFace(), new ItemStack(ModItems.AZALEA_FLOWERS.get()));
                level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            if (newState != null) {
                BlockState s = Mossable.getUnaffectedMossState(state);
                if (s != state) {
                    newState = s;
                    Block.popResourceFromFace(level, pos, event.getFace(), new ItemStack(ModItems.MOSS_CLUMP.get()));
                    level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                }
            }

            if (newState != null) {
                level.setBlockAndUpdate(pos, newState);

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
        //spawn ash
        else if (i instanceof ShovelItem) {
            if (state.getBlock() instanceof CampfireBlock && state.getValue(BlockStateProperties.LIT)) {
                Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(ModItems.SOOT.get()));
            }
            if (state.getBlock() instanceof FireBlock) {
                Block.popResource(level, pos, new ItemStack(ModItems.SOOT.get()));
                level.playSound(player, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);

                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
        //break crackable stuff
        else if (i instanceof PickaxeItem) {

            Block newBlock = Crackable.getIncreasedCrackBlock(state.getBlock()).orElse(null);
            if (newBlock != null && state.getBlock() instanceof Crackable crackable) {
                Block.popResourceFromFace(level, pos, event.getFace(), crackable.getRepairItem(state).getDefaultInstance());

                BlockState newState = newBlock.withPropertiesOf(state);
                level.playSound(player, pos, newState.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

                level.setBlockAndUpdate(pos, newState);

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        } else if (i instanceof AxeItem) {
            var stripped = state.getToolModifiedState(level, pos, player, stack, ToolActions.AXE_STRIP);
            if (stripped != null) {
                var bark = WeatheringHelper.getBarkForStrippedLog(stripped).orElse(null);
                if (bark != null) {

                    Block.popResourceFromFace(level, pos, event.getFace(), bark.getFirst().getDefaultInstance());
                    level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);


                    if (player != null) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                    }

                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    }
                    //not cancelling so the block can get
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                }
            }

        }
        //rust stuff
        else if (i == Items.WET_SPONGE) {
            //TODO: finish
            /*
            if (targetBlock.is(ModTags.RUSTABLE)) {
                world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (player != null) {
                    RUSTED_BLOCKS.forEach((clean, rusty) -> {
                        if (targetBlock.is(clean)) {
                            world.setBlockAndUpdate(targetPos, rusty.withPropertiesOf(targetBlock));
                        }
                    });
                }
                return InteractionResult.SUCCESS;
            }*/
        }
        //mossify stuff
        if (i == ModItems.MOSS_CLUMP.get()) {
            /*
            if (targetBlock.is(ModTags.MOSSABLE)) {
                world.playSound(player, targetPos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (player != null) {
                    if (!player.isCreative()) heldItem.shrink(1);
                    CLEANED_BLOCKS.forEach((mossy, clean) -> {
                        if (targetBlock.is(clean)) {
                            world.setBlockAndUpdate(targetPos, mossy.withPropertiesOf(targetBlock));
                        }
                    });
                }
                return InteractionResult.SUCCESS;


            }*/
        }

        else {
            //Fix cracked stuff
            BlockState fixedBlock = Crackable.getDecreasedCrackState(state).orElse(null);
            if (fixedBlock != null && fixedBlock.getBlock() instanceof Crackable crackable &&
                    crackable.getRepairItem(state) == i) {


                SoundEvent placeSound = fixedBlock.getSoundType().getPlaceSound();
                level.playSound(player, pos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);

                level.setBlockAndUpdate(pos, fixedBlock);

                if (player != null) {
                    if (!player.isCreative()) stack.shrink(1);
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));


            }
            //fix logs
            Pair<Item,Block> fixedLog = WeatheringHelper.getBarkForStrippedLog(state).orElse(null);
            if(fixedLog != null){
                BlockState fixedState = fixedLog.getSecond().withPropertiesOf(state);

                level.playSound(player, pos, fixedState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

                if (player != null) {
                    if (!player.isCreative()) stack.shrink(1);
                }

                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                }

                level.setBlockAndUpdate(pos, fixedState);

            }

        }
    }
}


