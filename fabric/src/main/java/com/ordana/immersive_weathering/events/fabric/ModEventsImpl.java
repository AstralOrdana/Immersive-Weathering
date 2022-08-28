package com.ordana.immersive_weathering.events.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ModEventsImpl {
    private static boolean doStripLog(ItemStack stack, BlockPos pos, BlockState state, Player player, Level level, InteractionHand hand, Direction dir) {
        return false;
    }
}
