package com.ordana.immersive_weathering.mixins.forge;

import com.ordana.immersive_weathering.blocks.soil.NulchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NulchBlock.class)
public abstract class SelfNulchMixin extends Block {

    public SelfNulchMixin(Properties arg) {
        super(arg);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        if (plantable.getPlantType(world, pos) == PlantType.NETHER) return true;
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        return super.hidesNeighborFace(level, pos, state, neighborState, dir);
    }
}
