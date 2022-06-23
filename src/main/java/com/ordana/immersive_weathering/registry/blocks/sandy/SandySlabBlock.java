package com.ordana.immersive_weathering.registry.blocks.sandy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SandySlabBlock extends SlabBlock {
    public SandySlabBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SANDINESS, 0));
    }

    public static final IntProperty SANDINESS = IntProperty.of("sandiness", 0, 1);

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SANDINESS);
        stateManager.add(TYPE, WATERLOGGED);
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
