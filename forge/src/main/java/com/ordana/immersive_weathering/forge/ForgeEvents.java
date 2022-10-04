package com.ordana.immersive_weathering.forge;


import com.ordana.immersive_weathering.data.block_growths.BlockGrowthHandler;

import com.ordana.immersive_weathering.data.fluid_generators.FluidGeneratorsHandler;
import com.ordana.immersive_weathering.events.ModLootInjects;
import com.ordana.immersive_weathering.mixins.EatBlockGoalMixin;
import net.mehvahdjukaar.selene.Selene;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

@Mod.EventBusSubscriber(modid = ImmersiveWeatheringForge.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {


    //hacky but eh
    @SubscribeEvent
    public static void onTagUpdated(TagsUpdatedEvent event) {
        BlockGrowthHandler.RELOAD_INSTANCE.rebuild(event.getTagManager());
        FluidGeneratorsHandler.RELOAD_INSTANCE.rebuild(event.getTagManager());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        var ret = com.ordana.immersive_weathering.events.ModEvents.onBlockCLicked(event.getItemStack(), event.getPlayer(), event.getWorld(), event.getHand(), event.getHitVec());
        if (ret != InteractionResult.PASS) {
            event.setCanceled(true);
            event.setCancellationResult(ret);
        }
    }

    @SubscribeEvent
    public static void onAddLootTables(LootTableLoadEvent event) {
        ModLootInjects.onLootInject(event.getLootTableManager(), event.getName(), (b) -> event.getTable().addPool(b.build()));
    }



    //old stuff
/*

//TODO: diepenser stuff
    //TODO: copy and merge fabic one from latest update



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
        //spawn ash
        //break crackable stuff

        //spawn bark

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
        //waxing

         else {
            //fix logs

        }
    }



*/
}


