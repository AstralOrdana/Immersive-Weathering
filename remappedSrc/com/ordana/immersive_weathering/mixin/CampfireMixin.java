package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CampfireBlock.class)
public class CampfireMixin extends BlockWithEntity{

    protected CampfireMixin(Settings settings) {
        super(settings);
    }

    @Nullable
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

    private boolean doesBlockCauseSignalFire(BlockState state) {
        return state.isIn(ModTags.SMOKEY_BLOCKS);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        int smokeHeight = 8;

        if (state.get(CampfireBlock.SIGNAL_FIRE)) {
            smokeHeight = 23;
        }

        if (state.get(CampfireBlock.LIT)) {
            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.up();
                BlockState above = world.getBlockState(sootPos.up());
                if (Block.isFaceFullSquare(above.getCollisionShape(world, sootPos.up()), Direction.DOWN)) {
                    if (world.getBlockState(sootPos).isAir()) {
                        world.setBlockState(sootPos, ModBlocks.SOOT.getDefaultState().with(Properties.UP, true), Block.NOTIFY_LISTENERS);
                    }
                    smokeHeight = i+1;
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
                }
                else if (currentState.isAir()) {
                    world.setBlockState(sootBlock, ModBlocks.SOOT.getDefaultState().with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CampfireBlockEntity(pos, state);
    }
}