package com.ordana.immersive_weathering.blocks.soil_types;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.lighting.LightEngine;

import java.util.List;
import java.util.Optional;

public class BaseSoilBlockFallable extends FallingBlock implements BonemealableBlock {

    public static final BooleanProperty SNOWY;

    public BaseSoilBlockFallable(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SNOWY, false));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public static boolean canBeGrass(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = levelReader.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(levelReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(levelReader, blockpos));
            return i < levelReader.getMaxLightLevel();
        }
    }

    public static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return canBeGrass(state, level, pos) && !level.getFluidState(blockpos).is(FluidTags.WATER);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getMaxLocalRawBrightness(pos.above()) >= 9 && canBeGrass(state, level, pos)) {

            for(int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockState s = level.getBlockState(blockPos);
                var soil = WeatheringHelper.getGrassySoil(s);
                if ((CommonConfigs.GRASS_OVER_MYCELIUM.get() && (s.is(Blocks.MYCELIUM))) && canPropagate(state, level, blockPos)) {
                    level.setBlockAndUpdate(blockPos, this.defaultBlockState().setValue(BlockStateProperties.SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                } else if (soil.isPresent() && canPropagate(state, level, blockPos)) {
                    if (soil.get().hasProperty(BlockStateProperties.SNOWY)) level.setBlockAndUpdate(blockPos, soil.get().setValue(BlockStateProperties.SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                    else level.setBlockAndUpdate(blockPos, soil.get());
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return level.getBlockState(pos.above()).isAir();
    }

    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.above();
        BlockState blockState = Blocks.GRASS.defaultBlockState();
        Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);

        label49:
        for(int i = 0; i < 128; ++i) {
            BlockPos blockPos2 = blockPos;

            for(int j = 0; j < i / 16; ++j) {
                blockPos2 = blockPos2.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockPos2.below()).is(this) || level.getBlockState(blockPos2).isCollisionShapeFullBlock(level, blockPos2)) {
                    continue label49;
                }
            }

            BlockState blockState2 = level.getBlockState(blockPos2);
            if (blockState2.is(blockState.getBlock()) && random.nextInt(10) == 0) {
                ((BonemealableBlock)blockState.getBlock()).performBonemeal(level, random, blockPos2, blockState2);
            }

            if (blockState2.isAir()) {
                Holder holder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = ((Biome)level.getBiome(blockPos2).value()).getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }

                    holder = ((RandomPatchConfiguration)((ConfiguredFeature)list.get(0)).config()).feature();
                } else {
                    if (!optional.isPresent()) {
                        continue;
                    }

                    holder = (Holder)optional.get();
                }

                ((PlacedFeature)holder.value()).place(level, level.getChunkSource().getGenerator(), random, blockPos2);
            }
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP ? state.setValue(SNOWY, isSnowySetting(neighborState)) : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos().above());
        return this.defaultBlockState().setValue(SNOWY, isSnowySetting(blockState));
    }

    private static boolean isSnowySetting(BlockState state) {
        return state.is(BlockTags.SNOW);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SNOWY);
    }

    static {
        SNOWY = BlockStateProperties.SNOWY;
    }
}
