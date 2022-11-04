package com.ordana.immersive_weathering.blocks.frostable;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.blocks.sandy.Sandy;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Optional;
import java.util.function.Supplier;

public interface Frosty {

    Supplier<BiMap<Block, Block>> UNFROSTY_TO_FROSTY = Suppliers.memoize(() -> {
        var builder = ImmutableBiMap.<Block, Block>builder()
                .put(Blocks.AIR, ModBlocks.FROST.get())
                .put(Blocks.GLASS, ModBlocks.FROSTY_GLASS.get())
                .put(Blocks.FERN, ModBlocks.FROSTY_FERN.get())
                .put(Blocks.GRASS, ModBlocks.FROSTY_GRASS.get())
                .put(Blocks.GLASS_PANE, ModBlocks.FROSTY_GLASS_PANE.get());
        //CommonPlatform.addExtraCrackedBlocks(builder);
        return builder.build();
    });

    //reverse map for reverse access in descending order
    Supplier<BiMap<Block, Block>> FROSTY_TO_UNFROSTY = Suppliers.memoize(() -> UNFROSTY_TO_FROSTY.get().inverse());


    default Optional<BlockState> getFrosty(BlockState state) {
        return getFrosty(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    default Optional<BlockState> getUnfrosty(BlockState state) {
        return getUnfrosty(state.getBlock()).map(block -> block.withPropertiesOf(state));
    }

    static Optional<Block> getUnfrosty(Block block) {
        return Optional.ofNullable(FROSTY_TO_UNFROSTY.get().get(block));
    }

    static Optional<Block> getFrosty(Block block) {
        return Optional.ofNullable(UNFROSTY_TO_FROSTY.get().get(block));
    }

    BooleanProperty NATURAL = ModBlockProperties.NATURAL;

    default void tryUnFrost(BlockState state, ServerLevel world, BlockPos pos) {
        if (state.getValue(NATURAL)) {
            if (world.dimensionType().ultraWarm() || (!world.isRaining() && world.isDay()) || (world.getBrightness(LightLayer.BLOCK, pos) > 7 - state.getLightBlock(world, pos))) {
                world.setBlockAndUpdate(pos, getUnfrosty(state).get());
            }
        }
    }

    default InteractionResult interactWithPlayer(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof FlintAndSteelItem) {
            level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
            if (player instanceof ServerPlayer) {
                if (!player.getAbilities().instabuild) stack.shrink(1);
                level.setBlockAndUpdate(pos, this.getUnfrosty(state).get());
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

}
