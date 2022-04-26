package com.ordana.immersive_weathering.registry.blocks;

import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.Random;

public class FulguriteBlock extends AmethystClusterBlock {
    public FulguriteBlock(int height, int xzOffset, Settings settings) {
        super(height, xzOffset, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.UP).with(WATERLOGGED, false).with(POWERED, false));
    }

    public static final BooleanProperty POWERED;

    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return (Boolean)state.get(POWERED) ? 15 : 0;
    }

    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return (Boolean)state.get(POWERED) && state.get(FACING) == direction ? 15 : 0;
    }

    public void setPowered(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, (BlockState)state.with(POWERED, true), 3);
        this.updateNeighbors(state, world, pos);
        world.createAndScheduleBlockTick(pos, this, 8);
        world.syncWorldEvent(3002, pos, ((Direction)state.get(FACING)).getAxis().ordinal());
    }

    private void updateNeighbors(BlockState state, World world, BlockPos pos) {
        world.updateNeighborsAlways(pos.offset(((Direction)state.get(FACING)).getOpposite()), this);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, (BlockState)state.with(POWERED, false), 3);
        this.updateNeighbors(state, world, pos);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isThundering() && (long)world.random.nextInt(200) <= world.getTime() % 200L && pos.getY() == world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ()) - 1) {
            ParticleUtil.spawnParticle(((Direction)state.get(FACING)).getAxis(), world, pos, 0.125D, ParticleTypes.ELECTRIC_SPARK, UniformIntProvider.create(1, 2));
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if ((Boolean)state.get(POWERED)) {
                this.updateNeighbors(state, world, pos);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!state.isOf(oldState.getBlock())) {
            if ((Boolean)state.get(POWERED) && !world.getBlockTickScheduler().isQueued(pos, this)) {
                world.setBlockState(pos, (BlockState)state.with(POWERED, false), 18);
            }

        }
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (world.isThundering() && projectile instanceof TridentEntity && ((TridentEntity)projectile).hasChanneling()) {
            BlockPos blockPos = hit.getBlockPos();
            if (world.isSkyVisible(blockPos)) {
                LightningEntity lightningEntity = (LightningEntity) EntityType.LIGHTNING_BOLT.create(world);
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos.up()));
                Entity entity = projectile.getOwner();
                lightningEntity.setChanneler(entity instanceof ServerPlayerEntity ? (ServerPlayerEntity)entity : null);
                world.spawnEntity(lightningEntity);
                world.playSound((PlayerEntity)null, blockPos, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.WEATHER, 5.0F, 1.0F);
            }
        }

    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, POWERED, WATERLOGGED});
    }

    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    static {
        POWERED = Properties.POWERED;
    }
}
