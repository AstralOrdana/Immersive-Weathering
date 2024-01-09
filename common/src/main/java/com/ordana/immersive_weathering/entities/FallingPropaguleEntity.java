package com.ordana.immersive_weathering.entities;

import com.ordana.immersive_weathering.reg.ModEntities;
import net.mehvahdjukaar.moonlight.api.entity.ImprovedFallingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class FallingPropaguleEntity extends ImprovedFallingBlockEntity {
    public FallingPropaguleEntity(EntityType<? extends FallingBlockEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void setBlockState(BlockState state) {
        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            state = state.setValue(BlockStateProperties.WATERLOGGED, false);
        }
        if (state.hasProperty(BlockStateProperties.HANGING)) {
            state = state.setValue(BlockStateProperties.HANGING, true);
        }
        CompoundTag tag = new CompoundTag();
        tag.put("BlockState", NbtUtils.writeBlockState(state));
        tag.putInt("Time", this.time);
        this.readAdditionalSaveData(tag);
    }

    public FallingPropaguleEntity(Level level, BlockPos pos, BlockState blockState) {
        super(ModEntities.FALLING_PROPAGULE.get(), level, pos, blockState, false);
    }

    public static FallingPropaguleEntity fall(Level level, BlockPos pos, BlockState state) {
        FallingPropaguleEntity entity = new FallingPropaguleEntity(level, pos, state);
        level.setBlockAndUpdate(pos, state.getFluidState().createLegacyBlock());
        level.addFreshEntity(entity);
        return entity;
    }
}
