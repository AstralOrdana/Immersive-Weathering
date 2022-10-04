package com.ordana.immersive_weathering.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import net.minecraft.util.RandomSource;

public class FulguriteBlock extends AmethystClusterBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public FulguriteBlock(int height, int xzOffset, Properties settings) {
        super(height, xzOffset, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false).setValue(POWERED, false));
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return state.getValue(POWERED) && state.getValue(FACING) == direction ? 15 : 0;
    }

    public void setPowered(BlockState state, Level world, BlockPos pos) {
        world.setBlock(pos, state.setValue(POWERED, true), 3);
        this.updateNeighbors(state, world, pos);
        world.scheduleTick(pos, this, 8);
        world.levelEvent(3002, pos, state.getValue(FACING).getAxis().ordinal());
    }

    private void updateNeighbors(BlockState state, Level world, BlockPos pos) {
        world.updateNeighborsAt(pos.relative(state.getValue(FACING).getOpposite()), this);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        world.setBlock(pos, state.setValue(POWERED, false), 3);
        this.updateNeighbors(state, world, pos);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (world.isThundering() && (long) world.random.nextInt(200) <= world.getGameTime() % 200L && pos.getY() == world.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) - 1) {
            ParticleUtils.spawnParticlesAlongAxis(state.getValue(FACING).getAxis(), world, pos, 0.125D, ParticleTypes.ELECTRIC_SPARK, UniformInt.of(1, 2));
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            if (state.getValue(POWERED)) {
                this.updateNeighbors(state, world, pos);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.is(oldState.getBlock())) {
            if (state.getValue(POWERED) && !world.getBlockTicks().hasScheduledTick(pos, this)) {
                world.setBlock(pos, state.setValue(POWERED, false), 18);
            }
        }
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (world.isThundering() && projectile instanceof ThrownTrident && ((ThrownTrident) projectile).isChanneling()) {
            BlockPos blockPos = hit.getBlockPos();
            if (world.canSeeSky(blockPos)) {
                LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                lightningEntity.moveTo(Vec3.atBottomCenterOf(blockPos.above()));
                Entity entity = projectile.getOwner();
                lightningEntity.setCause(entity instanceof ServerPlayer ? (ServerPlayer) entity : null);
                world.addFreshEntity(lightningEntity);
                world.playSound(null, blockPos, SoundEvents.TRIDENT_THUNDER, SoundSource.WEATHER, 5.0F, 1.0F);
            }
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, WATERLOGGED);
    }

}
