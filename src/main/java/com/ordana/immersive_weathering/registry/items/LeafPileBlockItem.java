package com.ordana.immersive_weathering.registry.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class LeafPileBlockItem extends BlockItem {

    public LeafPileBlockItem(Block block, Settings properties) {
        super(block, properties);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var result = super.useOnBlock(context);
        if(result == ActionResult.FAIL)return ActionResult.PASS;
        return result;
    }

    @Override
    public TypedActionResult<ItemStack> use(World p_43441_, PlayerEntity p_43442_, Hand p_43443_) {
        BlockHitResult blockhitresult = raycast(p_43441_, p_43442_, RaycastContext.FluidHandling.SOURCE_ONLY);
        BlockHitResult blockhitresult1 = blockhitresult.withBlockPos(blockhitresult.getBlockPos().up());
        ActionResult interactionresult = super.useOnBlock(new ItemUsageContext(p_43442_, p_43443_, blockhitresult1));
        return new TypedActionResult<>(interactionresult, p_43442_.getStackInHand(p_43443_));
    }
}
