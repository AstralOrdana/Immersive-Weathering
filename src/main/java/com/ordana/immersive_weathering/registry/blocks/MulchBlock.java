package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class MulchBlock extends Block {

    public static final BooleanProperty SOAKED = BooleanProperty.of("soaked");

    public MulchBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SOAKED, false));
    }

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

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        // RandomEvent ran = new RandomEvent();

        BlockState campfireState = world.getBlockState(pos.up());
        if (campfireState.isOf(Blocks.CAMPFIRE)) {

            if (random.nextFloat() < 0.2f) {
                world.setBlockState(pos, state.with(SOAKED, false));
                return;
            }
        }


        if (world.hasRain(pos.up())) {
            if (random.nextFloat() < 0.2f) {
                world.setBlockState(pos, state.with(SOAKED, true));
                return;
            }
        }

        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.isIn(ModTags.MAGMA_SOURCE)) {
                world.setBlockState(pos, state.with(SOAKED, false), 2);
                return;
            }
        }
        var biome = world.getBiome(pos);
        if (biome.isIn(ModTags.HOT)) {
            if (world.random.nextFloat() < 0.07f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        } else if (biome.isIn(ModTags.WET)) {
            if (world.random.nextFloat() < 0.4f) {
                world.setBlockState(pos, state.with(SOAKED, true));
            }
        } else if (world.getRegistryKey() == World.NETHER) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
    }
}
