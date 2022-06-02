package com.ordana.immersive_weathering.common;


import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.common.blocks.Waxables;
import com.ordana.immersive_weathering.common.blocks.Weatherable;
import com.ordana.immersive_weathering.common.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.common.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.common.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.common.entity.FollowLeafCrownGoal;
import com.ordana.immersive_weathering.common.items.ModItems;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagManager;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ImmersiveWeathering.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    private static final BlockGrowthHandler GROWTH_MANAGER = new BlockGrowthHandler();

    @SubscribeEvent
    public static void onTagUpdated(TagsUpdatedEvent event) {
        GROWTH_MANAGER.rebuild();
    }


    //hacky but that's the best I can do if I dont get given a registry access in that reload listener
    //Without it, it wont load tags
    //use this until forge approves that datapack registries PR (will take some time)
    @SubscribeEvent
    public static void onAddReloadListeners(final AddReloadListenerEvent event) {
        TagManager t = null;
        for(var l : event.getServerResources().listeners()){
            if(l instanceof TagManager tm){
                t = tm;
                GROWTH_MANAGER.registryAccess = t.registryAccess;
                event.addListener(GROWTH_MANAGER);
                break;
            }
        }
        if(t == null){
            throw(new RuntimeException("Failed to register Growth Manager. This means many weathering features wont work:"));
        }
    }


    @SubscribeEvent
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Bee bee) {
            bee.goalSelector.addGoal(3, new FollowLeafCrownGoal(bee, 1D, false));
        }
    }

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
                if (level.isClientSide) {
                    ModParticles.spawnParticlesOnBlockFaces(level, pos, ModParticles.AZALEA_FLOWER.get(), UniformInt.of(4, 6));
                } else {
                    Block.popResourceFromFace(level, pos, event.getFace(), new ItemStack(ModItems.AZALEA_FLOWERS.get()));
                }
                level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);

            } else {
                BlockState s = Mossable.getUnaffectedMossBlock(state);
                if (s != state) {
                    newState = s;
                    if (IntegrationHandler.quark) newState = QuarkPlugin.fixVerticalSlab(newState, state);
                    if (!level.isClientSide) {
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
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    player.awardStat(Stats.ITEM_USED.get(i));
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        } else if (i instanceof FlintAndSteelItem) {

            BlockState s = Mossable.getUnaffectedMossBlock(state);
            if (s != state) {
                s = Weatherable.setStable(s);
                if (IntegrationHandler.quark) s = QuarkPlugin.fixVerticalSlab(s, state);
                if (level.isClientSide) {
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
                    player.awardStat(Stats.ITEM_USED.get(i));
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }

        }
        //spawn ash
        else if (i instanceof ShovelItem) {
            if (b instanceof CampfireBlock && state.getValue(BlockStateProperties.LIT)) {
                Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(ModBlocks.SOOT.get()));
            }
            if (b instanceof FireBlock) {
                Block.popResource(level, pos, new ItemStack(ModBlocks.SOOT.get()));
                level.playSound(player, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);

                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(i));
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
        //break crackable stuff
        else if (i instanceof PickaxeItem &&
                (!ServerConfigs.CRACK_REQUIRES_SHIFTING.get() || player.isSecondaryUseActive())) {

            BlockState newBlock = Crackable.getCrackedBlock(state);
            if (newBlock != state) {
                if (IntegrationHandler.quark) newBlock = QuarkPlugin.fixVerticalSlab(newBlock, state);
                if (!player.isCreative()) {
                    if (newBlock instanceof Crackable crackable) {
                        Block.popResourceFromFace(level, pos, event.getFace(), crackable.getRepairItem(state).getDefaultInstance());
                    }
                }

                level.playSound(player, pos, newBlock.getSoundType().getHitSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

                // level.setBlockAndUpdate(pos, Block.updateFromNeighbourShapes(newBlock, level, pos));
                level.setBlockAndUpdate(pos, newBlock);

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(i));
                }

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
        //spawn bark
        else if (i instanceof AxeItem) {
            if (ServerConfigs.BARK_ENABLED.get()) {
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
                            player.awardStat(Stats.ITEM_USED.get(i));
                        }
                        //not cancelling so the block can getMossSpreader
                        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                        return;
                    }
                }
            }

            if (state.getBlock() instanceof Rustable r && r.getAge() != Rustable.RustLevel.RUSTED) {
                var unRusted = r.getPrevious(state).orElse(null);
                if (unRusted != null) {

                    level.setBlockAndUpdate(pos, unRusted);
                    level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.blockEvent(pos, unRusted.getBlock(), 1, 0);
                    if (player != null) {
                        stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(event.getHand()));
                    }

                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                        player.awardStat(Stats.ITEM_USED.get(i));
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
                    player.awardStat(Stats.ITEM_USED.get(i));
                }
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
        //mossify stuff
        else if (i == ModItems.MOSS_CLUMP.get()) {
            BlockState mossy = Mossable.getMossyBlock(state);
            if (mossy != state) {
                level.playSound(player, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (IntegrationHandler.quark) mossy = QuarkPlugin.fixVerticalSlab(mossy, state);
                level.setBlockAndUpdate(pos, mossy);

                if (player != null) {
                    if (!player.isCreative()) stack.shrink(1);
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(i));
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
                    player.awardStat(Stats.ITEM_USED.get(i));
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
                if (IntegrationHandler.quark) fixedBlock = QuarkPlugin.fixVerticalSlab(fixedBlock, state);

                SoundEvent placeSound = fixedBlock.getSoundType().getPlaceSound();
                level.playSound(player, pos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);

                level.setBlockAndUpdate(pos, fixedBlock);

                if (player != null) {
                    if (!player.isCreative()) stack.shrink(1);
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(i));
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
                    player.awardStat(Stats.ITEM_USED.get(i));
                }

                level.setBlockAndUpdate(pos, fixedState);

                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
            }
        }
    }


    private static final Map<String, ResourceLocation> fullReMap = new HashMap<>() {{
        put("ash_block", ImmersiveWeathering.res("soot_block"));
        put("nulch", ImmersiveWeathering.res("nulch_block"));
        put("mulch", ImmersiveWeathering.res("mulch_block"));
    }};

    @SubscribeEvent
    public static void onRemapBlocks(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings(ImmersiveWeathering.MOD_ID)) {
            String k = mapping.key.getPath();
            if (fullReMap.containsKey(k)) {
                var i = fullReMap.get(k);
                try {
                    ImmersiveWeathering.LOGGER.warn("Remapping block '{}' to '{}'", mapping.key, i);
                    mapping.remap(ForgeRegistries.BLOCKS.getValue(i));
                } catch (Throwable t) {
                    ImmersiveWeathering.LOGGER.warn("Remapping block '{}' to '{}' failed: {}", mapping.key, i, t);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRemapItems(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings(ImmersiveWeathering.MOD_ID)) {
            String k = mapping.key.getPath();
            if (fullReMap.containsKey(k)) {
                var i = fullReMap.get(k);
                try {
                    ImmersiveWeathering.LOGGER.warn("Remapping item '{}' to '{}'", mapping.key, i);
                    mapping.remap(ForgeRegistries.ITEMS.getValue(i));
                } catch (Throwable t) {
                    ImmersiveWeathering.LOGGER.warn("Remapping item '{}' to '{}' failed: {}", mapping.key, i, t);
                }
            }
        }
    }
}


