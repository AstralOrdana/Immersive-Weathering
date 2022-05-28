package com.ordana.immersive_weathering.registry.features;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Thickness;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import java.util.function.Consumer;

public class IcicleHelper {
    public IcicleHelper() {
    }

    protected static boolean canGenerate(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, IcicleHelper::canGenerate);
    }

    protected static void getIcicleThickness(Direction direction, int height, boolean merge, Consumer<BlockState> callback) {
        if (height >= 3) {
            callback.accept(getState(direction, Thickness.BASE));

            for(int i = 0; i < height - 3; ++i) {
                callback.accept(getState(direction, Thickness.MIDDLE));
            }
        }

        if (height >= 2) {
            callback.accept(getState(direction, Thickness.FRUSTUM));
        }

        if (height >= 1) {
            callback.accept(getState(direction, merge ? Thickness.TIP_MERGE : Thickness.TIP));
        }

    }

    protected static void generateIcicle(WorldAccess world, BlockPos pos, Direction direction, int height, boolean merge) {
        if (canReplace(world.getBlockState(pos.offset(direction.getOpposite())))) {
            BlockPos.Mutable mutable = pos.mutableCopy();
            getIcicleThickness(direction, height, merge, (state) -> {
                if (state.isOf(ModBlocks.ICICLE)) {
                    state = state.with(IcicleBlock.WATERLOGGED, world.isWater(mutable));
                }

                world.setBlockState(mutable, state, 2);
                mutable.move(direction);
            });
        }
    }

    protected static boolean generateIceBlock(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isIn(ModTags.ICICLE_REPLACEABLE_BLOCKS)) {
            world.setBlockState(pos, Blocks.PACKED_ICE.getDefaultState(), 2);
            return true;
        } else {
            return false;
        }
    }

    private static BlockState getState(Direction direction, Thickness thickness) {
        return ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, direction).with(IcicleBlock.THICKNESS, thickness);
    }

    public static boolean canReplaceOrLava(BlockState state) {
        return canReplace(state) || state.isOf(Blocks.LAVA);
    }

    public static boolean canReplace(BlockState state) {
        return state.isIn(ModTags.ICICLE_REPLACEABLE_BLOCKS);
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER);
    }
}
