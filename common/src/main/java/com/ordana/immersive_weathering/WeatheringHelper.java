package com.ordana.immersive_weathering;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.ordana.immersive_weathering.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.mixins.accessors.BiomeAccessor;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.mehvahdjukaar.moonlight.api.set.leaves.LeavesTypeRegistry;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WeatheringHelper {

    public static void addOptional(ImmutableBiMap.Builder<Block, Block> map,
                                   String moddedId, String moddedId2) {
        var o1 = Registry.BLOCK.getOptional(new ResourceLocation(moddedId));
        var o2 = Registry.BLOCK.getOptional(new ResourceLocation(moddedId2));
        if (o1.isPresent() && o2.isPresent()) {
            map.put(o1.get(), o2.get());
        }
    }

    public static final Supplier<BiMap<Block, Block>> FLOWERY_BLOCKS = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA)
                .put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES)
                .put(ModBlocks.LEAF_PILES.get(LeavesTypeRegistry.getValue(new ResourceLocation("flowering_azalea"))),
                        ModBlocks.LEAF_PILES.get(LeavesTypeRegistry.getValue(new ResourceLocation("azalea"))));
        addOptional(builder, "quark:flowering_azalea_hedge", "quark:azalea_hedge");
        addOptional(builder, "quark:flowering_azalea_leaf_carpet", "quark:azalea_leaf_carpet");
        return builder.build();
    });


    public static Optional<BlockState> getAzaleaGrowth(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().inverse().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static Optional<BlockState> getAzaleaSheared(BlockState state) {
        return Optional.ofNullable(FLOWERY_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }


    public static final Supplier<Map<Block, LeafPileBlock>> LEAVES_TO_PILES = Suppliers.memoize(() -> {
                var b = ImmutableMap.<Block, LeafPileBlock>builder();
                ModBlocks.LEAF_PILES.forEach((key, value) -> b.put(key.leaves, value));
                return b.build();
            }
    );

    public static final Supplier<Map<Block, SimpleParticleType>> LEAVES_TO_PARTICLE = Suppliers.memoize(() -> {
                var b = ImmutableMap.<Block, SimpleParticleType>builder();

                ModParticles.FALLING_LEAVES.forEach((key, value) -> b.put(key.leaves, value));
                return b.build();
            }
    );

    public static final Supplier<Map<Block, Block>> UNWAXED_BLOCKS = Suppliers.memoize(() ->
            ImmutableMap.<Block, Block>builder()

                    .put(ModBlocks.WAXED_CUT_IRON.get(), ModBlocks.CUT_IRON.get())
                    .put(ModBlocks.WAXED_EXPOSED_CUT_IRON.get(), ModBlocks.EXPOSED_CUT_IRON.get())
                    .put(ModBlocks.WAXED_WEATHERED_CUT_IRON.get(), ModBlocks.WEATHERED_CUT_IRON.get())
                    .put(ModBlocks.WAXED_RUSTED_CUT_IRON.get(), ModBlocks.RUSTED_CUT_IRON.get())
                    .put(ModBlocks.WAXED_CUT_IRON_STAIRS.get(), ModBlocks.CUT_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_EXPOSED_CUT_IRON_STAIRS.get(), ModBlocks.EXPOSED_CUT_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_WEATHERED_CUT_IRON_STAIRS.get(), ModBlocks.WEATHERED_CUT_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_RUSTED_CUT_IRON_STAIRS.get(), ModBlocks.RUSTED_CUT_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_CUT_IRON_SLAB.get(), ModBlocks.CUT_IRON_SLAB.get())
                    .put(ModBlocks.WAXED_EXPOSED_CUT_IRON_SLAB.get(), ModBlocks.EXPOSED_CUT_IRON_SLAB.get())
                    .put(ModBlocks.WAXED_WEATHERED_CUT_IRON_SLAB.get(), ModBlocks.WEATHERED_CUT_IRON_SLAB.get())
                    .put(ModBlocks.WAXED_RUSTED_CUT_IRON_SLAB.get(), ModBlocks.RUSTED_CUT_IRON_SLAB.get())

                    .put(ModBlocks.WAXED_PLATE_IRON.get(), ModBlocks.PLATE_IRON.get())
                    .put(ModBlocks.WAXED_EXPOSED_PLATE_IRON.get(), ModBlocks.EXPOSED_PLATE_IRON.get())
                    .put(ModBlocks.WAXED_WEATHERED_PLATE_IRON.get(), ModBlocks.WEATHERED_PLATE_IRON.get())
                    .put(ModBlocks.WAXED_RUSTED_PLATE_IRON.get(), ModBlocks.RUSTED_PLATE_IRON.get())
                    .put(ModBlocks.WAXED_PLATE_IRON_STAIRS.get(), ModBlocks.PLATE_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_EXPOSED_PLATE_IRON_STAIRS.get(), ModBlocks.EXPOSED_PLATE_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_WEATHERED_PLATE_IRON_STAIRS.get(), ModBlocks.WEATHERED_PLATE_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_RUSTED_PLATE_IRON_STAIRS.get(), ModBlocks.RUSTED_PLATE_IRON_STAIRS.get())
                    .put(ModBlocks.WAXED_PLATE_IRON_SLAB.get(), ModBlocks.PLATE_IRON_SLAB.get())
                    .put(ModBlocks.WAXED_EXPOSED_PLATE_IRON_SLAB.get(), ModBlocks.EXPOSED_PLATE_IRON_SLAB.get())
                    .put(ModBlocks.WAXED_WEATHERED_PLATE_IRON_SLAB.get(), ModBlocks.WEATHERED_PLATE_IRON_SLAB.get())
                    .put(ModBlocks.WAXED_RUSTED_PLATE_IRON_SLAB.get(), ModBlocks.RUSTED_PLATE_IRON_SLAB.get())

                    .put(ModBlocks.WAXED_IRON_DOOR.get(), Blocks.IRON_DOOR)
                    .put(ModBlocks.WAXED_EXPOSED_IRON_DOOR.get(), ModBlocks.EXPOSED_IRON_DOOR.get())
                    .put(ModBlocks.WAXED_WEATHERED_IRON_DOOR.get(), ModBlocks.WEATHERED_IRON_DOOR.get())
                    .put(ModBlocks.WAXED_RUSTED_IRON_DOOR.get(), ModBlocks.RUSTED_IRON_DOOR.get())
                    .put(ModBlocks.WAXED_IRON_TRAPDOOR.get(), Blocks.IRON_TRAPDOOR)
                    .put(ModBlocks.WAXED_EXPOSED_IRON_TRAPDOOR.get(), ModBlocks.EXPOSED_IRON_TRAPDOOR.get())
                    .put(ModBlocks.WAXED_WEATHERED_IRON_TRAPDOOR.get(), ModBlocks.WEATHERED_IRON_TRAPDOOR.get())
                    .put(ModBlocks.WAXED_RUSTED_IRON_TRAPDOOR.get(), ModBlocks.RUSTED_IRON_TRAPDOOR.get())
                    .put(ModBlocks.WAXED_IRON_BARS.get(), Blocks.IRON_BARS)
                    .put(ModBlocks.WAXED_EXPOSED_IRON_BARS.get(), ModBlocks.EXPOSED_IRON_BARS.get())
                    .put(ModBlocks.WAXED_WEATHERED_IRON_BARS.get(), ModBlocks.WEATHERED_IRON_BARS.get())
                    .put(ModBlocks.WAXED_RUSTED_IRON_BARS.get(), ModBlocks.RUSTED_IRON_BARS.get())

                    .put(Blocks.WAXED_COPPER_BLOCK, Blocks.COPPER_BLOCK)
                    .put(Blocks.WAXED_EXPOSED_COPPER, Blocks.EXPOSED_COPPER)
                    .put(Blocks.WAXED_WEATHERED_COPPER, Blocks.WEATHERED_COPPER)
                    .put(Blocks.WAXED_OXIDIZED_COPPER, Blocks.OXIDIZED_COPPER)
                    .put(Blocks.WAXED_CUT_COPPER, Blocks.CUT_COPPER)
                    .put(Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.EXPOSED_CUT_COPPER)
                    .put(Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER)
                    .put(Blocks.WAXED_OXIDIZED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER)
                    .put(Blocks.WAXED_CUT_COPPER_SLAB, Blocks.CUT_COPPER_SLAB)
                    .put(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB)
                    .put(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB)
                    .put(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB)
                    .put(Blocks.WAXED_CUT_COPPER_STAIRS, Blocks.CUT_COPPER_STAIRS)
                    .put(Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS)
                    .put(Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS)
                    .put(Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS)
                    .build());

    public static Optional<BlockState> getUnwaxedBlock(BlockState state) {
        return Optional.ofNullable(UNWAXED_BLOCKS.get().get(state.getBlock()))
                .map(block -> block.withPropertiesOf(state));
    }

    public static final Supplier<Map<Block, ParticleOptions>> LOG_TO_PARTICLES = Suppliers.memoize(() ->
            ImmutableMap.<Block, ParticleOptions>builder()
                    .put(Blocks.OAK_LOG, ModParticles.OAK_BARK.get())
                    .put(Blocks.DARK_OAK_LOG, ModParticles.DARK_OAK_BARK.get())
                    .put(Blocks.SPRUCE_LOG, ModParticles.SPRUCE_BARK.get())
                    .put(Blocks.BIRCH_LOG, ModParticles.BIRCH_BARK.get())
                    .put(Blocks.JUNGLE_LOG, ModParticles.JUNGLE_BARK.get())
                    .put(Blocks.ACACIA_LOG, ModParticles.ACACIA_BARK.get())
                    .put(Blocks.MANGROVE_LOG, ModParticles.MANGROVE_BARK.get())
                    .put(Blocks.CRIMSON_STEM, ModParticles.NETHER_SCALE.get())
                    .put(Blocks.WARPED_STEM, ModParticles.NETHER_SCALE.get())

                    .put(Blocks.OAK_WOOD, ModParticles.OAK_BARK.get())
                    .put(Blocks.DARK_OAK_WOOD, ModParticles.DARK_OAK_BARK.get())
                    .put(Blocks.SPRUCE_WOOD, ModParticles.SPRUCE_BARK.get())
                    .put(Blocks.BIRCH_WOOD, ModParticles.BIRCH_BARK.get())
                    .put(Blocks.JUNGLE_WOOD, ModParticles.JUNGLE_BARK.get())
                    .put(Blocks.ACACIA_WOOD, ModParticles.ACACIA_BARK.get())
                    .put(Blocks.MANGROVE_WOOD, ModParticles.MANGROVE_BARK.get())
                    .put(Blocks.CRIMSON_HYPHAE, ModParticles.NETHER_SCALE.get())
                    .put(Blocks.WARPED_HYPHAE, ModParticles.NETHER_SCALE.get())
                    .build());

    public static ParticleOptions getBarkParticle(BlockState state) {
        return LOG_TO_PARTICLES.get().getOrDefault(state.getBlock(), new BlockParticleOption(ParticleTypes.BLOCK, state));
    }


    public static Optional<Block> getFallenLeafPile(BlockState state) {
        Block b = state.getBlock();
        if (CommonConfigs.LEAF_PILES_BLACKLIST.get().contains(Registry.BLOCK.getKey(b).toString()))
            return Optional.empty();
        return Optional.ofNullable(LEAVES_TO_PILES.get().get(b));
    }

    public static Optional<SimpleParticleType> getFallenLeafParticle(BlockState state) {
        Block b = state.getBlock();
        return Optional.ofNullable(LEAVES_TO_PARTICLE.get().get(b));
    }


    @Nullable
    public static Item getBarkToStrip(BlockState normalLog) {
        WoodType woodType = BlockSetAPI.getBlockTypeOf(normalLog.getBlock(), WoodType.class);
        if (woodType != null) {
            boolean log = false;
            //woodType.getChildKey(normalLog)=="log" //TODO: add
            if (woodType.getChild("log") == normalLog.getBlock()) {
                log = true;
            } else if (woodType.getChild("wood") == normalLog.getBlock()) {
                log = true;
            }
            if(log) {
                return woodType.getItemOfThis("immersive_weathering:bark");
            }
        }
        return null;
    }

    public static Optional<Pair<Item, Block>> getBarkForStrippedLog(BlockState stripped) {
        WoodType woodType = BlockSetAPI.getBlockTypeOf(stripped.getBlock(), WoodType.class);
        if (woodType != null) {
            Object log = null;
            if (woodType.getChild("stripped_log") == stripped.getBlock()) {
                log = woodType.getChild("log");
            } else if (woodType.getChild("stripped_wood") == stripped.getBlock()) {
                log = woodType.getChild("wood");
            }
            if (log instanceof Block unStripped) {
                String s = CommonConfigs.GENERIC_BARK.get();
                if (!s.isEmpty()) {
                    ResourceLocation res = new ResourceLocation(s);
                    var bark = Registry.ITEM.getOptional(res);
                    if (bark.isPresent()) {
                        return Optional.of(Pair.of(bark.get(), unStripped));
                    }
                } else {
                    Item bark = woodType.getItemOfThis("immersive_weathering:bark");
                    if (bark != null) return Optional.of(Pair.of(bark, unStripped));
                }
            }
        }
        return Optional.empty();
    }


    /**
     * Grabs block positions around center pos. Order of these is random and depends on current blockpos
     *
     * @param centerPos center pos
     */
    public static List<BlockPos> grabBlocksAroundRandomly(BlockPos centerPos, int radiusX, int radiusY, int radiusZ) {
        var list = BlockPos.withinManhattanStream(centerPos, radiusX, radiusY, radiusZ)
                .map(BlockPos::new)
                .collect(Collectors.toList());
        //shuffling. provides way better result that iterating through it conventionally
        Collections.shuffle(list, new Random(Mth.getSeed(centerPos)));
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
    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radiusX, int radiusY, int radiusZ, Level level,
                                                Predicate<BlockState> blockPredicate, int requiredAmount) {

        var lis = grabBlocksAroundRandomly(centerPos, radiusX, radiusY, radiusZ);

        int count = 0;
        for (BlockPos pos : lis) {
            if (blockPredicate.test(level.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }

        return false;
    }


    public static boolean hasEnoughBlocksAround(BlockPos centerPos, int radius, Level level,
                                                Predicate<BlockState> blockPredicate, int requiredAmount) {
        return hasEnoughBlocksAround(centerPos, radius, radius, radius, level, blockPredicate, requiredAmount);
    }

    //same as before but just checks blocks facing this one (6 in total)
    public static boolean hasEnoughBlocksFacingMe(BlockPos centerPos, Level level,
                                                  Predicate<BlockState> blockPredicate, int requiredAmount) {
        int count = 0;
        //shuffling. provides way better result that iterating through it conventionally
        List<Direction> list = new ArrayList<Direction>(List.of(Direction.values()));
        Collections.shuffle(list, new Random(Mth.getSeed(centerPos)));
        for (Direction dir : list) {
            BlockPos pos = centerPos.relative(dir);
            if (blockPredicate.test(level.getBlockState(pos))) count += 1;
            if (count >= requiredAmount) return true;
        }
        return false;
    }

    public static boolean isLog(BlockState state) {
        return state.is(BlockTags.LOGS) && (!state.hasProperty(RotatedPillarBlock.AXIS) ||
                state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y) &&
                !Registry.BLOCK.getKey(state.getBlock()).getPath().contains("stripped");
    }

    public static boolean isIciclePos(BlockPos pos) {
        int rarity = CommonConfigs.ICICLE_RARITY.get();
        if (rarity == 1001) return false;
        Random posRandom = new Random(Mth.getSeed(pos));
        return posRandom.nextInt(rarity) == 0;
    }

    //spawn sooth?
    public static void onFireExpired(ServerLevel serverLevel, BlockPos pos, BlockState state) {
    }


    @Nullable
    public static BlockState getCharredState(BlockState state) {
        Block charred = null;
        if (state.is(BlockTags.WOODEN_FENCES)) {
            charred = ModBlocks.CHARRED_FENCE.get();
        } else if (state.is(BlockTags.FENCE_GATES)) {
            charred = ModBlocks.CHARRED_FENCE_GATE.get();
        } else if (state.is(BlockTags.WOODEN_SLABS)) {
            charred = ModBlocks.CHARRED_SLAB.get();
        } else if (state.is(BlockTags.WOODEN_STAIRS)) {
            charred = ModBlocks.CHARRED_STAIRS.get();
        } else if (state.is(BlockTags.PLANKS)) {
            charred = ModBlocks.CHARRED_PLANKS.get();
        } else if (state.is(BlockTags.LOGS_THAT_BURN)) {
            charred = ModBlocks.CHARRED_LOG.get();
        }
        if (charred == null) return null;
        return charred.withPropertiesOf(state);
    }


    //(for fire I think)
    public boolean ashStuff(BlockState state, Level level, BlockPos pos) {
        BlockState downState = level.getBlockState(pos.below());
        if (level.random.nextFloat() > 0.2f) {
            //level.setBlock(pos, ModBlocks.ASH_LAYER_BLOCK.withPropertiesOf(state), 3);
        } else if (downState.is(Blocks.GRASS_BLOCK)) {
            return level.setBlock(pos.below(), Blocks.DIRT.defaultBlockState(), 3);
        }
        return false;
    }


    //TODO: add serene seasons compat
    public static float getTemp(Level level, BlockPos pos) {
        Biome biome = level.getBiome(pos).value();
        return ((BiomeAccessor) (Object) biome).invokeGetTemperature(pos);
    }

    public static boolean isPosWet(Level level, Holder<Biome> biome, BlockPos pos) {
        return biome.is(ModTags.WET);
    }

    public static boolean isPosHot(Level level, Holder<Biome> biome, BlockPos pos) {
        return biome.is(ModTags.HOT);
    }

    public static boolean shouldGetWet(ServerLevel world, BlockPos pos) {
        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            var biome = world.getBiome(pos);
            BlockState neighborState = world.getBlockState(targetPos);

            if (neighborState.getFluidState().is(FluidTags.WATER)) {
                isTouchingWater = true;
                break;
            }

            if (world.isRainingAt(pos.relative(direction))) {
                temperature--;
            } else if (neighborState.is(ModTags.MAGMA_SOURCE) || world.dimensionType().ultraWarm()) {
                temperature++;
            } else if (isPosWet(world, biome, pos)) {
                temperature--;
            } else if (isPosHot(world, biome, pos)) {
                temperature++;
            }
        }
        return temperature < 0 || isTouchingWater;
    }


    public static void applyFreezing(Entity entity, int freezingIncrement) {
        applyFreezing(entity, freezingIncrement, false);
    }

    public static void applyFreezing(Entity entity, int freezingIncrement, boolean inWater) {
        if (entity instanceof Player) {
            int a = 1; //TODO: this isnt working
        }
        if (freezingIncrement != 0 && entity.canFreeze() && (entity instanceof LivingEntity le) &&
                !(EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, le) > 0) &&
                !entity.getType().is(ModTags.LIGHT_FREEZE_IMMUNE)) {
            if (inWater) {
                if (le.getItemBySlot(EquipmentSlot.FEET).is(Items.LEATHER_BOOTS)) return;
            } else {
                if (le.hasEffect(MobEffects.CONDUIT_POWER)) return;
            }
            entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze(), entity.getTicksFrozen() + freezingIncrement));
        }
    }

    public static void growHangingRoots(ServerLevel world, RandomSource random, BlockPos pos) {
        Direction dir = Direction.values()[1 + random.nextInt(5)].getOpposite();
        BlockPos targetPos = pos.relative(dir);
        BlockState targetState = world.getBlockState(targetPos);
        if (targetState.isAir()) return;
        BlockState newState = dir == Direction.DOWN ? Blocks.HANGING_ROOTS.defaultBlockState() :
                ModBlocks.HANGING_ROOTS_WALL.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, dir);
        if (targetState.is(Blocks.WATER)) {
            newState = newState.setValue(BlockStateProperties.WATERLOGGED, true);
        }
        world.setBlockAndUpdate(targetPos, newState);
    }


}
