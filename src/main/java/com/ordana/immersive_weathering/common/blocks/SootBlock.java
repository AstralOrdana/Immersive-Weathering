package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.common.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class SootBlock extends MultifaceBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public SootBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager);
        stateManager.add(LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(LIT)) {
            world.setBlock(pos, state.setValue(LIT, false), 2);
            world.playSound(player, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
            return InteractionResult.sidedSuccess(world.isClientSide);
        } else if(player.getItemInHand(hand).getItem() instanceof FlintAndSteelItem) {
            world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            ItemStack stack = (player.getItemInHand(hand));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            world.setBlockAndUpdate(pos, state.setValue(LIT, true));
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (state.getValue(LIT)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double) i + random.nextDouble();
            double e = (double) j + random.nextDouble();
            double f = (double) k + random.nextDouble();
            world.addParticle(ModParticles.EMBER.get(), d, e, f, 0.1D, 3D, 0.1D);
        }
    }

    //TODO: merge with ash
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.is(ModTags.MAGMA_SOURCE)) {
                if (world.isRainingAt(pos.above())) {
                    return;
                }
                world.setBlock(pos, state.setValue(LIT, true), 2);
            }
            if (world.isRainingAt(pos.above()) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                if (neighborState.is(ModTags.MAGMA_SOURCE)) {
                    return;
                }
                world.setBlock(pos, state.setValue(LIT, false), 2);
            }
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            if (world.isClientSide) {
                Random random = world.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    if (!state.getValue(LIT)) {
                        world.addParticle(ModParticles.SOOT.get(), entity.getX(), entity.getY() + 0.5, entity.getZ(), Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f, 0.05D, Mth.randomBetween(random, -1.0F, 1.0F) * 0.001f);
                    }
                }
            }
            if (state.getValue(LIT)) {
                if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                    entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
                }
            }
        }
    }
}
