package com.ordana.immersive_weathering.registry.features;

import com.mojang.serialization.Codec;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.floatprovider.ClampedNormalFloatProvider;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;

public class IcicleClusterFeature extends Feature<IcicleClusterFeatureConfig> {
    public IcicleClusterFeature(Codec<IcicleClusterFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<IcicleClusterFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        IcicleClusterFeatureConfig icicleClusterFeatureConfig = context.getConfig();
        net.minecraft.util.math.random.Random random = context.getRandom();
        if (!IcicleHelper.canGenerate(structureWorldAccess, blockPos)) {
            return false;
        } else {
            int i = icicleClusterFeatureConfig.height.get(random);
            float f = icicleClusterFeatureConfig.wetness.get(random);
            float g = icicleClusterFeatureConfig.density.get(random);
            int j = icicleClusterFeatureConfig.radius.get(random);
            int k = icicleClusterFeatureConfig.radius.get(random);

            for(int l = -j; l <= j; ++l) {
                for(int m = -k; m <= k; ++m) {
                    double d = this.icicleChance(j, k, l, m, icicleClusterFeatureConfig);
                    BlockPos blockPos2 = blockPos.add(l, 0, m);
                    this.generate(structureWorldAccess, random, blockPos2, l, m, f, d, i, g, icicleClusterFeatureConfig);
                }
            }

            return true;
        }
    }

    private void generate(StructureWorldAccess world, net.minecraft.util.math.random.Random random, BlockPos pos, int localX, int localZ, float wetness, double icicleChance, int height, float density, IcicleClusterFeatureConfig config) {
        Optional<CaveSurface> optional = CaveSurface.create(world, pos, config.floorToCeilingSearchRange, IcicleHelper::canGenerate, IcicleHelper::canReplaceOrLava);
        if (optional.isPresent()) {
            OptionalInt optionalInt = optional.get().getCeilingHeight();
            OptionalInt optionalInt2 = optional.get().getFloorHeight();
            if (optionalInt.isPresent() || optionalInt2.isPresent()) {
                boolean bl = random.nextFloat() < wetness;
                CaveSurface caveSurface;
                if (bl && optionalInt2.isPresent() && this.canWaterSpawn(world, pos.withY(optionalInt2.getAsInt()))) {
                    int i = optionalInt2.getAsInt();
                    caveSurface = optional.get().withFloor(OptionalInt.of(i - 1));
                    world.setBlockState(pos.withY(i), Blocks.WATER.getDefaultState(), 2);
                } else {
                    caveSurface = optional.get();
                }

                OptionalInt i = caveSurface.getFloorHeight();
                boolean bl2 = random.nextDouble() < icicleChance;
                int l;
                int j;
                if (optionalInt.isPresent() && bl2 && this.isLava(world, pos.withY(optionalInt.getAsInt()))) {
                    j = config.icicleBlockLayerThickness.get((net.minecraft.util.math.random.Random) random);
                    this.placeIceBlocks(world, pos.withY(optionalInt.getAsInt()), j, Direction.UP);
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
                if (i.isPresent() && k && this.isLava(world, pos.withY(i.getAsInt()))) {
                    m = config.icicleBlockLayerThickness.get((net.minecraft.util.math.random.Random) random);
                    this.placeIceBlocks(world, pos.withY(i.getAsInt()), m, Direction.DOWN);
                    if (optionalInt.isPresent()) {
                        j = Math.max(0, l + MathHelper.nextBetween((net.minecraft.util.math.random.Random) random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
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
                    int r = MathHelper.nextBetween((net.minecraft.util.math.random.Random) random, p, q + 1);
                    int s = r - 1;
                    m = o - r;
                    t = s - n;
                } else {
                    m = l;
                    t = j;
                }

                boolean n = random.nextBoolean() && m > 0 && t > 0 && caveSurface.getOptionalHeight().isPresent() && m + t == caveSurface.getOptionalHeight().getAsInt();
                if (optionalInt.isPresent()) {
                    IcicleHelper.generateIcicle(world, pos.withY(optionalInt.getAsInt() - 1), Direction.DOWN, m, n);
                }

                if (i.isPresent()) {
                    IcicleHelper.generateIcicle(world, pos.withY(i.getAsInt() + 1), Direction.UP, t, n);
                }

            }
        }
    }

    private boolean isLava(WorldView world, BlockPos pos) {
        return !world.getBlockState(pos).isOf(Blocks.LAVA);
    }

    private int getHeight(net.minecraft.util.math.random.Random random, int localX, int localZ, float density, int height, IcicleClusterFeatureConfig config) {
        if (random.nextFloat() > density) {
            return 0;
        } else {
            int i = Math.abs(localX) + Math.abs(localZ);
            float f = (float)MathHelper.clampedLerpFromProgress(i, 0.0D, config.maxDistanceFromCenterAffectingHeightBias, (double)height / 2.0D, 0.0D);
            return (int)clampedGaussian(random, (float)height, f, (float)config.heightDeviation);
        }
    }

    private boolean canWaterSpawn(StructureWorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.isOf(Blocks.WATER) && !blockState.isOf(Blocks.ICE) && !blockState.isOf(ModBlocks.ICICLE)) {
            Iterator<Direction> var4 = Direction.Type.HORIZONTAL.iterator();

            Direction direction;
            do {
                if (!var4.hasNext()) {
                    return this.isStoneOrWater(world, pos.down());
                }

                direction = var4.next();
            } while(this.isStoneOrWater(world, pos.offset(direction)));

        }
        return false;
    }

    private boolean isStoneOrWater(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isIn(BlockTags.BASE_STONE_OVERWORLD) || blockState.getFluidState().isIn(FluidTags.WATER);
    }

    private void placeIceBlocks(StructureWorldAccess world, BlockPos pos, int height, Direction direction) {
        BlockPos.Mutable mutable = pos.mutableCopy();

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
        return MathHelper.clampedLerpFromProgress((float)k, 0.0F, (float)config.maxDistanceFromCenterAffectingChanceOfIcicleColumn, config.chanceOfIcicleColumnAtMaxDistanceFromCenter, 1.0F);
    }

    private static float clampedGaussian(net.minecraft.util.math.random.Random random, float max, float mean, float deviation) {
        return ClampedNormalFloatProvider.get(random, mean, deviation, (float) 0.0, max);
    }
}
