package com.ordana.immersive_weathering.blocks.sandy;

import com.ordana.immersive_weathering.WeatheringHelper;
import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.mehvahdjukaar.moonlight.api.block.VerticalSlabBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SandyVerticalSlabBlock extends VerticalSlabBlock {

    public SandyVerticalSlabBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ModBlockProperties.SANDINESS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
        super.createBlockStateDefinition(stateManager);
        stateManager.add(ModBlockProperties.SANDINESS);
    }

    @Override
    public void animateTick (BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 1) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                double d0 = (double) pos.getX() + random.nextDouble();
                double d1 = (double) pos.getY() - 0.05D;
                double d2 = (double) pos.getZ() + random.nextDouble();
                if (state.getValue(ModBlockProperties.SANDINESS) == 0 && random.nextInt(10) == 1) {
                    level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
                else level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (item instanceof ShovelItem) {
            level.playSound(player, pos, SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.SAND.defaultBlockState()), UniformInt.of(3, 5));
            stack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(hand));
            if (player instanceof ServerPlayer) {
                if (state.getValue(ModBlockProperties.SANDINESS) == 0) level.setBlockAndUpdate(pos, WeatheringHelper.getUnsandyBlock(state).orElse(null));
                if (state.getValue(ModBlockProperties.SANDINESS) == 1) level.setBlockAndUpdate(pos, state.setValue(ModBlockProperties.SANDINESS, 0));
                if (!player.isCreative() || CommonConfigs.CREATIVE_DROP.get()) Block.popResourceFromFace(level, pos, hitResult.getDirection(), new ItemStack(ModBlocks.SAND_LAYER_BLOCK.get()));
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state,level,pos,player,hand,hitResult);
    }
}
