package com.ordana.immersive_weathering.blocks.frosted;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class FrostBlock extends MultifaceBlock implements Frosty {
    public static final MapCodec<FrostBlock> CODEC = simpleCodec(FrostBlock::new);
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public FrostBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(NATURAL, false));
    }

    @Override
    public MapCodec<FrostBlock> codec() {
        return CODEC;
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NATURAL);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return !context.getItemInHand().is(this.asItem()) || super.canBeReplaced(state, context);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        tryUnFrost(state, level, pos);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        InteractionResult success = interactWithPlayer(state, level, pos, player, hand);
        if (success != InteractionResult.PASS) return ItemInteractionResult.SUCCESS;

        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }


}
