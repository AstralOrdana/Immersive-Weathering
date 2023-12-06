package com.ordana.immersive_weathering.blocks.charred;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class CharredBlock extends Block implements Charred {

    public CharredBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SMOLDERING, false).setValue(OVERHANG, 0));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
        updateOverhang(state, level, pos);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Charred.super.animateTick(state, level, pos, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Charred.super.randomTick(state, level, pos, random);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        return this.defaultBlockState().setValue(OVERHANG, this.getOverhang(level, blockPos));
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(OVERHANG) == 2) {
            FallingBlockEntity.fall(level, pos, state.setValue(OVERHANG, 0));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager);
        stateManager.add(SMOLDERING, OVERHANG);
    }


    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        onEntityStepOn(state, entity);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof ShovelItem && state.getValue(SMOLDERING)) {
            level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.EMBERSPARK.get(), UniformInt.of(3, 5));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) {
                //Block.popResourceFromFace(level, pos, Direction.UP, new ItemStack(ModBlocks.ASH_LAYER_BLOCK.get()));
            }
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, state.setValue(SMOLDERING, Boolean.FALSE));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (item instanceof FlintAndSteelItem && !state.getValue(SMOLDERING)) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.EMBERSPARK.get(), UniformInt.of(3, 5));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, state.setValue(SMOLDERING, Boolean.TRUE));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult pHit, Projectile projectile) {
        BlockPos pos = pHit.getBlockPos();
        interactWithProjectile(level, state, projectile, pos);
    }

    @Override
    public void entityInside(BlockState state, Level levelIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof Projectile projectile) {
            interactWithProjectile(levelIn, state, projectile, pos);
        }
    }

}
