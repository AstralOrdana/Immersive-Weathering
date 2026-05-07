package com.ordana.immersive_weathering.blocks.soil_types;

import com.mojang.serialization.MapCodec;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SandyDirtBlock extends FallingBlock {
    public static final MapCodec<SandyDirtBlock> CODEC = simpleCodec(SandyDirtBlock::new);

    public SandyDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<SandyDirtBlock> codec() {
        return CODEC;
    }

    protected net.minecraft.world.ItemInteractionResult useItemOn(net.minecraft.world.item.ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof HoeItem) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            stack.hurtAndBreak(1, player, net.minecraft.world.entity.LivingEntity.getSlotForHand(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, ModBlocks.SANDY_FARMLAND.get().withPropertiesOf(state));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useItemOn(heldStack, state, level, pos, player, hand, hitResult);
    }

    public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return 0xd3b893;
    }
}
