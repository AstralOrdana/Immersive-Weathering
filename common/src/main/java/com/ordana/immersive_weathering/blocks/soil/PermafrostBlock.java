package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.minecraft.util.RandomSource;

public class PermafrostBlock extends Block {

    public static final BooleanProperty DIRTY = BooleanProperty.create("dirty");

    public PermafrostBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(DIRTY, false));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockState upState = world.getBlockState(pos.above());
        BlockState downState = world.getBlockState(pos.below());
        if (upState.is(BlockTags.DIRT)) {
            world.setBlockAndUpdate(pos, ModBlocks.PERMAFROST.get().defaultBlockState().setValue(PermafrostBlock.DIRTY, true));
        }
        if (downState.is(BlockTags.DIRT)) {
            world.setBlockAndUpdate(pos.below(), ModBlocks.CRYOSOL.get().defaultBlockState());
        }
    }


    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!(entity instanceof LivingEntity) || EnchantmentHelper.hasFrostWalker((LivingEntity) entity) || entity.getType() == EntityType.VILLAGER || entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES) || entity.getType() == EntityType.FOX || entity.getType() == EntityType.RABBIT || entity.getType() == EntityType.SHEEP || entity.getType() == EntityType.STRAY || entity.getType() == EntityType.GOAT) {
            return;
        }
        if(!world.isClientSide) {
            int freezing = CommonConfigs.FREEZING_PERMAFROST_SEVERITY.get();
            WeatheringHelper.applyFreezing(entity, freezing);
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP ? state.setValue(DIRTY, isDirt(neighborState)) : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().above());
        return this.defaultBlockState().setValue(DIRTY, isDirt(blockState));
    }

    private static boolean isDirt(BlockState state) {
        return state.is(BlockTags.DIRT);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        stateManager.add(DIRTY);
    }
}
