package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.data.block_growths.growths.builtin.LightningGrowth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LeafPileBlockItem extends BlockItem {

    public LeafPileBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (level.getBlockState(blockHitResult.getBlockPos()).getBlock() == Blocks.WATER) {
            BlockHitResult blockHitResult2 = blockHitResult.withPosition(blockHitResult.getBlockPos().above());
            InteractionResult interactionResult = super.useOn(new UseOnContext(player, hand, blockHitResult2));
            return new InteractionResultHolder<>(interactionResult, player.getItemInHand(hand));
        }
        return super.use(level, player, hand);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        return super.canPlace(context, state) &&
                context.getLevel().getFluidState(context.getClickedPos()).isEmpty();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var ret = super.useOn(context);
        if (ret == InteractionResult.FAIL) return InteractionResult.PASS;
        return ret;
    }
}
