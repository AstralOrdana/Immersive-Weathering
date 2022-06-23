package com.ordana.immersive_weathering.registry.blocks.sandy;

import com.ordana.immersive_weathering.registry.blocks.Weatherable;
import net.minecraft.block.*;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SandyWallBlock extends WallBlock {
    public SandyWallBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView getter, BlockPos pos, ShapeContext context) {
        return super.getOutlineShape(state.with(SANDINESS, 0), getter, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView getter, BlockPos pos, ShapeContext context) {
        return super.getCollisionShape(state.with(SANDINESS, 0), getter, pos, context);
    }

    public static final IntProperty SANDINESS = IntProperty.of("sandiness", 0, 1);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SANDINESS);
        stateManager.add(new Property[]{UP, NORTH_SHAPE, EAST_SHAPE, WEST_SHAPE, SOUTH_SHAPE, WATERLOGGED});
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (random.nextInt(10) == 1) {
            BlockPos blockpos = pos.down();
            BlockState blockstate = world.getBlockState(blockpos);
            if (!blockstate.isOpaque() || !blockstate.isSideSolidFullSquare(world, blockpos, Direction.UP)) {
                double d0 = (double) pos.getX() + random.nextDouble();
                double d1 = (double) pos.getY() - 0.05D;
                double d2 = (double) pos.getZ() + random.nextDouble();
                if (state.get(SANDINESS) == 0 && random.nextInt(10) == 1) {
                    world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
                else world.addParticle(new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, Blocks.SAND.getDefaultState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
