package com.ordana.immersive_weathering.common.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;

public class LeafPileBlockItem extends BlockItem {

    public LeafPileBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var result = super.useOn(context);
        if(result == InteractionResult.FAIL)return InteractionResult.PASS;
        return result;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_43441_, Player p_43442_, InteractionHand p_43443_) {
        BlockHitResult blockhitresult = getPlayerPOVHitResult(p_43441_, p_43442_, ClipContext.Fluid.SOURCE_ONLY);
        BlockHitResult blockhitresult1 = blockhitresult.withPosition(blockhitresult.getBlockPos().above());
        InteractionResult interactionresult = super.useOn(new UseOnContext(p_43442_, p_43443_, blockhitresult1));
        return new InteractionResultHolder<>(interactionresult, p_43442_.getItemInHand(p_43443_));
    }
}
