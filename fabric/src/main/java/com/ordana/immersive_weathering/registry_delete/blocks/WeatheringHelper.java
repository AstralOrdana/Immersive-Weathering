package com.ordana.immersive_weathering.registry_delete.blocks;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
public class WeatheringHelper {

    private static final HashMap<TagKey, Block> CHARRED_BLOCKS = new HashMap<>();

    static {
        CHARRED_BLOCKS.put(BlockTags.LOGS_THAT_BURN, ModBlocks.CHARRED_LOG.get());
        CHARRED_BLOCKS.put(BlockTags.PLANKS, ModBlocks.CHARRED_PLANKS.get());
        CHARRED_BLOCKS.put(BlockTags.WOODEN_SLABS, ModBlocks.CHARRED_SLAB.get());
        CHARRED_BLOCKS.put(BlockTags.WOODEN_STAIRS, ModBlocks.CHARRED_STAIRS.get());
        CHARRED_BLOCKS.put(BlockTags.WOODEN_FENCES, ModBlocks.CHARRED_FENCE.get());
        CHARRED_BLOCKS.put(BlockTags.FENCE_GATES, ModBlocks.CHARRED_FENCE_GATE.get());
    }

    public static final Supplier<ImmutableMap<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.AZALEA, Blocks.FLOWERING_AZALEA)
            .put(Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES)
            .put(ModBlocks.AZALEA_LEAF_PILE, ModBlocks.FLOWERING_AZALEA_LEAF_PILE)
            .build());

    public static final Supplier<Map<Block, Block>> LEAF_PILES = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE)
            .put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE)
            .put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE)
            .put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE)
            .put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE)
            .put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE)
            .put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE)
            .put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE)
            .build());

    public static final Supplier<Map<Block, SimpleParticleType>> LEAF_PARTICLES = Suppliers.memoize(() -> ImmutableMap.<Block, SimpleParticleType>builder()
            .put(Blocks.OAK_LEAVES, ModParticles.OAK_LEAF)
            .put(Blocks.DARK_OAK_LEAVES, ModParticles.DARK_OAK_LEAF)
            .put(Blocks.SPRUCE_LEAVES, ModParticles.SPRUCE_LEAF)
            .put(Blocks.BIRCH_LEAVES, ModParticles.BIRCH_LEAF)
            .put(Blocks.JUNGLE_LEAVES, ModParticles.JUNGLE_LEAF)
            .put(Blocks.ACACIA_LEAVES, ModParticles.ACACIA_LEAF)
            .put(Blocks.AZALEA_LEAVES, ModParticles.AZALEA_LEAF)
            .put(Blocks.FLOWERING_AZALEA_LEAVES, ModParticles.AZALEA_FLOWER)
            .build());

    public static final Supplier<Map<Block, SimpleParticleType>> BARK_PARTICLES = Suppliers.memoize(() -> ImmutableMap.<Block, SimpleParticleType>builder()
            .put(Blocks.OAK_LOG, ModParticles.OAK_BARK)
            .put(Blocks.DARK_OAK_LOG, ModParticles.DARK_OAK_BARK)
            .put(Blocks.SPRUCE_LOG, ModParticles.SPRUCE_BARK)
            .put(Blocks.BIRCH_LOG, ModParticles.BIRCH_BARK)
            .put(Blocks.JUNGLE_LOG, ModParticles.JUNGLE_BARK)
            .put(Blocks.ACACIA_LOG, ModParticles.ACACIA_BARK)
            .put(Blocks.CRIMSON_STEM, ModParticles.NETHER_SCALE)
            .put(Blocks.WARPED_STEM, ModParticles.NETHER_SCALE)
            .put(Blocks.OAK_WOOD, ModParticles.OAK_BARK)
            .put(Blocks.DARK_OAK_WOOD, ModParticles.DARK_OAK_BARK)
            .put(Blocks.SPRUCE_WOOD, ModParticles.SPRUCE_BARK)
            .put(Blocks.BIRCH_WOOD, ModParticles.BIRCH_BARK)
            .put(Blocks.JUNGLE_WOOD, ModParticles.JUNGLE_BARK)
            .put(Blocks.ACACIA_WOOD, ModParticles.ACACIA_BARK)
            .put(Blocks.CRIMSON_HYPHAE, ModParticles.NETHER_SCALE)
            .put(Blocks.WARPED_HYPHAE, ModParticles.NETHER_SCALE)
            .build());

    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.ofNullable(LEAF_PILES.get().get(state.getBlock()));
    }

    public static Optional<SimpleParticleType> getFallenLeafParticle(BlockState state) {
        return Optional.ofNullable(LEAF_PARTICLES.get().get(state.getBlock()));
    }

    public static Optional<SimpleParticleType> getBarkParticle(BlockState state) {
        return Optional.ofNullable(BARK_PARTICLES.get().get(state.getBlock()));
    }


    public static List<BlockPos> grabBlocksAroundRandomly(BlockPos centerPos, int radiusX, int radiusY, int radiusZ) {
        var list = BlockPos.withinManhattanStream(centerPos, radiusX, radiusY, radiusZ)
                .map(BlockPos::new)
                .collect(Collectors.toList());
        //shuffling. provides way better result that iterating through it conventionally
        Collections.shuffle(list, new Random(Mth.getSeed(centerPos)));
        return list;
    }


    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radiusX, int radiusY, int radiusZ, Level world, Predicate<BlockState> blockPredicate, int requiredAmount) {

        var lis = grabBlocksAroundRandomly(centerPos, radiusX, radiusY, radiusZ);

        int count = 0;
        for (BlockPos pos : lis) {
            if (blockPredicate.test(world.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }

        return false;
    }


    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radius, Level world,
                                                Predicate<BlockState> blockPredicate, int requiredAmount) {
        return hasEnoughBlocksAround(centerPos, radius, radius, radius, world, blockPredicate, requiredAmount);
    }

    //same as before but just checks blocks facing this one (6 in total)
    public static boolean hasEnoughBlocksFacingMe(BlockPos centerPos, Level world,
                                                  Predicate<BlockState> blockPredicate, int requiredAmount) {
        int count = 0;
        //shuffling. provides way better result that iterating through it conventionally
        List<Direction> list = new ArrayList<Direction>(List.of(Direction.values()));
        Collections.shuffle(list, new Random(Mth.getSeed(centerPos)));
        for (Direction dir : list) {
            BlockPos pos = centerPos.relative(dir);
            if (blockPredicate.test(world.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }
        return false;
    }

    public static boolean isLog(BlockState neighbor) {
        return neighbor.is(BlockTags.LOGS) && (!neighbor.hasProperty(RotatedPillarBlock.AXIS) ||
                neighbor.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y) &&
                !neighbor.getBlock().getName().getString().contains("stripped");
    }

    public static boolean isIciclePos(BlockPos pos) {
        Random posRandom = new Random(Mth.getSeed(pos));
        return posRandom.nextInt(12) == 0;
    }

    public static void tryPlacingIcicle(BlockState state, Level world, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW && WeatheringHelper.isIciclePos(pos)) {
            BlockPos p = pos.below(state.is(BlockTags.SNOW) ? 2 : 1);
            BlockState placement = ModBlocks.ICICLE.defaultBlockState().setValue(IcicleBlock.TIP_DIRECTION, Direction.DOWN);
            if (world.getBlockState(p).isAir() && placement.canSurvive(world, p)) {
                if (Direction.Plane.HORIZONTAL.stream().anyMatch(d -> {
                    BlockPos rel = p.relative(d);
                    return world.canSeeSky(rel) && world.getBlockState(rel).isAir();
                })) {
                    world.setBlock(p, placement, 3);
                }
            }
        }
    }

    public static void onLightningHit(BlockPos centerPos, Level world, int rec) {
        BlockState vitrified = ModBlocks.VITRIFIED_SAND.defaultBlockState();
        world.setBlock(centerPos, vitrified, 3);
        if (rec >= 5) return;

        rec++;
        float decrement = 0.7f;
        double p = Math.pow(decrement, rec);
        if (rec == 0 || world.random.nextFloat() < 1 * p) {
            BlockPos downPos = centerPos.below();
            if (world.getBlockState(downPos).is(BlockTags.SAND)) {
                world.setBlock(centerPos.above(), ModBlocks.FULGURITE.defaultBlockState().setValue(FulguriteBlock.FACING, Direction.UP), 3);
                onLightningHit(downPos, world, rec);
            }
        }
        for (BlockPos target : BlockPos.withinManhattan(centerPos, 1, 0, 1)) {
            if (world.random.nextFloat() < 0.3 * p && target != centerPos) {
                if (world.getBlockState(target).is(BlockTags.SAND)) {
                    onLightningHit(target, world, rec);
                }
            }
        }
    }

    public static boolean tryCharBlock(Level world, BlockPos pos, BlockState state) {
        BlockState downState = world.getBlockState(pos.below());
        if (world.random.nextFloat() < 0.85f) {
            if (state.is(BlockTags.WOODEN_FENCES) && !state.is(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerLevel) world).sendParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlock(pos, ModBlocks.CHARRED_FENCE
                            .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlock(pos, ModBlocks.CHARRED_FENCE
                        .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.is(BlockTags.FENCE_GATES) && !state.is(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerLevel) world).sendParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlock(pos, ModBlocks.CHARRED_FENCE_GATE
                            .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlock(pos, ModBlocks.CHARRED_FENCE_GATE
                        .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.is(BlockTags.WOODEN_SLABS) && !state.is(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerLevel) world).sendParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlock(pos, ModBlocks.CHARRED_SLAB
                            .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlock(pos, ModBlocks.CHARRED_SLAB
                        .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.is(BlockTags.WOODEN_STAIRS) && !state.is(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerLevel) world).sendParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlock(pos, ModBlocks.CHARRED_STAIRS
                            .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlock(pos, ModBlocks.CHARRED_STAIRS
                        .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.is(BlockTags.PLANKS) && !state.is(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerLevel) world).sendParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlock(pos, ModBlocks.CHARRED_PLANKS
                            .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlock(pos, ModBlocks.CHARRED_PLANKS
                        .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.is(BlockTags.LOGS_THAT_BURN) && !state.is(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerLevel) world).sendParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlock(pos, ModBlocks.CHARRED_LOG
                            .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlock(pos, ModBlocks.CHARRED_LOG
                        .withPropertiesOf(state).setValue(CharredBlock.SMOLDERING, false), 3);
            }
            else if (world.random.nextFloat() < 0.5f) {
                return world.setBlock(pos, ModBlocks.ASH_LAYER_BLOCK
                        .withPropertiesOf(state), 3);
            }
        }
        else if (world.random.nextFloat() > 0.2f) {
            return world.setBlock(pos, ModBlocks.ASH_LAYER_BLOCK
                    .withPropertiesOf(state), 3);
        }
        else if (downState.is(Blocks.GRASS_BLOCK)) {
            return world.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 3);
        }
        return false;
    }
}

 */