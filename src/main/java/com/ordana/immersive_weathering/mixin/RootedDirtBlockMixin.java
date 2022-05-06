package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.block_growth.BlockGrowthHandler;
import com.ordana.immersive_weathering.registry.blocks.ModHangingRootsBlock;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(RootedDirtBlock.class)
public abstract class RootedDirtBlockMixin extends Block implements Fertilizable {

    public RootedDirtBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Shadow
    public abstract boolean canGrow(World world, Random random, BlockPos pos, BlockState state);

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Direction rootDir = Direction.values()[1 + random.nextInt(5)].getOpposite();
        BlockPos rootPos = pos.offset(rootDir);
        BlockState targetState = world.getBlockState(rootPos);
        BlockState toPlace = Blocks.HANGING_ROOTS.getDefaultState();
        if(targetState.isOf(Blocks.WATER)) {
            toPlace = toPlace.with(ModHangingRootsBlock.WATERLOGGED, true);
        }
        else if(!targetState.isAir())return;
        if (rootDir == Direction.DOWN) {
            world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.HANGING, true), 3);
        }
        else {
            world.setBlockState(rootPos, toPlace.with(ModHangingRootsBlock.FACING, (rootDir)).with(ModHangingRootsBlock.HANGING, Boolean.FALSE), 3);
        }
    }
}
