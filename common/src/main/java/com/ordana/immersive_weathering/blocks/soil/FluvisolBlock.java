package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.blocks.Soaked;
import com.ordana.immersive_weathering.blocks.sandy.Sandy;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.util.RandomSource;

public class FluvisolBlock extends SoilBlock implements Soaked {

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape PUDDLE_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);

    public FluvisolBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SOAKED, false).setValue(FERTILE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager);
        stateManager.add(SOAKED);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(SOAKED)) {
            return PUDDLE_SHAPE;
        } else return SHAPE;
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.getValue(SOAKED)) {
            return PUDDLE_SHAPE;
        } else return SHAPE;
    }

    @Override
    public boolean canGrow(BlockState state) {
        return state.getValue(FERTILE) && state.getValue(SOAKED);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {

        //world gen hax to speed up turning those blocks to dirt
        BlockState downState = world.getBlockState(pos.below());
        if (downState.is(ModBlocks.SILT.get()) || downState.is(ModBlocks.FLUVISOL.get())) {
            world.setBlockAndUpdate(pos.below(), Blocks.DIRT.defaultBlockState());
        }

        boolean newState = world.isRainingAt(pos.above());
        if (state.getValue(SOAKED) != newState) {
            world.setBlockAndUpdate(pos, state.setValue(SOAKED, newState));
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (state.getValue(SOAKED)) {
            if (world.isClientSide && (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this))) {
                RandomSource random = world.getRandom();
                boolean bl = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(
                            ParticleTypes.SPLASH,
                            false,
                            entity.getX() + Mth.randomBetween(random, -0.2f, 0.2f),
                            pos.getY() + 1D,
                            entity.getZ() + Mth.randomBetween(random, -0.2f, 0.2f),
                            0D, 0.25D, 0D);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (stack.getItem() == Items.GLASS_BOTTLE && state.getValue(FluvisolBlock.SOAKED) && CommonConfigs.MUDDY_WATER_ENABLED.get()) {
            level.playSound(player, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
            if (player instanceof ServerPlayer) {
                ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, ModItems.POND_WATER.get().getDefaultInstance());
                player.setItemInHand(hand, itemStack2);
                level.setBlockAndUpdate(pos, state.setValue(FluvisolBlock.SOAKED, Boolean.FALSE));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (item instanceof ShovelItem && !state.getValue(SOAKED) && !state.getValue(SNOWY)) {
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
}


