package com.ordana.immersive_weathering.common.blocks.soil;

import com.ordana.immersive_weathering.block_growth.IConditionalGrowingBlock;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Random;

public class SiltBlock extends Block implements IConditionalGrowingBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PUDDLE_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public SiltBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SOAKED, false).setValue(FERTILE, true));
    }

    public static final BooleanProperty SOAKED = BooleanProperty.create("soaked");
    public static final BooleanProperty FERTILE = BooleanProperty.create("fertile");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
        stateManager.add(FERTILE);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(SOAKED)) {
            return PUDDLE_SHAPE;
        }
        else return SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.getValue(SOAKED)) {
            return PUDDLE_SHAPE;
        }
        else return SHAPE;
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.getValue(FERTILE) && state.getValue(SOAKED);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        BlockState upState = world.getBlockState(pos.above());
        BlockState downState = world.getBlockState(pos.below());
        if (downState.is(BlockTags.DIRT)) {
            world.setBlockAndUpdate(pos.below(), ModBlocks.FLUVISOL.defaultBlockState());
        }
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState neighborState = world.getBlockState(targetPos);
            if (!state.getValue(SOAKED) && world.isRainingAt(pos.above())) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, true));
            }
            if (state.getValue(SOAKED) && !world.isRainingAt(pos.above())) {
                world.setBlockAndUpdate(pos, state.setValue(SOAKED, false));
            }
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (state.getValue(SOAKED)) {
            if (world.isClientSide && (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this))) {
                Random random = world.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(
                            ParticleTypes.SPLASH,
                            false,
                            entity.getX() + Mth.randomBetween(random, -0.2f, 0.2f),
                            pos.getY() + 1D,
                            entity.getZ() + Mth.randomBetween(random, -0.2f, 0.2f),
                            0D, 0.25D, 0D);
                }
            }
        }
    }
}


