package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class NulchBlock extends Block {

    public NulchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(MOLTEN, false));
    }

    public static final BooleanProperty MOLTEN = BooleanProperty.of("molten");

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.shouldCancelInteraction()) {
            // empty bucket into mulch
            if (player.getStackInHand(hand).isOf(Items.LAVA_BUCKET) && !state.get(MOLTEN)) {
                if (!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(Items.BUCKET));
                }
                world.setBlockState(pos, state.with(MOLTEN, true));
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
            // fill bucket from mulch
            else if (player.getStackInHand(hand).isOf(Items.BUCKET) && state.get(MOLTEN)) {
                if (!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(Items.LAVA_BUCKET));
                }
                world.setBlockState(pos, state.with(MOLTEN, false));
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        Optional<RegistryKey<Biome>> j = world.getBiomeKey(pos);
        if (Objects.equals(j, Optional.of(BiomeKeys.ICE_SPIKES))) {
            if (world.random.nextFloat() < 0.08f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.FROZEN_OCEAN))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.FROZEN_PEAKS))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.FROZEN_RIVER))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.DEEP_FROZEN_OCEAN))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.COLD_OCEAN))) {
            if (world.random.nextFloat() < 0.01f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.DEEP_COLD_OCEAN))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.SNOWY_TAIGA))) {
            if (world.random.nextFloat() < 0.01f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.SNOWY_SLOPES))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.SNOWY_PLAINS))) {
            if (world.random.nextFloat() < 0.01f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.SNOWY_BEACH))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(MOLTEN, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.BASALT_DELTAS))) {
            if (world.random.nextFloat() < 0.15f) {
                world.setBlockState(pos, state.with(MOLTEN, true));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.NETHER_WASTES))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(MOLTEN, true));
            }
        }
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (state.get(MOLTEN)) {
            if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.damage(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(MOLTEN);
    }
}
