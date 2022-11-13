package com.ordana.immersive_weathering.blocks.sandy;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.reg.ModTags;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public interface Sandy {

    IntegerProperty SANDINESS = ModBlockProperties.SANDINESS;
    IntegerProperty SAND_AGE = ModBlockProperties.SAND_AGE;

    Supplier<BiMap<Block, Block>> NORMAL_TO_SANDY = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.STONE, ModBlocks.SANDY_STONE.get())
                .put(Blocks.STONE_STAIRS, ModBlocks.SANDY_STONE_STAIRS.get())
                .put(Blocks.STONE_SLAB, ModBlocks.SANDY_STONE_SLAB.get())
                .put(ModBlocks.STONE_WALL.get(), ModBlocks.SANDY_STONE_WALL.get())
                .put(Blocks.COBBLESTONE, ModBlocks.SANDY_COBBLESTONE.get())
                .put(Blocks.COBBLESTONE_STAIRS, ModBlocks.SANDY_COBBLESTONE_STAIRS.get())
                .put(Blocks.COBBLESTONE_SLAB, ModBlocks.SANDY_COBBLESTONE_SLAB.get())
                .put(Blocks.COBBLESTONE_WALL, ModBlocks.SANDY_COBBLESTONE_WALL.get())
                .put(Blocks.STONE_BRICKS, ModBlocks.SANDY_STONE_BRICKS.get())
                .put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.SANDY_CHISELED_STONE_BRICKS.get())
                .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.SANDY_STONE_BRICK_STAIRS.get())
                .put(Blocks.STONE_BRICK_SLAB, ModBlocks.SANDY_STONE_BRICK_SLAB.get())
                .put(Blocks.STONE_BRICK_WALL, ModBlocks.SANDY_STONE_BRICK_WALL.get());
        return builder.build();
    });

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> SANDY_TO_NORMAL = Suppliers.memoize(() -> NORMAL_TO_SANDY.get().inverse());


    static Optional<BlockState> getSandy(BlockState state) {
        return getSandy(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<BlockState> getUnSandy(BlockState state) {
        return getUnSandy(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<Block> getUnSandy(Block block) {
        return Optional.ofNullable(SANDY_TO_NORMAL.get().get(block));
    }

    static Optional<Block> getSandy(Block block) {
        return Optional.ofNullable(NORMAL_TO_SANDY.get().get(block));
    }

    default boolean interactWithPlayer(BlockState state, Level level, BlockPos pos, Player player,
                                       InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        Optional<BlockState> unSandy = getUnSandy(state);
        if (unSandy.isPresent() && item instanceof ShovelItem) {
            level.playSound(player, pos, SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), UniformInt.of(3, 5));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer serverPlayer) {
                level.setBlockAndUpdate(pos, unSandy.get());
                if (state.getValue(ModBlockProperties.SANDINESS) == 0) level.setBlockAndUpdate(pos, unSandy.get());
                if (state.getValue(ModBlockProperties.SANDINESS) == 1) level.setBlockAndUpdate(pos, state.setValue(ModBlockProperties.SANDINESS, 0));
                if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get())
                    Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModBlocks.SAND_LAYER_BLOCK.get()));
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return true;
        }
        return false;
    }

    default void spawnParticles(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 1) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                double d0 = pos.getX() + random.nextDouble();
                double d1 = pos.getY() - 0.05D;
                double d2 = pos.getZ() + random.nextDouble();
                if (state.getValue(ModBlockProperties.SANDINESS) == 0 && random.nextInt(10) == 1) {
                    level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                } else
                    level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    static boolean isRandomSandyPos(BlockPos pos) {
        Random posRandom = new Random(Mth.getSeed(pos));
        return posRandom.nextInt(5) > 2;
    }

    default int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    default IntegerProperty getAgeProperty() {
        return SAND_AGE;
    }

    default void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        Optional<BlockState> unSandy = getUnSandy(state);
        Optional<BlockState> Sandy = getSandy(belowState);
        if (belowState.isAir() && state.getValue(SAND_AGE) > 0) {
            level.setBlockAndUpdate(pos, state.setValue(SAND_AGE, getAge(state) - 1));
            //if (state.getValue(SANDINESS) == 0 && unSandy.isPresent()) level.setBlockAndUpdate(pos, unSandy.get());
            level.setBlockAndUpdate(belowPos, ModBlocks.SAND_LAYER_BLOCK.get().defaultBlockState());
        }
        else if (belowState.is(ModTags.DOUBLE_SANDABLE) && isRandomSandyPos(pos) && Sandy.isPresent()) {
            if (state.getValue(SANDINESS) == 1) level.setBlockAndUpdate(pos, state.setValue(SANDINESS, 0));
            if (state.getValue(SANDINESS) == 0 && unSandy.isPresent()) level.setBlockAndUpdate(pos, unSandy.get());
            level.setBlockAndUpdate(belowPos, Sandy.get());
        }
    }

    default void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        Optional<BlockState> unSandy = Sandy.getUnSandy(state);
        BlockState neighborState = level.getBlockState(neighborPos);
        if (neighborState.getFluidState().is(Fluids.FLOWING_WATER) && unSandy.isPresent()) {
            level.setBlockAndUpdate(pos, unSandy.get());
            ItemStack stack = new ItemStack(ModBlocks.SAND_LAYER_BLOCK.get());
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
            level.playSound(null, pos, SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);

            //TODO make falling sand particles spawn on block faces
            if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()),
                    pos.getX() + 0.5D,
                    pos.getY() + 0.5D,
                    pos.getZ() + 0.5D,
                    10,
                    0.5D, 0.5D, 0.5D,
                    0.0D);

            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), UniformInt.of(3, 5));
        }
    }
}
