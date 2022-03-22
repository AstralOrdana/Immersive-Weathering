package com.ordana.immersive_weathering.common;


import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.blocks.Waxables;
import com.ordana.immersive_weathering.common.blocks.Weatherable;
import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import com.ordana.immersive_weathering.common.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.common.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.common.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
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

@Mod.EventBusSubscriber(modid = ImmersiveWeathering.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {


    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Item i = stack.getItem();
        BlockPos pos = event.getPos();
        Level level = event.getWorld();
        BlockState state = level.getBlockState(pos);
        Block b = state.getBlock();
        Player player = event.getPlayer();

        //shear azalea
        if (i instanceof ShearsItem) {
            var newState = WeatheringHelper.getAzaleaSheared(state).orElse(null);
            if (newState != null) {
                if(level.isClientSide){
                    ModParticles.spawnParticlesOnBlockFaces(level, pos, ModParticles.AZALEA_FLOWER.get(),UniformInt.of(4,6));
                }else{
                    Block.popResourceFromFace(level, pos, event.getFace(), new ItemStack(ModItems.AZALEA_FLOWERS.get()));
                }
               level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);

            } else {
                BlockState s = Mossable.getUnaffectedMossState(state);
                if (s != state) {
                    s = Weatherable.setStable(s);
                    newState = s;
                    if(!level.isClientSide){
                        Block.popResourceFromFace(level, pos, event.getFace(), new ItemStack(ModItems.MOSS_CLUMP.get()));
                    }
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
        else if(i instanceof FlintAndSteelItem){
            if(b instanceof Mossable){
                BlockState s = Mossable.getUnaffectedMossState(state);
                if (s != state) {
                    s = Weatherable.setStable(s);

                    if(level.isClientSide){
                        ModParticles.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.FLAME, UniformInt.of(3, 5));
                    }
                    //fixing stuff prevents them from weathering

                    level.setBlockAndUpdate(pos, s);
                    level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);

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
        }
        //spawn ash
        else if (i instanceof ShovelItem) {
            if (b instanceof CampfireBlock && state.getValue(BlockStateProperties.LIT)) {
                Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(ModItems.SOOT.get()));
            }
            if (b instanceof FireBlock) {
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
        else if (i instanceof PickaxeItem && b instanceof Crackable crackable) {

            BlockState newBlock = crackable.getNextCracked(state).orElse(null);
            if (newBlock != null) {
                Block.popResourceFromFace(level, pos, event.getFace(), crackable.getRepairItem(state).getDefaultInstance());

                level.playSound(player, pos, newBlock.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

                // level.setBlockAndUpdate(pos, Block.updateFromNeighbourShapes(newBlock, level, pos));
                level.setBlockAndUpdate(pos, newBlock);

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
        //spawn bark
        else if (i instanceof AxeItem) {
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
                    //not cancelling so the block can getMossSpreader
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                    return;
                }
            }

            if(state instanceof Rustable r && r.getAge() != Rustable.RustLevel.RUSTED){
                var unRusted = r.getPrevious(state).orElse(null);
                if(unRusted != null) {

                    level.setBlockAndUpdate(pos, unRusted);
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.blockEvent(pos, unRusted.getBlock(), 1, 0);
                    if (player != null) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                    }

                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    }
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                    event.setCanceled(true);
                    return;
                }
            }

        }
        //rust stuff
        else if (i == Items.WET_SPONGE && b instanceof Rustable rustable) {
            BlockState rusted = rustable.getNext(state).orElse(null);
            if (rusted != null) {
                level.playSound(player, pos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);

                level.setBlockAndUpdate(pos, rusted);

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
        //mossify stuff
        else if (i == ModItems.MOSS_CLUMP.get() && b instanceof Mossable mossable) {
            BlockState mossy = mossable.getNextMossy(state).orElse(null);
            if (mossy != null) {
                level.playSound(player, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);

                level.setBlockAndUpdate(pos, mossy);

                if (player != null) {
                    if (!player.isCreative()) stack.shrink(1);
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
        //waxing
        else if (i instanceof HoneycombItem) {
            var waxed = Waxables.getWaxedState(state).orElse(null);
            if (waxed != null) {

                level.levelEvent(player, 3003, pos, 0);

                level.setBlockAndUpdate(pos, waxed);

                if (player != null) {
                    if (!player.isCreative()) stack.shrink(1);
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }

        } else if (b instanceof Crackable crackable && crackable.getRepairItem(state) == i) {
            //Fix cracked stuff
            BlockState fixedBlock = crackable.getPreviousCracked(state).orElse(null);
            if (fixedBlock != null) {

                //fixing stuff prevents them from weathering
                fixedBlock = Weatherable.setStable(fixedBlock);

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
        } else {
            //fix logs
            Pair<Item, Block> fixedLog = WeatheringHelper.getBarkForStrippedLog(state).orElse(null);
            if (fixedLog != null && stack.getItem() == fixedLog.getFirst()) {
                BlockState fixedState = fixedLog.getSecond().withPropertiesOf(state);

                level.playSound(player, pos, fixedState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

                if (player != null) {
                    if (!player.isCreative()) stack.shrink(1);
                }

                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                }

                level.setBlockAndUpdate(pos, fixedState);

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }
}


