package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class MulchBlock extends Block {

    public MulchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SOAKED, false));
    }

    public static final BooleanProperty SOAKED = BooleanProperty.of("soaked");

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.shouldCancelInteraction()) {
            // empty bucket into mulch
            if (player.getStackInHand(hand).isOf(Items.WATER_BUCKET) && !state.get(SOAKED)) {
                if (!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(Items.BUCKET));
                }
                world.setBlockState(pos, state.with(SOAKED, true));
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
            // fill bucket from mulch
            else if (player.getStackInHand(hand).isOf(Items.BUCKET) && state.get(SOAKED)) {
                if (!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
                }
                world.setBlockState(pos, state.with(SOAKED, false));
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.hasRain(pos.up())) {
            if (random.nextFloat() < 0.2f) {
                world.setBlockState(pos, state.with(SOAKED, true));
            }
        }
        Optional<RegistryKey<Biome>> j = world.getBiomeKey(pos);
        if (Objects.equals(j, Optional.of(BiomeKeys.DESERT))) {
            if (world.random.nextFloat() < 0.08f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.BADLANDS))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.ERODED_BADLANDS))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.WOODED_BADLANDS))) {
            if (world.random.nextFloat() < 0.01f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.NETHER_WASTES))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.CRIMSON_FOREST))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.BASALT_DELTAS))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.WARPED_FOREST))) {
            if (world.random.nextFloat() < 0.05f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
        else if (Objects.equals(j, Optional.of(BiomeKeys.SOUL_SAND_VALLEY))) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
    }
}
