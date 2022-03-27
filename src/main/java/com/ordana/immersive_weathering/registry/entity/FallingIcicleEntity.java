package com.ordana.immersive_weathering.registry.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FallingIcicleEntity extends FallingBlockEntity {

    public FallingIcicleEntity(EntityType<? extends FallingBlockEntity> type, World world) {
        super(type, world);
    }

    public FallingIcicleEntity(World world, double x, double y, double z, BlockState state) {
        this(ModEntities.FALLING_ICICLE, world);
        this.setBlockState(state);
        this.intersectionChecked = true;
        this.setPosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.setFallingBlockPos(this.getBlockPos());
    }

    //workaround
    public void setBlockState(BlockState state) {
        if (state.contains(Properties.WATERLOGGED)) {
            state = state.with(Properties.WATERLOGGED, false);
        }
        NbtCompound tag = new NbtCompound();
        tag.put("BlockState", NbtHelper.fromBlockState(state));
        tag.putInt("Time", this.timeFalling);
        this.readCustomDataFromNbt(tag);
    }

    public static FallingBlockEntity spawnFromBlock (World world, BlockPos pos, BlockState state) {
        FallingBlockEntity fallingblockentity = new FallingIcicleEntity(world,
                pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state);
        world.setBlockState(pos, state.getFluidState().getBlockState(), 3);
        world.spawnEntity(fallingblockentity);
        return fallingblockentity;
    }

    //so much for this... ty mojang
    @Nullable
    @Override
    public ItemEntity dropItem(ItemConvertible item) {
        return null;
    }
}