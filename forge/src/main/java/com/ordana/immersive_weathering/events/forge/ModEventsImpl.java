package com.ordana.immersive_weathering.events.forge;

import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;

public class ModEventsImpl {
    private static boolean doStripLog(ItemStack stack, BlockPos pos, BlockState state, Player player, Level level, InteractionHand hand, Direction dir) {

        var stripped = state.getToolModifiedState(level, pos, player, stack, ToolActions.AXE_STRIP);
        if (stripped != null) {
            var bark = WeatheringHelper.getBarkForStrippedLog(stripped).orElse(null);
            if (bark != null) {

                Block.popResourceFromFace(level, pos, dir, bark.getFirst().getDefaultInstance());
                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);

                var barkParticle = ModParticles.OAK_BARK.get();
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, barkParticle, UniformInt.of(3, 5));

                if (player != null) {
                    stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
                }

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                }
                //not cancelling so the block can stripped
                //TODO: fix
                //event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
                return true;
            }
        }
        return false;
    }
}
