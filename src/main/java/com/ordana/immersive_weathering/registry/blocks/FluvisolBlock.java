package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class FluvisolBlock extends SoilBlock implements IConditionalGrowingBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PUDDLE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public FluvisolBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SOAKED, false).with(FERTILE, true));
    }

    public static final BooleanProperty SOAKED = BooleanProperty.of("soaked");
    public static final BooleanProperty FERTILE = BooleanProperty.of("fertile");

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
        stateManager.add(FERTILE);
        stateManager.add(SNOWY);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(SOAKED)) {
            return PUDDLE_SHAPE;
        }
        else return SHAPE;
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        if (state.get(SOAKED)) {
            return PUDDLE_SHAPE;
        }
        else return SHAPE;
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.get(FERTILE) && state.get(SOAKED);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState upState = world.getBlockState(pos.up());
        BlockState downState = world.getBlockState(pos.down());
        if (downState.isOf(ModBlocks.SILT) || downState.isOf(ModBlocks.FLUVISOL)) {
            world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState());
        }
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (!state.get(SOAKED) && world.hasRain(pos.up())) {
                world.setBlockState(pos, state.with(SOAKED, true));
            }
            if (state.get(SOAKED) && !world.hasRain(pos.up())) {
                world.setBlockState(pos, state.with(SOAKED, false));
            }
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(SOAKED)) {
            if (world.isClient && (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this))) {
                Random random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(
                            ParticleTypes.SPLASH,
                            false,
                            entity.getX() + MathHelper.nextBetween(random, -0.2f, 0.2f),
                            pos.getY() + 1D,
                            entity.getZ() + MathHelper.nextBetween(random, -0.2f, 0.2f),
                            0D, 0.25D, 0D);
                }
            }
        }
    }
}


