package com.ordana.immersive_weathering.registry.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * Author: MehVahdJukaar
 */

public class ImprovedFallingBlockEntity extends FallingBlockEntity {

    protected boolean saveTileDataToItem;

    public ImprovedFallingBlockEntity(EntityType<? extends FallingBlockEntity> type, World level) {
        super(type, level);
        saveTileDataToItem = false;
    }

    public ImprovedFallingBlockEntity(EntityType<? extends FallingBlockEntity> type, World level, BlockPos pos, BlockState blockState, boolean saveDataToItem) {
        super(type, level);
        this.intersectionChecked = true;
        this.prevX = pos.getX() + 0.5D;
        this.prevY = pos.getY();
        this.prevZ = pos.getZ() + 0.5D;
        this.setPos(prevX, prevY + (double) ((1.0F - this.getHeight()) / 2.0F), prevZ);
        this.setVelocity(Vec3d.ZERO);
        this.setFallingBlockPos(this.getBlockPos());
        this.setBlockState(blockState);
        this.saveTileDataToItem = saveDataToItem;
    }

    public static ImprovedFallingBlockEntity spawnFromBlock(EntityType<? extends FallingBlockEntity> type,
                                                  World level, BlockPos pos, BlockState state, boolean saveDataToItem) {
        ImprovedFallingBlockEntity entity = new ImprovedFallingBlockEntity(type, level, pos, state, saveDataToItem);
        level.setBlockState(pos, state.getFluidState().getBlockState(), 3);
        level.spawnEntity(entity);
        return entity;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.saveTileDataToItem = tag.getBoolean("saveToItem");
    }

    //        this.setHurtsEntities(1f, 20);

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

    @Override
    public ItemEntity dropItem (ItemConvertible itemIn, int offset) {
        ItemStack stack = new ItemStack(itemIn);
        return this.dropStack(stack, (float) offset);
    }


}
