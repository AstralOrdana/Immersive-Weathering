package com.ordana.immersive_weathering.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.blocks.IvyBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.GlowLichenConfiguration;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.RandomSource;
import java.util.stream.Collectors;

public class IvyFeature extends Feature<GlowLichenConfiguration> {
    public IvyFeature(Codec<GlowLichenConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<GlowLichenConfiguration> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        GlowLichenConfiguration glowLichenFeatureConfig = context.config();
        if (isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
            return false;
        } else {
            List<Direction> list = shuffleDirections(glowLichenFeatureConfig, random);
            if (generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), glowLichenFeatureConfig, random, list)) {
                return true;
            } else {
                BlockPos.MutableBlockPos mutable = blockPos.mutable();

                for (Direction direction : list) {
                    mutable.set(blockPos);
                    List<Direction> list2 = shuffleDirections(glowLichenFeatureConfig, random, direction.getOpposite());

                    for (int i = 0; i < glowLichenFeatureConfig.searchRange; ++i) {
                        mutable.setWithOffset(blockPos, direction);
                        BlockState blockState = structureWorldAccess.getBlockState(mutable);
                        if (isAirOrWater(blockState) && !blockState.is(ModBlocks.IVY.get())) {
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

    public static boolean generate(WorldGenLevel world, BlockPos pos, BlockState state, GlowLichenConfiguration config, RandomSource random, List<Direction> directions) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        Iterator<Direction> var7 = directions.iterator();

        Direction direction;
        BlockState blockState;
        do {
            if (!var7.hasNext()) {
                return false;
            }

            direction = var7.next();
            blockState = world.getBlockState(mutable.setWithOffset(pos, direction));
        } while(!blockState.is(config.canBePlacedOn));

        IvyBlock ivyBlock = ModBlocks.IVY.get();
        BlockState blockState2 = ivyBlock.getStateForPlacement(state, world, pos, direction);
        if (blockState2 == null) {
            return false;
        } else {
            world.setBlock(pos, blockState2, 3);
            world.getChunk(pos).markPosForPostprocessing(pos);
            if (random.nextFloat() < config.chanceOfSpreading) {
                ivyBlock.spreadFromFaceTowardRandomDirection(blockState2, world, pos, direction, random, true);
            }

            return true;
        }
    }

    public static List<Direction> shuffleDirections(GlowLichenConfiguration config, RandomSource random) {
        List<Direction> list = Lists.newArrayList(config.validDirections);
        Collections.shuffle(list, random);
        return list;
    }

    public static List<Direction> shuffleDirections(GlowLichenConfiguration config, RandomSource random, Direction excluded) {
        List<Direction> list = config.validDirections.stream().filter((direction) -> direction != excluded).collect(Collectors.toList());
        Collections.shuffle(list, random);
        return list;
    }

    private static boolean isAirOrWater(BlockState state) {
        return !state.isAir() && !state.is(Blocks.WATER);
    }
}