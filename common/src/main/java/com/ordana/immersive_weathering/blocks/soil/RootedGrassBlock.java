package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.block_growths.IConditionalGrowingBlock;
import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LayerLightEngine;
import java.util.List;
import java.util.Random;

import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.BlockHitResult;


public class RootedGrassBlock extends ModGrassBlock implements BonemealableBlock, IConditionalGrowingBlock {
    public RootedGrassBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof ShovelItem && !state.getValue(SNOWY)) {
            level.playSound(player, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer) {
                level.setBlockAndUpdate(pos, Blocks.DIRT_PATH.defaultBlockState());
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    //TODO: check this whole class
    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {

        WeatheringHelper.growHangingRoots(world, random, pos);

        BlockPos blockPos = pos.above();
        BlockState blockState = Blocks.GRASS.defaultBlockState();
        world.setBlockAndUpdate(pos, this.defaultBlockState().setValue(FERTILE, true));
        label46:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos2 = blockPos;

            for(int j = 0; j < i / 16; ++j) {
                blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!world.getBlockState(blockPos2.below()).is(this) || world.getBlockState(blockPos2).isCollisionShapeFullBlock(world, blockPos2)) {
                    continue label46;
                }
            }

            BlockState blockState2 = world.getBlockState(blockPos2);
            if (blockState2.is(blockState.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock)blockState.getBlock()).performBonemeal(world, random, blockPos2, blockState2);
            }

            if (blockState2.isAir()) {
                Holder registryEntry;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = ((Biome)world.getBiome(blockPos2).value()).getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    registryEntry = ((RandomPatchConfiguration)((ConfiguredFeature)list.get(0)).config()).feature();
                } else {
                    registryEntry = VegetationPlacements.GRASS_BONEMEAL;
                }

                ((PlacedFeature)registryEntry.value()).place(world, world.getChunkSource().getGenerator(), random, blockPos2);
            }
        }
    }

    private static boolean canPropagate(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        return canBeGrass(state, world, pos) && !world.getFluidState(blockPos).is(FluidTags.WATER);
    }

    private static boolean canBeGrass(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LayerLightEngine.getLightBlockInto(world, state, pos, blockState, blockPos, Direction.UP, blockState.getLightBlock(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.getValue(FERTILE);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (state.getValue(AGE) == 10) {
            level.setBlockAndUpdate(pos, Blocks.DIRT_PATH.defaultBlockState());
        }
        if (state.getValue(AGE) < 10 && state.getValue(AGE) > 1) {
            int j = state.getValue(AGE);
            level.setBlock(pos, state.setValue(AGE, j - 1), 3);
        }
        if (!canBeGrass(state, level, pos)) {
            level.setBlockAndUpdate(pos, Blocks.ROOTED_DIRT.defaultBlockState());
        } else {
            if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockState = this.defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if ((level.getBlockState(blockPos).is(Blocks.DIRT) || (CommonConfigs.GRASS_OVER_MYCELIUM.get() && (level.getBlockState(blockPos).is(Blocks.MYCELIUM)))) && canPropagate(blockState, level, blockPos)) {
                        level.setBlockAndUpdate(blockPos, Blocks.GRASS_BLOCK.defaultBlockState().setValue(SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    } else if ((level.getBlockState(blockPos).is(Blocks.ROOTED_DIRT)) && canPropagate(blockState, level, blockPos)) {
                        level.setBlockAndUpdate(blockPos, ModBlocks.ROOTED_GRASS_BLOCK.get().defaultBlockState().setValue(SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    }
                }
            }
        }
    }
}
