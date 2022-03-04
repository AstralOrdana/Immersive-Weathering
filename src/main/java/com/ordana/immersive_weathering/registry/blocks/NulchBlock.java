package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class NulchBlock extends Block {

    public NulchBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(MOLTEN, false));
    }

    public static final BooleanProperty MOLTEN = BooleanProperty.create("molten");

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.isSecondaryUseActive()) {
            // empty bucket into mulch
            if (player.getItemInHand(hand).is(Items.LAVA_BUCKET) && !state.getValue(MOLTEN)) {
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, true));
                world.playSound(player, pos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
            // fill bucket from mulch
            else if (player.getItemInHand(hand).is(Items.BUCKET) && state.getValue(MOLTEN)) {
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.LAVA_BUCKET));
                }
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, false));
                world.playSound(player, pos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        var biome = world.getBiome(pos);
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.is(ModTags.MAGMA_SOURCE)) {
                if (world.isRainingAt(pos.above())) {
                    return;
                }
                world.setBlock(pos, state.setValue(MOLTEN, true), 2);
            }
        }
        if (biome.is(ModTags.ICY)) {
            if (world.random.nextFloat() < 0.4f) {
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, false));
            }
        }
        else if (world.dimension() == Level.NETHER) {
            if (world.random.nextFloat() < 0.5f) {
                world.setBlockAndUpdate(pos, state.setValue(MOLTEN, true));
            }
        }
    }

    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (state.getValue(MOLTEN)) {
            if (!entity.fireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.hurt(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(MOLTEN);
    }
}
