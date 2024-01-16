package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.network.NetworkHandler;
import com.ordana.immersive_weathering.network.SendCustomParticlesPacket;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class LeavesGrowth extends BuiltinBlockGrowth {

    public LeavesGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources, float chance) {
        super(name, owners, sources, chance);
    }

    //TODO: add particles here too
    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Supplier<Holder<Biome>> biome) {
        if (!(growthChance == 1 || level.random.nextFloat() < growthChance)) return;

        RandomSource random = level.random;

        //Drastically reduced this chance to help lag
        //checking if it has these properties because mcreator mods...
        if ((!state.hasProperty(LeavesBlock.PERSISTENT) || !state.getValue(LeavesBlock.PERSISTENT))
                && random.nextFloat() < CommonConfigs.LEAF_PILES_REACH.get()) {

            var leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
            if (leafPile != null && level.getBlockState(pos.below()).isAir()) {


                //also spawns icicles
                //TODO: move out of here to data
                if (random.nextBoolean() && WeatheringHelper.isIciclePos(pos) && level.getBiome(pos).value().coldEnoughToSnow(pos)) {
                    level.setBlock(pos.below(), ModBlocks.ICICLE.get().defaultBlockState()
                            .setValue(PointedDripstoneBlock.TIP_DIRECTION, Direction.DOWN), 2);
                }

                if (!PlatHelper.isAreaLoaded(level, pos, 2)) return;
                BlockPos targetPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
                int maxFallenLeavesReach = CommonConfigs.LEAF_PILES_REACH.get();
                int maxPileHeight = CommonConfigs.LEAF_PILE_MAX_HEIGHT.get();
                int dist = pos.getY() - targetPos.getY();
                //calculating normally if heightmap fails
                if (dist < 0) {
                    targetPos = pos;
                    BlockState selected;
                    do {
                        targetPos = targetPos.below();
                        dist = pos.getY() - targetPos.getY();
                        selected = level.getBlockState(targetPos);
                    } while ((selected.canBeReplaced() && selected.getFluidState().isEmpty()) &&
                            dist < maxFallenLeavesReach);
                    targetPos = targetPos.above();

                }
                if (dist < maxFallenLeavesReach) {
                    tryPlacingAt(level, leafPile, targetPos, maxPileHeight);
                }
            }
        }
    }

    private void tryPlacingAt(ServerLevel level, Block leafPile, BlockPos targetPos, int maxPileHeight) {
        BlockState replaceState = level.getBlockState(targetPos);
        BlockState groundState = level.getBlockState(targetPos.below());

        boolean isOnLeaf = replaceState.getBlock() instanceof LeafPileBlock;
        boolean waterBelow = groundState.is(Blocks.WATER) && groundState.getFluidState().isSource();
        int pileHeight = 1;
        if (isOnLeaf) {
            pileHeight = replaceState.getValue(LeafPileBlock.LAYERS);
            if (pileHeight == 0 || pileHeight >= maxPileHeight) return;
        }
        BlockState baseLeaf = leafPile.defaultBlockState().setValue(LeafPileBlock.LAYERS, waterBelow ? 0 : 1);
        //if we find a non-air block we check if its upper face is sturdy. Given previous iteration if we are not on the first cycle blocks above must be air
        if (isOnLeaf || (
                replaceState.canBeReplaced()
                        && baseLeaf.canSurvive(level, targetPos)
                        && !WeatheringHelper.hasEnoughBlocksAround(targetPos, 2, 1, 2,
                        level, b -> b.getBlock() instanceof LeafPileBlock, 5)
        )) {


            if (waterBelow) {
                level.setBlock(targetPos, baseLeaf, 2);
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

    public static void decayLeavesPile(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        //this is server side, cant access client configs. Also meet to send color and send particles doesn't support that
        if (!(state.getBlock() instanceof LeavesBlock)) {
            ImmersiveWeathering.LOGGER.error("Some mod tried to call leaves random tick without passing a leaf block blockstate as expected. This should be fixed on their end. Given blockstate : {}", state);
            return;
        }
        if (CommonConfigs.LEAF_PILES_FROM_DECAY_CHANCE.get() > level.random.nextFloat()) {
            Block leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
            if (leafPile == null) return;
            BlockState baseLeaf = leafPile.defaultBlockState();

            level.setBlock(pos, baseLeaf.setValue(LeafPileBlock.LAYERS, Mth.randomBetweenInclusive(level.random, 1, 5)).setValue(LeafPileBlock.AGE, 1), 2);
        }


        BlockPos downPos = pos.below();
        BlockState downState = level.getBlockState(downPos);
        if (!downState.canOcclude() || !downState.isFaceSturdy(level, downPos, Direction.UP)) {

            //packet here
            NetworkHandler.CHANNEL.sendToAllClientPlayersInRange(level, pos, 32,
                    new SendCustomParticlesPacket(SendCustomParticlesPacket.EventType.DECAY_LEAVES,
                            pos, Block.getId(state)));
        }

    }


}
