package com.ordana.immersive_weathering.common.features;

import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.common.ModBlocks;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class IcicleClusterFeature extends Feature<IcicleClusterFeatureConfig> {
    public IcicleClusterFeature(Codec<IcicleClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<IcicleClusterFeatureConfig> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        IcicleClusterFeatureConfig icicleClusterFeatureConfig = context.config();
        Random random = context.random();
        if (!IcicleHelper.canGenerate(structureWorldAccess, blockPos)) {
            return false;
        } else {
            int i = icicleClusterFeatureConfig.height.sample(random);
            float f = icicleClusterFeatureConfig.wetness.sample(random);
            float g = icicleClusterFeatureConfig.density.sample(random);
            int j = icicleClusterFeatureConfig.radius.sample(random);
            int k = icicleClusterFeatureConfig.radius.sample(random);

            for(int l = -j; l <= j; ++l) {
                for(int m = -k; m <= k; ++m) {
                    double d = this.icicleChance(j, k, l, m, icicleClusterFeatureConfig);
                    BlockPos blockPos2 = blockPos.offset(l, 0, m);
                    this.generate(structureWorldAccess, random, blockPos2, l, m, f, d, i, g, icicleClusterFeatureConfig);
                }
            }

            return true;
        }
    }

    private void generate(WorldGenLevel world, Random random, BlockPos pos, int localX, int localZ, float wetness, double icicleChance, int height, float density, IcicleClusterFeatureConfig config) {
        Optional<Column> optional = Column.scan(world, pos, config.floorToCeilingSearchRange, IcicleHelper::canGenerate, IcicleHelper::canReplaceOrLava);
        if (optional.isPresent()) {
            OptionalInt optionalInt = optional.get().getCeiling();
            OptionalInt optionalInt2 = optional.get().getFloor();
            if (optionalInt.isPresent() || optionalInt2.isPresent()) {
                boolean bl = random.nextFloat() < wetness;
                Column caveSurface;
                if (bl && optionalInt2.isPresent() && this.canWaterSpawn(world, pos.atY(optionalInt2.getAsInt()))) {
                    int i = optionalInt2.getAsInt();
                    caveSurface = optional.get().withFloor(OptionalInt.of(i - 1));
                    world.setBlock(pos.atY(i), Blocks.WATER.defaultBlockState(), 2);
                } else {
                    caveSurface = optional.get();
                }

                OptionalInt i = caveSurface.getFloor();
                boolean bl2 = random.nextDouble() < icicleChance;
                int l;
                int j;
                if (optionalInt.isPresent() && bl2 && this.isLava(world, pos.atY(optionalInt.getAsInt()))) {
                    j = config.icicleBlockLayerThickness.sample(random);
                    this.placeIceBlocks(world, pos.atY(optionalInt.getAsInt()), j, Direction.UP);
                    int k;
                    if (i.isPresent()) {
                        k = Math.min(height, optionalInt.getAsInt() - i.getAsInt());
                    } else {
                        k = height;
                    }

                    l = this.getHeight(random, localX, localZ, density, k, config);
                } else {
                    l = 0;
                }

                boolean k = random.nextDouble() < icicleChance;
                int m;
                if (i.isPresent() && k && this.isLava(world, pos.atY(i.getAsInt()))) {
                    m = config.icicleBlockLayerThickness.sample(random);
                    this.placeIceBlocks(world, pos.atY(i.getAsInt()), m, Direction.DOWN);
                    if (optionalInt.isPresent()) {
                        j = Math.max(0, l + Mth.randomBetweenInclusive(random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
                    } else {
                        j = this.getHeight(random, localX, localZ, density, height, config);
                    }
                } else {
                    j = 0;
                }

                int t;
                if (optionalInt.isPresent() && i.isPresent() && optionalInt.getAsInt() - l <= i.getAsInt() + j) {
                    int n = i.getAsInt();
                    int o = optionalInt.getAsInt();
                    int p = Math.max(o - l, n + 1);
                    int q = Math.min(n + j, o - 1);
                    int r = Mth.randomBetweenInclusive(random, p, q + 1);
                    int s = r - 1;
                    m = o - r;
                    t = s - n;
                } else {
                    m = l;
                    t = j;
                }

                boolean n = random.nextBoolean() && m > 0 && t > 0 && caveSurface.getHeight().isPresent() && m + t == caveSurface.getHeight().getAsInt();
                if (optionalInt.isPresent()) {
                    IcicleHelper.generateIcicle(world, pos.atY(optionalInt.getAsInt() - 1), Direction.DOWN, m, n);
                }

                if (i.isPresent()) {
                    IcicleHelper.generateIcicle(world, pos.atY(i.getAsInt() + 1), Direction.UP, t, n);
                }

            }
        }
    }

    private boolean isLava(LevelReader world, BlockPos pos) {
        return !world.getBlockState(pos).is(Blocks.LAVA);
    }

    private int getHeight(Random random, int localX, int localZ, float density, int height, IcicleClusterFeatureConfig config) {
        if (random.nextFloat() > density) {
            return 0;
        } else {
            int i = Math.abs(localX) + Math.abs(localZ);
            float f = (float)Mth.clampedMap(i, 0.0D, config.maxDistanceFromCenterAffectingHeightBias, (double)height / 2.0D, 0.0D);
            return (int)clampedGaussian(random, (float)height, f, (float)config.heightDeviation);
        }
    }

    private boolean canWaterSpawn(WorldGenLevel world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.is(Blocks.WATER) && !blockState.is(Blocks.ICE) && !blockState.is(ModBlocks.ICICLE.get())) {
            Iterator<Direction> var4 = Direction.Plane.HORIZONTAL.iterator();

            Direction direction;
            do {
                if (!var4.hasNext()) {
                    return this.isStoneOrWater(world, pos.below());
                }

                direction = var4.next();
            } while(this.isStoneOrWater(world, pos.relative(direction)));

        }
        return false;
    }

    private boolean isStoneOrWater(LevelAccessor world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.is(BlockTags.BASE_STONE_OVERWORLD) || blockState.getFluidState().is(FluidTags.WATER);
    }

    private void placeIceBlocks(WorldGenLevel world, BlockPos pos, int height, Direction direction) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

        for(int i = 0; i < height; ++i) {
            if (!IcicleHelper.generateIceBlock(world, mutable)) {
                return;
            }
            mutable.move(direction);
        }
    }

    private double icicleChance(int radiusX, int radiusZ, int localX, int localZ, IcicleClusterFeatureConfig config) {
        int i = radiusX - Math.abs(localX);
        int j = radiusZ - Math.abs(localZ);
        int k = Math.min(i, j);
        return Mth.clampedMap((float)k, 0.0F, (float)config.maxDistanceFromCenterAffectingChanceOfIcicleColumn, config.chanceOfIcicleColumnAtMaxDistanceFromCenter, 1.0F);
    }

    private static float clampedGaussian(Random random, float max, float mean, float deviation) {
        return ClampedNormalFloat.sample(random, mean, deviation, (float) 0.0, max);
    }
}
