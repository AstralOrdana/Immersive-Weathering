package com.ordana.immersive_weathering.registry.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.registry.blocks.CinderLichenBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GlowLichenFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CinderLichenFeature extends Feature<GlowLichenFeatureConfig> {
    public CinderLichenFeature(Codec<GlowLichenFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<GlowLichenFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        GlowLichenFeatureConfig glowLichenFeatureConfig = context.getConfig();
        if (isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
            return false;
        } else {
            List<Direction> list = shuffleDirections(glowLichenFeatureConfig, random);
            if (generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), glowLichenFeatureConfig, random, list)) {
                return true;
            } else {
                BlockPos.Mutable mutable = blockPos.mutableCopy();

                for (Direction direction : list) {
                    mutable.set(blockPos);
                    List<Direction> list2 = shuffleDirections(glowLichenFeatureConfig, random, direction.getOpposite());

                    for (int i = 0; i < glowLichenFeatureConfig.searchRange; ++i) {
                        mutable.set(blockPos, direction);
                        BlockState blockState = structureWorldAccess.getBlockState(mutable);
                        if (isAirOrWater(blockState) && !blockState.isOf(ModBlocks.CINDER_LICHEN)) {
                            break;
                        }

                        if (generate(structureWorldAccess, mutable, blockState, glowLichenFeatureConfig, random, list2)) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    public static boolean generate(StructureWorldAccess world, BlockPos pos, BlockState state, GlowLichenFeatureConfig config, Random random, List<Direction> directions) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        Iterator<Direction> var7 = directions.iterator();

        Direction direction;
        BlockState blockState;
        do {
            if (!var7.hasNext()) {
                return false;
            }

            direction = var7.next();
            blockState = world.getBlockState(mutable.set(pos, direction));
        } while(!blockState.isIn(config.canPlaceOn));

        CinderLichenBlock cinderLichenBlock = (CinderLichenBlock) ModBlocks.CINDER_LICHEN;
        BlockState blockState2 = cinderLichenBlock.withDirection(state, world, pos, direction);
        if (blockState2 == null) {
            return false;
        } else {
            world.setBlockState(pos, blockState2, 3);
            world.getChunk(pos).markBlockForPostProcessing(pos);
            if (random.nextFloat() < config.spreadChance) {
                cinderLichenBlock.trySpreadRandomly(blockState2, world, pos, direction, random, true);
            }

            return true;
        }
    }

    public static List<Direction> shuffleDirections(GlowLichenFeatureConfig config, Random random) {
        List<Direction> list = Lists.newArrayList(config.directions);
        Collections.shuffle(list, random);
        return list;
    }

    public static List<Direction> shuffleDirections(GlowLichenFeatureConfig config, Random random, Direction excluded) {
        List<Direction> list = config.directions.stream().filter((direction) -> direction != excluded).collect(Collectors.toList());
        Collections.shuffle(list, random);
        return list;
    }

    private static boolean isAirOrWater(BlockState state) {
        return !state.isAir() && !state.isOf(Blocks.WATER);
    }
}