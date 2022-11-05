package com.ordana.immersive_weathering.mixins.forge;

import com.ordana.immersive_weathering.blocks.soil.MulchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MulchBlock.class)
public abstract class SelfMulchMixin extends Block {

    protected SelfMulchMixin(Properties arg) {
        super(arg);
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction direction, IPlantable plantable) {
        if (plantable.getPlantType(world, pos) != PlantType.NETHER) return true;
        return super.canSustainPlant(state, world, pos, direction, plantable);
    }
}
