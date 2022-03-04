package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CampfireBlock.class)
public class CampfireMixin extends BaseEntityBlock{

    protected CampfireMixin(Properties settings) {
        super(settings);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelAccessor worldAccess = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        boolean bl = worldAccess.getFluidState(blockPos).getType() == Fluids.WATER;
        return (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(CampfireBlock.WATERLOGGED, bl)).setValue(CampfireBlock.SIGNAL_FIRE, this.doesBlockCauseSignalFire(worldAccess.getBlockState(blockPos.below())))).setValue(CampfireBlock.LIT, !bl)).setValue(CampfireBlock.FACING, ctx.getHorizontalDirection());
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.getValue(CampfireBlock.WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return direction == Direction.DOWN ? (BlockState)state.setValue(CampfireBlock.SIGNAL_FIRE, this.doesBlockCauseSignalFire(neighborState)) : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private boolean doesBlockCauseSignalFire(BlockState state) {
        return state.is(ModTags.SMOKEY_BLOCKS);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {

        int smokeHeight = 8;

        if (state.getValue(CampfireBlock.SIGNAL_FIRE)) {
            smokeHeight = 23;
        }

        if (state.getValue(CampfireBlock.LIT)) {
            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.above();
                BlockState above = world.getBlockState(sootPos.above());
                if (Block.isFaceFull(above.getCollisionShape(world, sootPos.above()), Direction.DOWN)) {
                    if (world.getBlockState(sootPos).isAir()) {
                        world.setBlock(sootPos, ModBlocks.SOOT.defaultBlockState().setValue(BlockStateProperties.UP, true), Block.UPDATE_CLIENTS);
                    }
                    smokeHeight = i+1;
                }
            }
            BlockPos sootBlock = pos.above(random.nextInt(smokeHeight) + 1);
            int rand = random.nextInt(4);
            Direction sootDir = Direction.from2DDataValue(rand);
            BlockPos testPos = sootBlock.relative(sootDir);
            BlockState testBlock = world.getBlockState(testPos);

            if (Block.isFaceFull(testBlock.getCollisionShape(world, testPos), sootDir.getOpposite())) {
                BlockState currentState = world.getBlockState(sootBlock);
                if (currentState.is(ModBlocks.SOOT)) {
                    world.setBlock(sootBlock, currentState.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(sootDir), true), Block.UPDATE_CLIENTS);
                }
                else if (currentState.isAir()) {
                    world.setBlock(sootBlock, ModBlocks.SOOT.defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(sootDir), true), Block.UPDATE_CLIENTS);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CampfireBlockEntity(pos, state);
    }
}