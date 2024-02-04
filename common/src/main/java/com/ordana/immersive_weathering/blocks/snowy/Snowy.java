package com.ordana.immersive_weathering.blocks.snowy;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.util.TemperatureManager;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;
import java.util.function.Supplier;

public interface Snowy {

    Supplier<BiMap<Block, Block>> NORMAL_TO_SNOWY = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.STONE, ModBlocks.SNOWY_STONE.get())
                .put(Blocks.STONE_STAIRS, ModBlocks.SNOWY_STONE_STAIRS.get())
                .put(Blocks.STONE_SLAB, ModBlocks.SNOWY_STONE_SLAB.get())
                .put(ModBlocks.STONE_WALL.get(), ModBlocks.SNOWY_STONE_WALL.get())
                .put(Blocks.COBBLESTONE, ModBlocks.SNOWY_COBBLESTONE.get())
                .put(Blocks.COBBLESTONE_STAIRS, ModBlocks.SNOWY_COBBLESTONE_STAIRS.get())
                .put(Blocks.COBBLESTONE_SLAB, ModBlocks.SNOWY_COBBLESTONE_SLAB.get())
                .put(Blocks.COBBLESTONE_WALL, ModBlocks.SNOWY_COBBLESTONE_WALL.get())
                .put(Blocks.STONE_BRICKS, ModBlocks.SNOWY_STONE_BRICKS.get())
                .put(Blocks.CHISELED_STONE_BRICKS, ModBlocks.SNOWY_CHISELED_STONE_BRICKS.get())
                .put(Blocks.STONE_BRICK_STAIRS, ModBlocks.SNOWY_STONE_BRICK_STAIRS.get())
                .put(Blocks.STONE_BRICK_SLAB, ModBlocks.SNOWY_STONE_BRICK_SLAB.get())
                .put(Blocks.STONE_BRICK_WALL, ModBlocks.SNOWY_STONE_BRICK_WALL.get());
        return builder.build();
    });

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> SNOWY_TO_NORMAL = Suppliers.memoize(() -> NORMAL_TO_SNOWY.get().inverse());


    static Optional<BlockState> getSnowy(BlockState state) {
        return getSnowy(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<BlockState> getUnSnowy(BlockState state) {
        return getUnSnowy(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<Block> getUnSnowy(Block block) {
        return Optional.ofNullable(SNOWY_TO_NORMAL.get().get(block));
    }

    static Optional<Block> getSnowy(Block block) {
        return Optional.ofNullable(NORMAL_TO_SNOWY.get().get(block));
    }

    default boolean interactWithPlayer(BlockState state, Level level, BlockPos pos, Player player,
                                       InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        Optional<BlockState> unSnowy = getUnSnowy(state);
        if (unSnowy.isPresent() && item instanceof ShovelItem) {
            level.playSound(player, pos, SoundEvents.SNOW_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SNOW.defaultBlockState()), UniformInt.of(3, 5));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer serverPlayer) {
                level.setBlockAndUpdate(pos, unSnowy.get());
                if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get())
                    Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(Items.SNOWBALL));
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return true;
        }
        return false;
    }

    default void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        Optional<BlockState> unSnowy = getUnSnowy(state);

        if (!unSnowy.isPresent()) {
            return;
        }

        //todo remove this. they shoudlnt drop snow when melting
        for (Direction dir : Direction.values()) {
            if (level.getBrightness(LightLayer.BLOCK, pos.relative(dir)) > 11) {
                level.setBlockAndUpdate(pos, unSnowy.get());
                Block.popResourceFromFace(level, pos, dir, new ItemStack(Items.SNOWBALL));

                return;
            }
        }

        Supplier<Holder<Biome>> biome = Suppliers.memoize(() -> level.getBiome(pos));
        if (level.canSeeSky(pos.above()) && TemperatureManager.canSnowMelt(pos, biome)) {
            level.setBlockAndUpdate(pos, unSnowy.get());
        }
    }

    default void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        Optional<BlockState> unSnowy = getUnSnowy(state);
        BlockState neighborState = level.getBlockState(neighborPos);
        if (neighborState.getFluidState().is(FluidTags.WATER) || neighborState.getFluidState().is(FluidTags.LAVA) && unSnowy.isPresent()) {
            level.setBlockAndUpdate(pos, unSnowy.get());
            level.playSound(null, pos, SoundEvents.SNOW_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (neighborState.getFluidState().is(FluidTags.LAVA)) level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1f, 1f);
        }
    }
}
