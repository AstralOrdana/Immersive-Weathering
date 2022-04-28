package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.data.BlockGrowthHandler;
import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
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

public class CrackedMudBlock extends Block {

    public CrackedMudBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SOAKED, false).with(FERTILE, true));
    }

    public static final BooleanProperty SOAKED = BooleanProperty.of("soaked");
    public static final BooleanProperty FERTILE = BooleanProperty.of("fertile");

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.shouldCancelInteraction()) {
            if (player.getStackInHand(hand).isOf(Items.WATER_BUCKET) && !state.get(SOAKED)) {
                if (!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(Items.BUCKET));
                }
                world.setBlockState(pos, state.with(SOAKED, true));
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            } else if (player.getStackInHand(hand).isOf(Items.BUCKET) && state.get(SOAKED)) {
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
    public void randomDisplayTick(BlockState state, World level, BlockPos pos, Random random) {
        if (state.get(SOAKED)) {
            if (random.nextInt(25) == 1) {
                BlockPos blockpos = pos.down();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.isOpaque() || !blockstate.isSideSolidFullSquare(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(FERTILE) && state.get(SOAKED)) {
            BlockGrowthHandler.tickBlock(state, world, pos);
        }

        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            var biome = world.getBiome(pos);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                isTouchingWater = true;
            }
            if (world.hasRain(pos.offset(direction)) || biome.isIn(ModTags.WET) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                temperature--;
            } else if (neighborState.isIn(ModTags.MAGMA_SOURCE) || biome.isIn(ModTags.HOT) || world.getRegistryKey() == World.NETHER) {
                temperature++;
            }
        }
        if (temperature < 0 || isTouchingWater) {
            if (!state.get(SOAKED)) {
                world.setBlockState(pos, state.with(SOAKED, true));
            }
        }
        else if (temperature > 0 && state.get(SOAKED)) {
            world.setBlockState(pos, state.with(SOAKED, false));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
        stateManager.add(FERTILE);
    }
}