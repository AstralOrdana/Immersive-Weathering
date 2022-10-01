package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.configs.ClientConfigs;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.reg.LeafPilesRegistry;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class LeavesGrowth extends BuiltinBlockGrowth {

    public LeavesGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    //TODO: add particles here too
    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        Random random = level.random;

        //Drastically reduced this chance to help lag
        //checking if it has this properties because mcreator mods...
        if ((!state.hasProperty(LeavesBlock.PERSISTENT) || !state.getValue(LeavesBlock.PERSISTENT))
                && random.nextFloat() < CommonConfigs.LEAF_PILES_REACH.get()) {

            var leafPile = LeafPilesRegistry.getFallenLeafPile(state).orElse(null);
            if (leafPile != null && level.getBlockState(pos.below()).isAir()) {


                //also spawns icicles
                //TODO: mvoe out of here to data
                if (random.nextBoolean() && WeatheringHelper.isIciclePos(pos) && level.getBiome(pos).value().coldEnoughToSnow(pos)) {
                    level.setBlock(pos.below(), ModBlocks.ICICLE.get().defaultBlockState()
                            .setValue(PointedDripstoneBlock.TIP_DIRECTION, Direction.DOWN), 2);
                }

                if (!PlatformHelper.isAreaLoaded(level, pos, 2)) return;
                BlockPos targetPos = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos);
                int maxFallenLeavesReach = CommonConfigs.LEAF_PILES_REACH.get();
                int maxPileHeight = CommonConfigs.LEAF_PILE_MAX_HEIGHT.get();
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


    //called from mixin. Client Side
    public static void spawnLeavesParticles(BlockState state, Level level, BlockPos pos, Random random) {
        if (ClientConfigs.FALLING_LEAF_PARTICLES.get()) {
            if (!state.getValue(LeavesBlock.PERSISTENT)) {
                var leafParticle = LeafPilesRegistry.getFallenLeafParticle(state).orElse(null);
                if (leafParticle == null) return;
                int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0);
                BlockPos blockPos = pos.below();
                BlockState blockState = level.getBlockState(blockPos);
                if (!blockState.canOcclude() || !blockState.isFaceSturdy(level, blockPos, Direction.UP)) {
                    double d = (double) pos.getX() + random.nextDouble();
                    double e = (double) pos.getY() - 0.05;
                    double f = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(leafParticle, d, e, f, 0.0, color, 0.0);
                }
            }
        }
    }

    public static void decayLeavesPile(BlockState state, ServerLevel level, BlockPos pos) {
        //this is server side, cant access client configs. Also meed to send color and send particles doesnt support that
        /*
        if (ClientConfigs.LEAF_DECAY_PARTICLES.get()) {
            int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0);

            var leafParticle = LeafPilesRegistry.getFallenLeafParticle(state);
            leafParticle.ifPresent(p -> level.addParticle(
                    p,
                    (double) pos.getX() + 0.5D,
                    (double) pos.getY() + 0.5D,
                    (double) pos.getZ() + 0.5D,
                    10,
                    0.5D, color, 0.5D));
        }*/

        if (CommonConfigs.LEAF_DECAY_SOUND.get()) {
            level.playSound(null, pos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
        }

        if (CommonConfigs.LEAF_PILES_FROM_DECAY_CHANCE.get() > level.random.nextFloat()) {
            Block leafPile = LeafPilesRegistry.getFallenLeafPile(state).orElse(null);
            if (leafPile == null) return;
            BlockState baseLeaf = leafPile.defaultBlockState();

            level.setBlock(pos, baseLeaf.setValue(LeafPileBlock.LAYERS, Mth.randomBetweenInclusive(level.random, 1, 5)), 2);
        }
    }


}
