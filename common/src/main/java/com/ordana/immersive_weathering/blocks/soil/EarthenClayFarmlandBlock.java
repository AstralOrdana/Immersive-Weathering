package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Iterator;
import java.util.Objects;

public class EarthenClayFarmlandBlock extends ModFarmlandBlock {

    public EarthenClayFarmlandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, 0));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        int i = 0;
        if (bl) i = 7;
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.EARTHEN_CLAY.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, bl) : Objects.requireNonNull(super.getStateForPlacement(context)).setValue(MOISTURE, i);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (serverLevel.getRawBrightness(blockPos.above(), 0) >= 9 && blockState.getValue(BlockStateProperties.MOISTURE) == 7) {
            BlockState cropState = serverLevel.getBlockState(blockPos.above());
            if(cropState.is(ModTags.CLAY_SOIL_CROP)) {
                for(int j = 0; j < 10; j++){
                    cropState.randomTick(serverLevel, blockPos.above(), randomSource);
                }
            }
        }
        int i = blockState.getValue(MOISTURE);
        if (serverLevel.isRainingAt(blockPos.above()) || isNearWater(serverLevel, blockPos)) {
            serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, MAX_MOISTURE), 2);
        } else if (serverLevel.isDay() && !serverLevel.isRaining() && serverLevel.canSeeSky(blockPos.above())) {
            if (!isUnderCrops(serverLevel, blockPos)) turnToDirt(blockState, serverLevel, blockPos);
        }
    }

    public static boolean isNearWater(LevelReader level, BlockPos pos) {
        Iterator var2 = BlockPos.betweenClosed(pos.offset(-3, 0, -3), pos.offset(3, 1, 3)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!level.getFluidState(blockPos).is(FluidTags.WATER));

        return true;
    }


    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        if (!level.isClientSide && level.random.nextFloat() < f - 0.5F && entity instanceof LivingEntity && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F) {
            turnToDirt(state, level, pos);
        }

        super.fallOn(level, state, pos, entity, f);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!blockState.canSurvive(serverLevel, blockPos)) {
            turnToDirt(blockState, serverLevel, blockPos);
        }
    }

    public static void turnToDirt(BlockState state, Level level, BlockPos pos) {
        if (state.getValue(MOISTURE) == 7) level.setBlockAndUpdate(pos, pushEntitiesUp(state, ModBlocks.EARTHEN_CLAY.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), level, pos));
        else level.setBlockAndUpdate(pos, pushEntitiesUp(state, ModBlocks.EARTHEN_CLAY.get().defaultBlockState(), level, pos));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.isSecondaryUseActive()) {
            // empty bucket into mulch
            ItemStack stack = player.getItemInHand(hand);
            if (stack.is(Items.WATER_BUCKET) && state.getValue(MOISTURE) == 0) {
                level.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (player instanceof ServerPlayer) {
                    ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.BUCKET.getDefaultInstance());
                    player.setItemInHand(hand, itemStack2);
                    level.setBlockAndUpdate(pos, state.setValue(MOISTURE, 7));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            // fill bucket from mulch
            else if (stack.is(Items.BUCKET) && state.getValue(MOISTURE) > 0) {
                level.playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                if (player instanceof ServerPlayer) {
                    ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.LAVA_BUCKET.getDefaultInstance());
                    player.setItemInHand(hand, itemStack2);
                    level.setBlockAndUpdate(pos, state.setValue(MOISTURE, 0));
                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
