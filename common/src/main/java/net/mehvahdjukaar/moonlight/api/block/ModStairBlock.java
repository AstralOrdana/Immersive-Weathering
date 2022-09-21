package net.mehvahdjukaar.moonlight.api.block;

import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StairsShape;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ModStairBlock extends StairBlock {

    private static final Field FORGE_BLOCK_SUPPLIER = PlatformHelper.findField(StairBlock.class, "stateSupplier");

    public ModStairBlock(Supplier<Block> baseBlock, Properties settings) {
        super(FORGE_BLOCK_SUPPLIER == null ? baseBlock.get().defaultBlockState() : Blocks.AIR.defaultBlockState(), settings);
        if (FORGE_BLOCK_SUPPLIER != null) {
            FORGE_BLOCK_SUPPLIER.setAccessible(true);
            try {
                FORGE_BLOCK_SUPPLIER.set(this, (Supplier<BlockState>) () -> baseBlock.get().defaultBlockState());
            } catch (Exception ignored) {
            }
        }
    }

    public static StairsShape getStairsShape(BlockState state, BlockGetter level, BlockPos pos) {
        Direction direction = (Direction)state.getValue(FACING);
        BlockState blockState = level.getBlockState(pos.relative(direction));
        if (isStairs(blockState) && state.getValue(HALF) == blockState.getValue(HALF)) {
            Direction direction2 = (Direction)blockState.getValue(FACING);
            if (direction2.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && canTakeShape(state, level, pos, direction2.getOpposite())) {
                if (direction2 == direction.getCounterClockWise()) {
                    return StairsShape.OUTER_LEFT;
                }

                return StairsShape.OUTER_RIGHT;
            }
        }

        BlockState blockState2 = level.getBlockState(pos.relative(direction.getOpposite()));
        if (isStairs(blockState2) && state.getValue(HALF) == blockState2.getValue(HALF)) {
            Direction direction3 = (Direction)blockState2.getValue(FACING);
            if (direction3.getAxis() != ((Direction)state.getValue(FACING)).getAxis() && canTakeShape(state, level, pos, direction3)) {
                if (direction3 == direction.getCounterClockWise()) {
                    return StairsShape.INNER_LEFT;
                }

                return StairsShape.INNER_RIGHT;
            }
        }

        return StairsShape.STRAIGHT;
    }

    private static boolean canTakeShape(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        BlockState blockState = level.getBlockState(pos.relative(face));
        return !isStairs(blockState) || blockState.getValue(FACING) != state.getValue(FACING) || blockState.getValue(HALF) != state.getValue(HALF);
    }
}
