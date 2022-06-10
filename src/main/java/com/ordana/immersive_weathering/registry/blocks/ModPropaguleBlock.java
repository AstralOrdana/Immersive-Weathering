package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.entities.FallingAshEntity;
import com.ordana.immersive_weathering.registry.entities.FallingPropaguleEntity;
import com.ordana.immersive_weathering.registry.entities.ModEntities;
import net.minecraft.block.*;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ModPropaguleBlock extends PropaguleBlock implements LandingBlock {
    private static final BooleanProperty WATERLOGGED;

    public ModPropaguleBlock(Settings settings) {
        super(settings);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{STAGE}).add(new Property[]{AGE}).add(new Property[]{WATERLOGGED}).add(HANGING);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.createAndScheduleBlockTick(pos, this, this.getFallDelay());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        else if ((!state.get(HANGING) || (state.get(HANGING) && state.get(AGE) < 4)) && !state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
        world.createAndScheduleBlockTick(pos, this, this.getFallDelay());
        return state;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (canFallThrough(world.getBlockState(pos.down())) && pos.getY() >= world.getBottomY() && (world.getBlockState(pos.up()).isAir() || world.getBlockState(pos.up()).isIn(ModTags.LEAF_PILES)) && state.get(HANGING) && state.get(AGE) == 4) {
            FallingBlockEntity fallingblockentity = FallingPropaguleEntity.fall(world, pos, state);
            this.configureFallingBlockEntity(fallingblockentity);
        }
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
    }

    protected int getFallDelay() {
        return 2;
    }

    public static boolean canFallThrough(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.isIn(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(16) == 0) {
            BlockPos blockPos = pos.down();
            if (canFallThrough(world.getBlockState(blockPos))) {
                double d = (double)pos.getX() + random.nextDouble();
                double e = (double)pos.getY() - 0.05D;
                double f = (double)pos.getZ() + random.nextDouble();
                world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return -16777216;
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }
}