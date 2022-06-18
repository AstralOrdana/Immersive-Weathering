package com.ordana.immersive_weathering.registry.blocks;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.charred.CharredBlock;
import com.ordana.immersive_weathering.registry.blocks.rotten.RottenFenceBlock;
import net.minecraft.block.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WeatheringHelper {



    public static final Supplier<ImmutableMap<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.AZALEA, Blocks.FLOWERING_AZALEA)
            .put(Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES)
            .put(ModBlocks.AZALEA_LEAF_PILE, ModBlocks.FLOWERING_AZALEA_LEAF_PILE)
            .build());
    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.getStateWithProperties(state));
    }

    public static final Supplier<Map<Block, Block>> LEAF_PILES = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE)
            .put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE)
            .put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE)
            .put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE)
            .put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE)
            .put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE)
            .put(Blocks.MANGROVE_LEAVES, ModBlocks.MANGROVE_LEAF_PILE)
            .put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE)
            .put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE)
            .build());
    public static Optional<Block> getFallenLeafPile(BlockState state) {
        return Optional.ofNullable(LEAF_PILES.get().get(state.getBlock()));
    }

    public static final Supplier<Map<Block, DefaultParticleType>> LEAF_PARTICLES = Suppliers.memoize(() -> ImmutableMap.<Block, DefaultParticleType>builder()
            .put(Blocks.OAK_LEAVES, ModParticles.OAK_LEAF)
            .put(Blocks.DARK_OAK_LEAVES, ModParticles.DARK_OAK_LEAF)
            .put(Blocks.SPRUCE_LEAVES, ModParticles.SPRUCE_LEAF)
            .put(Blocks.BIRCH_LEAVES, ModParticles.BIRCH_LEAF)
            .put(Blocks.JUNGLE_LEAVES, ModParticles.JUNGLE_LEAF)
            .put(Blocks.ACACIA_LEAVES, ModParticles.ACACIA_LEAF)
            .put(Blocks.MANGROVE_LEAVES, ModParticles.MANGROVE_LEAF)
            .put(Blocks.AZALEA_LEAVES, ModParticles.AZALEA_LEAF)
            .put(Blocks.FLOWERING_AZALEA_LEAVES, ModParticles.AZALEA_FLOWER)
            .build());
    public static Optional<DefaultParticleType> getFallenLeafParticle(BlockState state) {
        return Optional.ofNullable(LEAF_PARTICLES.get().get(state.getBlock()));
    }

    public static final Supplier<Map<Block, DefaultParticleType>> BRANCH_LEAF_PARTICLES = Suppliers.memoize(() -> ImmutableMap.<Block, DefaultParticleType>builder()
            .put(ModBlocks.OAK_BRANCHES, ModParticles.OAK_LEAF)
            .put(ModBlocks.DARK_OAK_BRANCHES, ModParticles.DARK_OAK_LEAF)
            .put(ModBlocks.SPRUCE_BRANCHES, ModParticles.SPRUCE_LEAF)
            .put(ModBlocks.BIRCH_BRANCHES, ModParticles.BIRCH_LEAF)
            .put(ModBlocks.JUNGLE_BRANCHES, ModParticles.JUNGLE_LEAF)
            .put(ModBlocks.ACACIA_BRANCHES, ModParticles.ACACIA_LEAF)
            .put(ModBlocks.MANGROVE_BRANCHES, ModParticles.MANGROVE_LEAF)
            .build());
    public static Optional<DefaultParticleType> getBranchLeafParticle(BlockState state) {
        return Optional.ofNullable(BRANCH_LEAF_PARTICLES.get().get(state.getBlock()));
    }

    public static final Supplier<Map<Block, DefaultParticleType>> BARK_PARTICLES = Suppliers.memoize(() -> ImmutableMap.<Block, DefaultParticleType>builder()
            .put(Blocks.OAK_LOG, ModParticles.OAK_BARK)
            .put(Blocks.DARK_OAK_LOG, ModParticles.DARK_OAK_BARK)
            .put(Blocks.SPRUCE_LOG, ModParticles.SPRUCE_BARK)
            .put(Blocks.BIRCH_LOG, ModParticles.BIRCH_BARK)
            .put(Blocks.JUNGLE_LOG, ModParticles.JUNGLE_BARK)
            .put(Blocks.ACACIA_LOG, ModParticles.ACACIA_BARK)
            .put(Blocks.MANGROVE_LOG, ModParticles.MANGROVE_BARK)
            .put(Blocks.CRIMSON_STEM, ModParticles.NETHER_SCALE)
            .put(Blocks.WARPED_STEM, ModParticles.NETHER_SCALE)
            .put(Blocks.OAK_WOOD, ModParticles.OAK_BARK)
            .put(Blocks.DARK_OAK_WOOD, ModParticles.DARK_OAK_BARK)
            .put(Blocks.SPRUCE_WOOD, ModParticles.SPRUCE_BARK)
            .put(Blocks.BIRCH_WOOD, ModParticles.BIRCH_BARK)
            .put(Blocks.JUNGLE_WOOD, ModParticles.JUNGLE_BARK)
            .put(Blocks.ACACIA_WOOD, ModParticles.ACACIA_BARK)
            .put(Blocks.MANGROVE_WOOD, ModParticles.MANGROVE_BARK)
            .put(Blocks.CRIMSON_HYPHAE, ModParticles.NETHER_SCALE)
            .put(Blocks.WARPED_HYPHAE, ModParticles.NETHER_SCALE)
            .build());
    public static Optional<DefaultParticleType> getBarkParticle(BlockState state) {
        return Optional.ofNullable(BARK_PARTICLES.get().get(state.getBlock()));
    }

    public static final Supplier<Map<Block, Block>> LEAVES_FROM_BRANCHES = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(ModBlocks.OAK_BRANCHES, Blocks.OAK_LEAVES)
            .put(ModBlocks.DARK_OAK_BRANCHES, Blocks.DARK_OAK_LEAVES)
            .put(ModBlocks.SPRUCE_BRANCHES, Blocks.SPRUCE_LEAVES)
            .put(ModBlocks.BIRCH_BRANCHES, Blocks.BIRCH_LEAVES)
            .put(ModBlocks.JUNGLE_BRANCHES, Blocks.JUNGLE_LEAVES)
            .put(ModBlocks.ACACIA_BRANCHES, Blocks.ACACIA_LEAVES)
            .put(ModBlocks.MANGROVE_BRANCHES, Blocks.MANGROVE_LEAVES)
            .build());
    public static Optional<BlockState> getLeavesFromBranches(BlockState state) {
        return Optional.ofNullable(LEAVES_FROM_BRANCHES.get().get(state.getBlock()))
                .map(block -> block.getStateWithProperties(state));
    }

    public static final Supplier<Map<Block, Block>> BRANCHES_FROM_LEAVES = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.OAK_LEAVES, ModBlocks.OAK_BRANCHES)
            .put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_BRANCHES)
            .put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_BRANCHES)
            .put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_BRANCHES)
            .put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_BRANCHES)
            .put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_BRANCHES)
            .put(Blocks.MANGROVE_LEAVES, ModBlocks.MANGROVE_BRANCHES)
            .build());
    public static Optional<BlockState> getBranchesFromLeaves(BlockState state) {
        return Optional.ofNullable(BRANCHES_FROM_LEAVES.get().get(state.getBlock()))
                .map(block -> block.getStateWithProperties(state));
    }

    public static final Supplier<Map<Block, Block>> SNOWY_BLOCKS = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.STONE, ModBlocks.SNOWY_STONE)
            .put(Blocks.STONE_STAIRS, ModBlocks.SNOWY_STONE_STAIRS)
            .put(Blocks.STONE_SLAB, ModBlocks.SNOWY_STONE_SLAB)
            .put(ModBlocks.STONE_WALL, ModBlocks.SNOWY_STONE_WALL)
            .put(Blocks.COBBLESTONE, ModBlocks.SNOWY_COBBLESTONE)
            .put(Blocks.COBBLESTONE_STAIRS, ModBlocks.SNOWY_COBBLESTONE_STAIRS)
            .put(Blocks.COBBLESTONE_SLAB, ModBlocks.SNOWY_COBBLESTONE_SLAB)
            .put(Blocks.COBBLESTONE_WALL, ModBlocks.SNOWY_COBBLESTONE_WALL)
            .put(Blocks.STONE_BRICKS, ModBlocks.SNOWY_STONE_BRICKS)
            .put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.SNOWY_CHISELED_STONE_BRICKS)
            .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.SNOWY_STONE_BRICK_STAIRS)
            .put(Blocks.STONE_BRICK_SLAB, ModBlocks.SNOWY_STONE_BRICK_SLAB)
            .put(Blocks.STONE_BRICK_WALL, ModBlocks.SNOWY_STONE_BRICK_WALL)
            .build());
    public static Optional<BlockState> getSnowyBlock(BlockState state) {
        return Optional.ofNullable(SNOWY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.getStateWithProperties(state));
    }

    public static final Supplier<Map<Block, Block>> SANDY_BLOCKS = Suppliers.memoize(() -> ImmutableMap.<Block, Block>builder()
            .put(Blocks.STONE, ModBlocks.SANDY_STONE)
            .put(Blocks.STONE_STAIRS, ModBlocks.SANDY_STONE_STAIRS)
            .put(Blocks.STONE_SLAB, ModBlocks.SANDY_STONE_SLAB)
            .put(ModBlocks.STONE_WALL, ModBlocks.SANDY_STONE_WALL)
            .put(Blocks.COBBLESTONE, ModBlocks.SANDY_COBBLESTONE)
            .put(Blocks.COBBLESTONE_STAIRS, ModBlocks.SANDY_COBBLESTONE_STAIRS)
            .put(Blocks.COBBLESTONE_SLAB, ModBlocks.SANDY_COBBLESTONE_SLAB)
            .put(Blocks.COBBLESTONE_WALL, ModBlocks.SANDY_COBBLESTONE_WALL)
            .put(Blocks.STONE_BRICKS, ModBlocks.SANDY_STONE_BRICKS)
            .put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.SANDY_CHISELED_STONE_BRICKS)
            .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.SANDY_STONE_BRICK_STAIRS)
            .put(Blocks.STONE_BRICK_SLAB, ModBlocks.SANDY_STONE_BRICK_SLAB)
            .put(Blocks.STONE_BRICK_WALL, ModBlocks.SANDY_STONE_BRICK_WALL)
            .build());
    public static Optional<BlockState> getSandyBlock(BlockState state) {
        return Optional.ofNullable(SANDY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.getStateWithProperties(state));
    }


    /**
     * Grabs block positions around center pos. Order of these is random and depends on current blockpos
     *
     * @param centerPos center pos
     */
    public static List<BlockPos> grabBlocksAroundRandomly(BlockPos centerPos, int radiusX, int radiusY, int radiusZ) {
        var list = BlockPos.streamOutwards(centerPos, radiusX, radiusY, radiusZ)
                .map(BlockPos::new)
                .collect(Collectors.toList());
        //shuffling. provides way better result that iterating through it conventionally
        Collections.shuffle(list, new Random(MathHelper.hashCode(centerPos)));
        return list;
    }

    /**
     * optimized version of BlockPos.withinManhattanStream / BlockPos.expandOutwards that tries to limit world.getBlockState calls
     * Remember to call  "if (!level.isAreaLoaded(pos, radius)) return" before calling this
     *
     * @param blockPredicate type of target block
     * @param requiredAmount maximum amount of blocks that we want around this
     * @return true if blocks around that match the given predicate exceed(inclusive) the maximum size given
     */
    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radiusX, int radiusY, int radiusZ, World world, Predicate<BlockState> blockPredicate, int requiredAmount) {

        var lis = grabBlocksAroundRandomly(centerPos, radiusX, radiusY, radiusZ);

        int count = 0;
        for (BlockPos pos : lis) {
            if (blockPredicate.test(world.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }

        return false;
    }


    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radius, World world,
                                                Predicate<BlockState> blockPredicate, int requiredAmount) {
        return hasEnoughBlocksAround(centerPos, radius, radius, radius, world, blockPredicate, requiredAmount);
    }

    //same as before but just checks blocks facing this one (6 in total)
    public static boolean hasEnoughBlocksFacingMe(BlockPos centerPos, World world,
                                                  Predicate<BlockState> blockPredicate, int requiredAmount) {
        int count = 0;
        //shuffling. provides way better result that iterating through it conventionally
        List<Direction> list = new ArrayList<Direction>(List.of(Direction.values()));
        Collections.shuffle(list, new Random(MathHelper.hashCode(centerPos)));
        for (Direction dir : list) {
            BlockPos pos = centerPos.offset(dir);
            if (blockPredicate.test(world.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }
        return false;
    }

    public static boolean isLog(BlockState neighbor) {
        return neighbor.isIn(BlockTags.LOGS) && (!neighbor.contains(PillarBlock.AXIS) ||
                neighbor.get(PillarBlock.AXIS) == Direction.Axis.Y) &&
                !neighbor.getBlock().getName().getString().contains("stripped");
    }

    public static boolean isIciclePos(BlockPos pos) {
        Random posRandom = new Random(MathHelper.hashCode(pos));
        return posRandom.nextInt(12) == 0;
    }

    public static boolean isWeatherPos(BlockPos pos) {
        Random posRandom = new Random(MathHelper.hashCode(pos));
        return posRandom.nextInt(6) == 0;
    }

    public static void worldTickWeathering(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        var biome = world.getBiome(pos);

        if(ImmersiveWeathering.getConfig().fireAndIceConfig.iciclePlacement) {
            if (precipitation == Biome.Precipitation.SNOW && WeatheringHelper.isIciclePos(pos)) {
                BlockPos p = pos.down(state.isIn(BlockTags.SNOW) ? 2 : 1);
                BlockState placement = ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);
                if (world.getBlockState(p).isAir() && placement.canPlaceAt(world, p)) {
                    if (Direction.Type.HORIZONTAL.stream().anyMatch(d -> {
                        BlockPos rel = p.offset(d);
                        return world.isSkyVisible(rel) && world.getBlockState(rel).isAir();
                    })) {
                        world.setBlockState(p, placement, 3);
                    }
                }
            }
        }

        /*
        if (state.isIn(ModTags.SNOWABLE)) {
            if (world.isRaining() && world.isSkyVisible(pos) && world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, pos).getY() > pos.getY()) {
                BlockState downBlock = world.getBlockState(pos.down());
                var snowyBlock = WeatheringHelper.getSnowyBlock(state).orElse(null);
                var sandyBlock = WeatheringHelper.getSandyBlock(state).orElse(null);
                if (biome.isIn(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE)) {
                    world.setBlockState(pos, sandyBlock.getBlock().getStateWithProperties(state));
                    if (isWeatherPos(pos.down())) {
                        world.setBlockState(pos.down(), sandyBlock.getBlock().getStateWithProperties(downBlock));
                    }
                }
                if (!biome.value().doesNotSnow(pos)) {
                    world.setBlockState(pos, snowyBlock.getBlock().getStateWithProperties(state));
                    if (isWeatherPos(pos.down())) {
                        world.setBlockState(pos.down(), snowyBlock.getBlock().getStateWithProperties(downBlock));
                    }
                }
            }
        }
         */

        if (state.isIn(ModTags.SNOWABLE)) {
            if (biome.isIn(BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE)) {
                BlockPos downPos = pos.down();
                BlockState downBlock = world.getBlockState(downPos);
                var sandyBlock = WeatheringHelper.getSandyBlock(state).orElse(null);
                world.setBlockState(pos, sandyBlock.getBlock().getStateWithProperties(state));
                if (isWeatherPos(downPos) && downBlock.isIn(ModTags.SNOWABLE)) {
                    var sandyDownBlock = WeatheringHelper.getSandyBlock(downBlock).orElse(null);
                    world.setBlockState(pos.down(), sandyDownBlock.getBlock().getStateWithProperties(downBlock));
                }
            }
            if (precipitation == Biome.Precipitation.SNOW) {
                BlockPos downPos = pos.down();
                BlockState downBlock = world.getBlockState(downPos);
                var snowyBlock = WeatheringHelper.getSnowyBlock(state).orElse(null);
                world.setBlockState(pos, snowyBlock.getBlock().getStateWithProperties(state));
                if (isWeatherPos(downPos) && downBlock.isIn(ModTags.SNOWABLE)) {
                    var snowyDownBlock = WeatheringHelper.getSnowyBlock(downBlock).orElse(null);
                    world.setBlockState(pos.down(), snowyDownBlock.getBlock().getStateWithProperties(downBlock));
                }
            }
        }

        if (ImmersiveWeathering.getConfig().blockGrowthConfig.woodWeathering) {
            if (precipitation == Biome.Precipitation.RAIN && world.getBlockState(pos.up()).isAir()) {
                if (state.isIn(BlockTags.WOODEN_FENCES) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                    world.setBlockState(pos, ModBlocks.ROTTEN_FENCE.getStateWithProperties(state), 3);
                }
                else if (state.isIn(BlockTags.FENCE_GATES) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                    world.setBlockState(pos, ModBlocks.ROTTEN_FENCE_GATE.getStateWithProperties(state), 3);
                }
                else if (state.isIn(BlockTags.WOODEN_SLABS) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                    world.setBlockState(pos, ModBlocks.ROTTEN_SLAB.getStateWithProperties(state), 3);
                }
                else if (state.isIn(BlockTags.WOODEN_STAIRS) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                    world.setBlockState(pos, ModBlocks.ROTTEN_STAIRS.getStateWithProperties(state), 3);
                }
                else if (state.isIn(BlockTags.PLANKS) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                    world.setBlockState(pos, ModBlocks.ROTTEN_PLANKS.getStateWithProperties(state), 3);
                }
                else if (state.isIn(ModTags.STRIPPED_LOGS) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                    world.setBlockState(pos, ModBlocks.ROTTEN_LOG.getStateWithProperties(state), 3);
                }
            }
        }
    }

    public static void onLightningHit(BlockPos centerPos, World world, int rec) {
        BlockState vitrified = ModBlocks.VITRIFIED_SAND.getDefaultState();
        world.setBlockState(centerPos, vitrified, 3);
        if (rec >= 5) return;

        rec++;
        float decrement = 0.7f;
        double p = Math.pow(decrement, rec);
        if (rec == 0 || world.random.nextFloat() < 1 * p) {
            BlockPos downPos = centerPos.down();
            if (world.getBlockState(downPos).isIn(BlockTags.SAND)) {
                world.setBlockState(centerPos.up(), ModBlocks.FULGURITE.getDefaultState().with(FulguriteBlock.FACING, Direction.UP), 3);
                onLightningHit(downPos, world, rec);
            }
        }
        for (BlockPos target : BlockPos.iterateOutwards(centerPos, 1, 0, 1)) {
            if (world.random.nextFloat() < 0.3 * p && target != centerPos) {
                if (world.getBlockState(target).isIn(BlockTags.SAND)) {
                    onLightningHit(target, world, rec);
                }
            }
        }
    }

    public static boolean tryCharBlock(World world, BlockPos pos, BlockState state) {
        BlockState downState = world.getBlockState(pos.down());
        if (world.random.nextFloat() < 0.85f) {
            if (state.isIn(BlockTags.WOODEN_FENCES) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerWorld) world).spawnParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlockState(pos, ModBlocks.CHARRED_FENCE
                            .getStateWithProperties(state).with(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlockState(pos, ModBlocks.CHARRED_FENCE
                        .getStateWithProperties(state).with(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.isIn(BlockTags.FENCE_GATES) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerWorld) world).spawnParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlockState(pos, ModBlocks.CHARRED_FENCE_GATE
                            .getStateWithProperties(state).with(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlockState(pos, ModBlocks.CHARRED_FENCE_GATE
                        .getStateWithProperties(state).with(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.isIn(BlockTags.WOODEN_SLABS) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerWorld) world).spawnParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlockState(pos, ModBlocks.CHARRED_SLAB
                            .getStateWithProperties(state).with(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlockState(pos, ModBlocks.CHARRED_SLAB
                        .getStateWithProperties(state).with(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.isIn(BlockTags.WOODEN_STAIRS) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerWorld) world).spawnParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlockState(pos, ModBlocks.CHARRED_STAIRS
                            .getStateWithProperties(state).with(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlockState(pos, ModBlocks.CHARRED_STAIRS
                        .getStateWithProperties(state).with(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.isIn(BlockTags.PLANKS) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerWorld) world).spawnParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlockState(pos, ModBlocks.CHARRED_PLANKS
                            .getStateWithProperties(state).with(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlockState(pos, ModBlocks.CHARRED_PLANKS
                        .getStateWithProperties(state).with(CharredBlock.SMOLDERING, false), 3);
            }
            else if (state.isIn(BlockTags.LOGS_THAT_BURN) && !state.isIn(BlockTags.NON_FLAMMABLE_WOOD)) {
                ((ServerWorld) world).spawnParticles(ModParticles.SOOT, (double) pos.getX() + 0.5D,
                        (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                        0.5D, 0.5D, 0.5D, 0.0D);
                if (world.random.nextFloat() < 0.5f) {
                    return world.setBlockState(pos, ModBlocks.CHARRED_LOG
                            .getStateWithProperties(state).with(CharredBlock.SMOLDERING, true), 3);
                } else return world.setBlockState(pos, ModBlocks.CHARRED_LOG
                        .getStateWithProperties(state).with(CharredBlock.SMOLDERING, false), 3);
            }
            else if (world.random.nextFloat() < 0.5f) {
                return world.setBlockState(pos, ModBlocks.ASH_LAYER_BLOCK
                        .getStateWithProperties(state), 3);
            }
        }
        else if (world.random.nextFloat() > 0.2f) {
            return world.setBlockState(pos, ModBlocks.ASH_LAYER_BLOCK
                    .getStateWithProperties(state), 3);
        }
        else if (downState.isOf(Blocks.GRASS_BLOCK)) {
            return world.setBlockState(pos.down(), Blocks.DIRT.getDefaultState(), 3);
        }
        return false;
    }
}