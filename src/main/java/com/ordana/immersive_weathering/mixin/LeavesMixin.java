package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.common.blocks.ModBlocks;
import com.ordana.immersive_weathering.common.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements BonemealableBlock {

    public LeavesMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(LeavesBlock.PERSISTENT);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci) {

        //Drastically reduced this chance to help lag
        if (!state.getValue(LeavesBlock.PERSISTENT) && random.nextFloat() < 0.03f) {

            var leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
            if (leafPile != null && level.getBlockState(pos.below()).isAir()) {


                if (WeatheringHelper.isIciclePos(pos) && level.getBiome(pos).value().coldEnoughToSnow(pos)) {
                    level.setBlock(pos.below(), ModBlocks.ICICLE.get().defaultBlockState()
                            .setValue(PointedDripstoneBlock.TIP_DIRECTION, Direction.DOWN), 2);
                }

                if (!level.isAreaLoaded(pos, 2)) return;
                BlockPos targetPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
                int maxFallenLeavesReach = 12;
                int maxPileHeight = 3;
                int dist = pos.getY() - targetPos.getY();
                //calculating normally if heightmap fails
                if (dist < 0) {
                    targetPos = pos;
                    do {
                        targetPos = targetPos.below();
                        dist = pos.getY() - targetPos.getY();
                    } while (level.getBlockState(targetPos).getMaterial().isReplaceable() &&
                            dist < maxFallenLeavesReach);
                    targetPos = targetPos.above();

                }
                if (dist < maxFallenLeavesReach) {

                    BlockState replaceState = level.getBlockState(targetPos);

                    boolean isOnLeaf = replaceState.getBlock() instanceof LeafPileBlock;
                    int pileHeight = 1;
                    if (isOnLeaf) {
                        pileHeight = replaceState.getValue(LeafPileBlock.LAYERS);
                        if (pileHeight == 0 || pileHeight >= maxPileHeight) return;
                    }

                    BlockState baseLeaf = leafPile.defaultBlockState().setValue(LeafPileBlock.LAYERS, 0);
                    //if we find a non-air block we check if its upper face is sturdy. Given previous iteration if we are not on the first cycle blocks above must be air
                    if (isOnLeaf ||
                            (replaceState.getMaterial().isReplaceable() && baseLeaf.canSurvive(level, targetPos)
                                    && !WeatheringHelper.hasEnoughBlocksAround(targetPos, 2, 2, 2,
                                    level, b -> b.getBlock() instanceof LeafPileBlock, 6))) {


                        if (level.getBlockState(targetPos.below()).is(Blocks.WATER)) {
                            level.setBlock(targetPos, baseLeaf.setValue(LeafPileBlock.LAYERS, 0), 2);
                        } else {
                            if (isOnLeaf) {
                                int original = pileHeight;
                                boolean hasLog = false;
                                BlockState[] neighbors = new BlockState[4];
                                for (Direction direction : Direction.Plane.HORIZONTAL) {
                                    neighbors[direction.get2DDataValue()] = level.getBlockState(targetPos.relative(direction));
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
                                                Math.min(neighbor.getValue(LeafPileBlock.LAYERS) - 1, maxPileHeight);
                                        if (i > pileHeight) {
                                            pileHeight = Math.min(pileHeight + 1, i);
                                            break;
                                        }
                                    }
                                }
                                if (pileHeight == original) return;
                            }
                            level.setBlock(targetPos, baseLeaf.setValue(LeafPileBlock.LAYERS, pileHeight), 2);
                        }
                    }

                }
            }
        }
    }


    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockAndUpdate(targetPos, s)
                );
            }
        }
    }
}
