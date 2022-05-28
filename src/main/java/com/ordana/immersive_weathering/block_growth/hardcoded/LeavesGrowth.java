package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.IBlockGrowth;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.Random;
import java.util.stream.Collectors;

public class LeavesGrowth implements IBlockGrowth {

    @Override
    public Iterable<Block> getOwners() {
        return Registry.BLOCK.getEntryList(BlockTags.LEAVES).get().stream().map(RegistryEntry::value).collect(Collectors.toList());
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerWorld world, RegistryEntry<Biome> biome) {
        if(ImmersiveWeathering.getConfig().leavesConfig.leafPilesForm) {
            Random random = world.random;
            //Drastically reduced this chance to help lag
            if (state.contains(LeavesBlock.PERSISTENT) && !state.get(LeavesBlock.PERSISTENT) && random.nextFloat() < 0.03f) {

                var leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
                if (leafPile != null && world.getBlockState(pos.down()).isIn(ModTags.LEAF_PILE_REPLACEABLE)) {


                    if (random.nextBoolean() && WeatheringHelper.isIciclePos(pos) && world.getBiome(pos).value().isCold(pos)) {
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
                                                    Math.min(neighbor.get(LeafPileBlock.LAYERS) - 1, maxPileHeight);
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
    }
}

