package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements Fertilizable {

    public LeavesMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return !(Boolean)state.get(LeavesBlock.PERSISTENT);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {

        //Drastically reduced this chance to help lag
        if (!state.get(LeavesBlock.PERSISTENT) && random.nextFloat() < 0.03f) {

            var leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
            if (leafPile != null && world.getBlockState(pos.down()).isIn(ModTags.LEAF_PILE_REPLACEABLE)) {


                if (WeatheringHelper.isIciclePos(pos) && world.getBiome(pos).value().isCold(pos)) {
                    world.setBlockState(pos.down(), ModBlocks.ICICLE.getDefaultState()
                            .with(PointedDripstoneBlock.VERTICAL_DIRECTION, Direction.DOWN), 2);
                }

                if (!world.isChunkLoaded(pos)) return;
                BlockPos targetPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
                int maxFallenLeavesReach = 12;
                int maxPileHeight = 3;
                int dist = pos.getY() - targetPos.getY();
                if (dist < 0) {
                    targetPos = pos;
                    do {
                        targetPos = targetPos.down();
                        dist = pos.getY() - targetPos.getY();
                    } while (world.getBlockState(targetPos).getMaterial().isReplaceable() &&
                            dist < maxFallenLeavesReach);
                    targetPos = targetPos.up();

                }
                if (dist < maxFallenLeavesReach) {

                    BlockState replaceState = world.getBlockState(targetPos);

                    boolean isOnLeaf = replaceState.getBlock() instanceof LeafPileBlock;

                    int pileHeight = 1;
                    if (isOnLeaf) {
                        pileHeight = replaceState.get(LeafPileBlock.LAYERS);
                        if (pileHeight == 0 || pileHeight >= maxPileHeight) return;
                    }

                    BlockState baseLeaf = leafPile.getDefaultState().with(LeafPileBlock.LAYERS, 0);
                    //if we find a non-air block we check if its upper face is sturdy. Given previous iteration if we are not on the first cycle blocks above must be air
                    if (isOnLeaf ||
                            (replaceState.getMaterial().isReplaceable() && baseLeaf.canPlaceAt(world, targetPos)
                                    && !WeatheringHelper.hasEnoughBlocksAround(targetPos, 2, 1, 2,
                                    world, b -> b.getBlock() instanceof LeafPileBlock, 6))) {


                        if (world.getBlockState(targetPos.down()).isOf(Blocks.WATER)) {
                            world.setBlockState(targetPos, baseLeaf.with(LeafPileBlock.LAYERS, 0), 2);
                        } else {
                            if (isOnLeaf) {
                                int original = pileHeight;
                                boolean hasLog = false;
                                BlockState[] neighbors = new BlockState[4];
                                for (Direction direction : Direction.Type.HORIZONTAL) {
                                    neighbors[direction.getHorizontal()] = world.getBlockState(targetPos.offset(direction));
                                }
                                for (var neighbor : neighbors) {
                                    if (WeatheringHelper.isLog(neighbor)) {
                                        hasLog = true;
                                        break;
                                    }
                                }
                                for (var neighbor : neighbors) {
                                    if (neighbor.getBlock() instanceof LeafPileBlock) {
                                        int i = hasLog ? maxPileHeight :
                                                Math.min(neighbor.get(LeafPileBlock.LAYERS) -1, maxPileHeight);
                                        if (i > pileHeight) {
                                            pileHeight = Math.min(pileHeight + 1, i);
                                            break;
                                        }
                                    }
                                }
                                if (pileHeight == original) return;
                            }
                            world.setBlockState(targetPos, baseLeaf.with(LeafPileBlock.LAYERS, pileHeight), 2);
                        }
                    }

                }
            }
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.isOf(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.isOf(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.offset(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockState(targetPos, s)
                );
            }
        }
    }
}

