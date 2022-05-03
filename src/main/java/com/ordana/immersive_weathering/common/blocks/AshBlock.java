package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.common.ModBlocks;
import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.common.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.ModList;

import java.util.Random;

public class AshBlock extends FallingBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public AshBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(LIT)) {
            world.setBlockAndUpdate(pos, state.setValue(LIT, false));
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

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (state.getValue(LIT)) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        int temperature = 0;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);

            if (world.isRainingAt(pos.relative(direction)) || neighborState.getFluidState().getType() == Fluids.FLOWING_WATER || neighborState.getFluidState().getType() == Fluids.WATER) {
                temperature--;
            } else if (neighborState.is(ModTags.MAGMA_SOURCE)) {
                temperature++;
            }
        }
        if (temperature > 0 && !state.getValue(LIT)) {
            world.setBlock(pos, state.setValue(LIT, true), 2);
        } else if (temperature < 0 && state.getValue(LIT)) {
            world.setBlock(pos, state.setValue(LIT, false), 2);
        }
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter world, BlockPos pos) {
        return -1842206;
    }

    private static final boolean HAS_ASH = !ModList.get().isLoaded("supplementaries");

    public static boolean tryConvertToAsh(Level level, BlockPos pos, BlockState state) {
        if (level.random.nextFloat() < 0.5) {

            if (level.random.nextFloat() < 0.65) {
                /*
                BlockState toPlace = getMaximumAttachmentState(ModBlocks.SOOT.get(), pos, level);
                if (toPlace != null) {
                    level.setBlock(pos, toPlace.setValue(SootBlock.LIT, true), Block.UPDATE_CLIENTS);
                    return true;
                }*/
            } else if (HAS_ASH && !state.isAir()) {
                level.setBlock(pos, ModBlocks.ASH_BLOCK.get().defaultBlockState()
                        .setValue(SootLayerBlock.LIT, level.random.nextBoolean()), Block.UPDATE_CLIENTS);
                return true;
            }
        }
        return false;
    }


}
