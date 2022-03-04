package com.ordana.immersive_weathering.registry.features;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

public class IcicleHelper {
    public IcicleHelper() {
    }

    protected static boolean canGenerate(LevelAccessor world, BlockPos pos) {
        return world.isStateAtPosition(pos, IcicleHelper::canGenerate);
    }

    protected static void getIcicleThickness(Direction direction, int height, boolean merge, Consumer<BlockState> callback) {
        if (height >= 3) {
            callback.accept(getState(direction, DripstoneThickness.BASE));

            for(int i = 0; i < height - 3; ++i) {
                callback.accept(getState(direction, DripstoneThickness.MIDDLE));
            }
        }

        if (height >= 2) {
            callback.accept(getState(direction, DripstoneThickness.FRUSTUM));
        }

        if (height >= 1) {
            callback.accept(getState(direction, merge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
        }

    }

    protected static void generateIcicle(LevelAccessor world, BlockPos pos, Direction direction, int height, boolean merge) {
        if (canReplace(world.getBlockState(pos.relative(direction.getOpposite())))) {
            BlockPos.MutableBlockPos mutable = pos.mutable();
            getIcicleThickness(direction, height, merge, (state) -> {
                if (state.is(ModBlocks.ICICLE)) {
                    state = state.setValue(IcicleBlock.WATERLOGGED, world.isWaterAt(mutable));
                }

                world.setBlock(mutable, state, 2);
                mutable.move(direction);
            });
        }
    }

    protected static boolean generateIceBlock(LevelAccessor world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.is(ModTags.ICICLE_REPLACEABLE_BLOCKS)) {
            world.setBlock(pos, Blocks.ICE.defaultBlockState(), 2);
            return true;
        } else {
            return false;
        }
    }

    private static BlockState getState(Direction direction, DripstoneThickness thickness) {
        return ModBlocks.ICICLE.defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, direction).setValue(IcicleBlock.THICKNESS, thickness);
    }

    public static boolean canReplaceOrLava(BlockState state) {
        return canReplace(state) || state.is(Blocks.LAVA);
    }

    public static boolean canReplace(BlockState state) {
        return state.is(ModTags.ICICLE_REPLACEABLE_BLOCKS);
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }
}
