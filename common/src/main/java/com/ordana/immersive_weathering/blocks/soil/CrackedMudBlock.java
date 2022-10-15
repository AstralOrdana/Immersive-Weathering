package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.blocks.Soaked;
import com.ordana.immersive_weathering.data.block_growths.IConditionalGrowingBlock;
import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import net.minecraft.util.RandomSource;

public class CrackedMudBlock extends Block implements IConditionalGrowingBlock, Soaked {


    public static final BooleanProperty FERTILE = ModBlockProperties.FERTILE;

    public CrackedMudBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SOAKED, false).setValue(FERTILE, true));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(SOAKED)) {
            if (random.nextInt(25) == 1) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        boolean newState = WeatheringHelper.shouldGetWet(world, pos);
        if (state.getValue(SOAKED) != newState) {
            world.setBlockAndUpdate(pos, state.setValue(SOAKED, newState));
        }
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
        stateManager.add(FERTILE);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.getValue(FERTILE) && state.getValue(SOAKED);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof ShearsItem && state.getValue(FERTILE)) {
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
        var res = soakOrDrain(state, level, pos, player, hand);
        if (res != InteractionResult.PASS) return res;
        return super.use(state, level, pos, player, hand, hit);
    }


}