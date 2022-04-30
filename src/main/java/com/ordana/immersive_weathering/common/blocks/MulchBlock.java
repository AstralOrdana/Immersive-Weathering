package com.ordana.immersive_weathering.common.blocks;

import com.ordana.immersive_weathering.common.ModParticles;
import com.ordana.immersive_weathering.common.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Supplier;

public class MulchBlock extends Block {

    public static final BooleanProperty SOAKED = BooleanProperty.create("soaked");

    public MulchBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SOAKED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.isSecondaryUseActive()) {
            // empty bucket into mulch
            if (player.getItemInHand(hand).is(Items.WATER_BUCKET) && !state.getValue(SOAKED)) {
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, true));
                world.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
            // fill bucket from mulch
            else if (player.getItemInHand(hand).is(Items.BUCKET) && state.getValue(SOAKED)) {
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
                }
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, false));
                world.playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {

        BlockState campfireState = world.getBlockState(pos.above());
        if (campfireState.is(Blocks.CAMPFIRE)) {

            if (random.nextFloat() < 0.2f) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, false));
                return;
            }
        }


        if (world.isRainingAt(pos.above())) {
            if (random.nextFloat() < 0.2f) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, true));
                return;
            }
        }

        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.is(ModTags.MAGMA_SOURCE)) {
                world.setBlock(pos, state.setValue(SOAKED, false), 2);
                return;
            }
        }
        var biome = world.getBiome(pos);
        if (biome.is(ModTags.HOT)) {
            if (world.random.nextFloat() < 0.07f) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, false));
            }
        } else if (biome.is(ModTags.WET)) {
            if (world.random.nextFloat() < 0.4f) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, true));
            }
        } else if (world.dimension() == Level.NETHER) {
            if (world.random.nextFloat() < 0.1f) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, false));
            }
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(SOAKED);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction direction, IPlantable plantable) {
        return true;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        if (entity instanceof LivingEntity) {
            if (level.isClientSide) {
                Random random = level.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {

                    level.addParticle(ModParticles.MULCH.get(),
                            entity.getX() + Mth.randomBetween(random,-0.2f,0.2f),
                            pos.getY() + 1.025,
                            entity.getZ() +Mth.randomBetween(random,-0.2f,0.2f),
                            Mth.randomBetween(random,-0.9f,-1),
                            -1,
                            0);
                }
            }
        }
    }
}
