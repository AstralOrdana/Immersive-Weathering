package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.data.block_growths.IConditionalGrowingBlock;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class ModGrassBlock extends GrassBlock implements BonemealableBlock, IConditionalGrowingBlock {
    public static final BooleanProperty FERTILE = SoilBlock.FERTILE;
    public static final IntegerProperty AGE = ModBlockProperties.AGE;

    public ModGrassBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, true).setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
        builder.add(AGE);
    }

    /*
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(FERTILE)) {
            int i = pos.getX();
            int j = pos.getY();
            int k = pos.getZ();
            double d = (double) i + random.nextDouble();
            double e = (double) j + random.nextDouble();
            double f = (double) k + random.nextDouble();
            level.addParticle(ModParticles.AZALEA_FLOWER.get(), d, e, f, 0.1D, 3D, 0.1D);
        }
    }
     */

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);
        RandomSource random = level.getRandom();
        if (CommonConfigs.DESIRE_PATHS.get()) {
            int j = state.getValue(AGE);
            if (entity instanceof Player || entity.getType().is(ModTags.TRAMPLES_PATHS)) {
                double rarity = CommonConfigs.DESIRE_PATH_RATE.get();
                if (random.nextFloat() < rarity && state.getValue(AGE) < 10) {
                    level.setBlock(pos, state.setValue(AGE, j + 1), 3);
                }
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return SoilBlock.isFertile(state) && super.isRandomlyTicking(state);
    }

    @Override
    public boolean canGrow(BlockState state) {
        return SoilBlock.isFertile(state);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        if (!SoilBlock.isFertile(state)) {
            state = state.setValue(FERTILE, true);
            level.setBlock(pos, state, 3);
        } else {
            super.performBonemeal(level, random, pos, state);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (SoilBlock.isFertile(state)) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.getItem() instanceof ShearsItem) {
                if (!level.isClientSide) {
                    level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pos, state.setValue(FERTILE, false), 11);

                    itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
                    level.gameEvent(player, GameEvent.SHEAR, pos);
                    player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
                } else {
                    level.addDestroyBlockEffect(pos, Blocks.GRASS.defaultBlockState());
                    // int p = level.random.nextInt(3)+3;

                    //  ModParticles.spawnParticleOnFace(level, pos,Direction.UP, BlockParticleOption.);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (level.getBlockState(pos) != state) return;
        if (state.getValue(AGE) == 10) {
            //TODO: dont like this stuff might want to remove. also dont like the extra fertile state, causes incompatibilities
            level.setBlockAndUpdate(pos, Blocks.DIRT_PATH.defaultBlockState());
            return;
        }
        if (state.getValue(AGE) < 10 && state.getValue(AGE) > 1) {
            int j = state.getValue(AGE);
            level.setBlock(pos, state.setValue(AGE, j - 1), 3);
            return;
        }

        if (level.getMaxLocalRawBrightness(pos.above()) >= 9) {
            BlockState blockState = this.defaultBlockState();

            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                BlockState s = level.getBlockState(blockPos);
                if ((CommonConfigs.GRASS_OVER_MYCELIUM.get() && (s.is(Blocks.MYCELIUM))) && canPropagate(blockState, level, blockPos)) {
                    level.setBlockAndUpdate(blockPos, this.defaultBlockState().setValue(SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                } else if ((s.is(Blocks.ROOTED_DIRT)) && canPropagate(blockState, level, blockPos)) {
                    level.setBlockAndUpdate(blockPos, ModBlocks.ROOTED_GRASS_BLOCK.get().defaultBlockState().setValue(SNOWY, level.getBlockState(blockPos.above()).is(Blocks.SNOW)));
                }
            }
        }
    }


}
