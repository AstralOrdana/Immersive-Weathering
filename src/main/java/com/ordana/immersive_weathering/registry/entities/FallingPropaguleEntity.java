package com.ordana.immersive_weathering.registry.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingPropaguleEntity extends ImprovedFallingBlockEntity {
    public FallingPropaguleEntity(EntityType<? extends FallingBlockEntity> type, World level) {
        super(type, level);
    }

    @Override
    public void setBlockState(BlockState state) {
        if (state.contains(Properties.WATERLOGGED)) {
            state = state.with(Properties.WATERLOGGED, false);
        }
        if (state.contains(Properties.HANGING)) {
            state = state.with(Properties.HANGING, false);
        }
        NbtCompound tag = new NbtCompound();
        tag.put("BlockState", NbtHelper.fromBlockState(state));
        tag.putInt("Time", this.timeFalling);
        this.readCustomDataFromNbt(tag);
    }

    public FallingPropaguleEntity(World level, BlockPos pos, BlockState blockState) {
        super(ModEntities.FALLING_PROPAGULE, level, pos, blockState, false);
    }

    public static FallingPropaguleEntity fall(World level, BlockPos pos, BlockState state) {
        FallingPropaguleEntity entity = new FallingPropaguleEntity(level, pos, state);
        level.setBlockState(pos, state.getFluidState().getBlockState(), 3);
        level.spawnEntity(entity);
        return entity;
    }
}
