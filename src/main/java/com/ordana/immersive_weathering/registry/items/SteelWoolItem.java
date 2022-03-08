package com.ordana.immersive_weathering.registry.items;

import com.ordana.immersive_weathering.registry.blocks.rustable.Rustable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SteelWoolItem extends Item {

    public SteelWoolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        var previous = Rustable.getDecreasedRustState(state).orElse(null);
        if (previous != null) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (player != null) {
                context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
            }

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos,  context.getItemInHand());
            }

            level.blockEvent(pos, previous.getBlock(), 1, 0);

            level.setBlockAndUpdate(pos, previous);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }


}
