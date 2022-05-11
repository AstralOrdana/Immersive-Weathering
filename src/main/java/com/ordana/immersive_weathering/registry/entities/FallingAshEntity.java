package com.ordana.immersive_weathering.registry.entities;

import com.ordana.immersive_weathering.registry.blocks.AshLayerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LandingBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Author: MehVahdJukaar
 */

public class FallingAshEntity extends ImprovedFallingBlockEntity {

    public FallingAshEntity(EntityType<FallingAshEntity> type, World level) {
        super(type, level);
    }

    public FallingAshEntity(World level) {
        super(ModEntities.FALLING_ASH, level);
    }

    public FallingAshEntity(World level, BlockPos pos, BlockState blockState) {
        super(ModEntities.FALLING_ASH, level, pos, blockState, false);
    }

    public static FallingAshEntity fall(World level, BlockPos pos, BlockState state) {
        FallingAshEntity entity = new FallingAshEntity(level, pos, state);
        level.setBlockState(pos, state.getFluidState().getBlockState(), 3);
        level.spawnEntity(entity);
        return entity;
    }

    @Nullable
    @Override
    public ItemEntity dropItem(ItemConvertible pItem) {
        this.dropBlockContent(this.getBlockState(), this.getBlockPos());
        return null;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        if (world.isClient) {
            super.tick();
            return;
        }
        BlockState blockState = this.getBlockState();
        if (blockState.isAir()) {
            this.discard();
        } else {
            Block block = blockState.getBlock();
            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MovementType.SELF, this.getVelocity());
            if (!this.world.isClient) {
                BlockPos pos = this.getBlockPos();
                if (this.world.getFluidState(pos).isIn(FluidTags.WATER)) {
                    this.discard();
                    return;
                }
                if (this.getVelocity().lengthSquared() > 1.0D) {
                    BlockHitResult blockhitresult = this.world.raycast(new RaycastContext(new Vec3d(this.prevX, this.prevY, this.prevZ), this.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.SOURCE_ONLY, this));
                    if (blockhitresult.getType() != HitResult.Type.MISS && this.world.getFluidState(blockhitresult.getBlockPos()).isIn(FluidTags.WATER)) {
                        this.discard();
                        return;
                    }
                }


                //fall
                if (!this.onGround) {
                    if (!this.world.isClient && (this.timeFalling > 100 && (pos.getY() <= this.world.getBottomY() || pos.getY() > this.world.getTopY()) || this.timeFalling > 600)) {
                        if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.dropItem(block);
                        }

                        this.discard();
                    }
                    //place
                } else {
                    BlockState onState = this.world.getBlockState(pos);
                    this.setVelocity(this.getVelocity().multiply(0.7D, -0.5D, 0.7D));
                    if (!onState.isOf(Blocks.MOVING_PISTON)) {

                        boolean canBeReplaced = onState.canReplace(new AutomaticItemPlacementContext(this.world, pos, Direction.DOWN,
                                new ItemStack(blockState.getBlock().asItem()), Direction.UP));
                        boolean isFree = isFree(this.world.getBlockState(pos.down()));
                        boolean canSurvive = blockState.canPlaceAt(this.world, pos) && !isFree;
                        if (canBeReplaced && canSurvive) {

                            int remaining = 0;

                            if (onState.isOf(blockState.getBlock())) {
                                int layers = blockState.get(AshLayerBlock.LAYERS);
                                int toLayers = onState.get(AshLayerBlock.LAYERS);
                                int total = layers + toLayers;
                                int target = MathHelper.clamp(total, 1, 8);
                                remaining = total - target;
                                blockState = blockState.with(AshLayerBlock.LAYERS, target);
                            }

                            if (this.world.setBlockState(pos, blockState, 3)) {
                                ((ServerWorld) this.world).getChunkManager().threadedAnvilChunkStorage.sendToOtherNearbyPlayers(this,
                                        new BlockUpdateS2CPacket(pos, this.world.getBlockState(pos)));

                                if (block instanceof LandingBlock fallable) {
                                    fallable.onLanding(this.world, pos, blockState, onState, this);
                                }
                                this.discard();

                                if (remaining != 0) {
                                    BlockPos above = pos.up();
                                    blockState = blockState.with(AshLayerBlock.LAYERS, remaining);
                                    if (world.getBlockState(above).getMaterial().isReplaceable()) {
                                        if (!this.world.setBlockState(above, blockState, 3)) {
                                            ((ServerWorld) this.world).getChunkManager().threadedAnvilChunkStorage.sendToOtherNearbyPlayers(this,
                                                    new BlockUpdateS2CPacket(above, this.world.getBlockState(above)));
                                            this.dropBlockContent(blockState, pos);
                                        }
                                    }
                                }


                                return;
                            }
                        }
                        this.discard();
                        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.onDestroyedOnLanding(block, pos);
                            this.dropBlockContent(blockState, pos);
                        }
                    }
                }
            }

            this.setVelocity(this.getVelocity().multiply(0.98D));
        }
    }

    public static boolean isFree(BlockState pState) {
        Material material = pState.getMaterial();
        return pState.isAir() || pState.isIn(BlockTags.FIRE) || material.isLiquid() || (material.isReplaceable() && !(pState.getBlock() instanceof AshLayerBlock));
    }


    private void dropBlockContent(BlockState state, BlockPos pos) {
        Block.dropStacks(state, world, pos, null, null, ItemStack.EMPTY);

        world.syncWorldEvent(null, 2001, pos, Block.getRawIdFromState(state));
    }
}