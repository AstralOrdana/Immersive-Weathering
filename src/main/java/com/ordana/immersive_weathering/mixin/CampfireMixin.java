package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(CampfireBlock.class)
public abstract class CampfireMixin extends Block {

    protected CampfireMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    private boolean doesBlockCauseSignalFire(BlockState state) {
        return state.isIn(ModTags.SMOKEY_BLOCKS);
    }

    @Nullable
    @Shadow
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        boolean bl = worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER;
        return (BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(CampfireBlock.WATERLOGGED, bl)).with(CampfireBlock.SIGNAL_FIRE, this.doesBlockCauseSignalFire(worldAccess.getBlockState(blockPos.down())))).with(CampfireBlock.LIT, !bl)).with(CampfireBlock.FACING, ctx.getPlayerFacing());
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(CampfireBlock.WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction == Direction.DOWN ? (BlockState)state.with(CampfireBlock.SIGNAL_FIRE, this.doesBlockCauseSignalFire(neighborState)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        //TODO: move to soot class
        if (state.get(CampfireBlock.LIT)) {

            int smokeHeight = state.get(CampfireBlock.SIGNAL_FIRE) ? 23 : 8;

            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.up();
                BlockState above = world.getBlockState(sootPos.up());
                if (Block.isFaceFullSquare(above.getCollisionShape(world, sootPos.up()), Direction.DOWN)) {
                    if (world.getBlockState(sootPos).isAir()) {
                        world.setBlockState(sootPos, ModBlocks.SOOT.getDefaultState().with(Properties.UP, true), 3);
                    }
                    smokeHeight = i + 1;
                }
            }
            BlockPos sootBlock = pos.up(random.nextInt(smokeHeight) + 1);
            int rand = random.nextInt(4);
            Direction sootDir = Direction.fromHorizontal(rand);
            BlockPos testPos = sootBlock.offset(sootDir);
            BlockState testBlock = world.getBlockState(testPos);

            if (Block.isFaceFullSquare(testBlock.getCollisionShape(world, testPos), sootDir.getOpposite())) {
                BlockState currentState = world.getBlockState(sootBlock);
                if (currentState.isOf(ModBlocks.SOOT)) {
                    world.setBlockState(sootBlock, currentState.with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                } else if (currentState.isAir()) {
                    world.setBlockState(sootBlock, ModBlocks.SOOT.getDefaultState().with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                }
            }
        }
    }

}