package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.registry.blocks.Waxables;
import com.ordana.immersive_weathering.registry.blocks.rustable.Rustable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SteelWoolItem extends Item {

    public SteelWoolItem(Properties properties) {
        super(properties);
    }
/*
    @Override
    public InteractionResult useOn(UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        //TODO: add unwaxing
        var previous = Rustable.getDecreasedRustBlock(state).orElse(null);
        if (previous != null && state.getBlock() instanceof Rustable r && r.getAge()!= Rustable.RustLevel.RUSTED) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);

            level.blockEvent(pos, previous.getBlock(), 1, 0);
        }
        else {
            previous = Waxables.getUnWaxedState(state).orElse(null);
            if (previous != null) {

                level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.levelEvent(player, 3004, pos, 0);
            }
        }
        if(previous != null){
            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
            }

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, context.getItemInHand());
            }

            level.setBlockAndUpdate(pos, previous);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

*/
}
