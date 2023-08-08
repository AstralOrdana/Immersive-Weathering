package com.ordana.immersive_weathering.blocks.soil;


import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.data.block_growths.IConditionalGrowingBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class SoilBlock extends SnowyDirtBlock implements BonemealableBlock, IConditionalGrowingBlock {

    public static final BooleanProperty FERTILE = ModBlockProperties.FERTILE;

    public SoilBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, true));
    }

    public static boolean isFertile(BlockState state){
        return state.hasProperty(FERTILE) && state.getValue(FERTILE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return isFertile(state) && super.isRandomlyTicking(state);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return SoilBlock.isFertile(state);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        return !isFertile(state) && level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, RandomSource p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if(!isFertile(state)) {
            level.setBlock(pos, state.setValue(FERTILE, true), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof ShearsItem && !state.getValue(SNOWY) && state.getValue(FERTILE)) {
            level.playSound(player, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            level.addDestroyBlockEffect(pos, state);
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, state.setValue(FERTILE, Boolean.FALSE));
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (item instanceof ShovelItem && !state.getValue(SNOWY) && !state.is(ModBlocks.SILT.get())) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, Blocks.DIRT_PATH.defaultBlockState());
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state,level,pos,player,hand,hitResult);
    }
}
