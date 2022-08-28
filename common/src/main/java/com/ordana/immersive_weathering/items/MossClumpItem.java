package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.integration.IntegrationHandler;
import com.ordana.immersive_weathering.integration.QuarkPlugin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MossClumpItem extends Item {

    public MossClumpItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (CommonConfigs.MOSS_SHEARING.get()) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            BlockState state = level.getBlockState(pos);
            BlockState mossy = Mossable.getMossyBlock(state);
            if (mossy != state) {
                ItemStack stack = context.getItemInHand();
                Player player = context.getPlayer();
                level.playSound(player, pos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);

                if (player != null && !player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (player instanceof ServerPlayer) {
                    //todo
                    //if (IntegrationHandler.quark) mossy = QuarkPlugin.fixVerticalSlab(mossy, state);

                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                    level.setBlockAndUpdate(pos, mossy);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.useOn(context);
    }
}
