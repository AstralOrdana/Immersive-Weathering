package com.ordana.immersive_weathering.blocks.soil;

import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModTags;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BeetrootBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.util.RandomSource;

public class MulchBlock extends FarmBlock {

    public MulchBlock(Properties settings) {
        super(settings);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (isSoaked(state)) {
            if (random.nextInt(25) == 1) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
                    double d0 = pos.getX() + random.nextDouble();
                    double d1 = pos.getY() - 0.05D;
                    double d2 = pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        BlockState cropState = level.getBlockState(pos.above());
        if (CommonConfigs.MULCH_GROWS_CROPS.get() && level.getRawBrightness(pos.above(), 0) >= 9 && state.getValue(MulchBlock.MOISTURE) == 7) {
            if(CommonConfigs.MULCH_GROWTH_TYPE_BONEMEAL.get()) {
                if (cropState.getBlock() instanceof CropBlock crop) {
                    crop.growCrops(level, pos.above(), cropState);
                }
            }
            else if(CommonConfigs.MULCH_GROWTH_TYPE_RANDOM_TICK.get()){
                for(int i = 0; i<CommonConfigs.MULCH_GROWTH_RANDOM_TICKS.get(); i++){
                    cropState.randomTick(level, pos.above(), random);
                }
            }
        }

        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            var biome = level.getBiome(pos);
            BlockState neighborState = level.getBlockState(targetPos);
            if (neighborState.getFluidState().is(FluidTags.WATER)) {
                isTouchingWater = true;
            }
            if (level.isRainingAt(pos.relative(direction)) || biome.is(ModTags.WET) || neighborState.getFluidState().is(FluidTags.WATER)) {
                temperature--;
            } else if (neighborState.is(ModTags.MAGMA_SOURCE) || biome.is(ModTags.HOT) || level.dimension() == Level.NETHER) {
                temperature++;
            }
        }
        if (temperature < 0 || isTouchingWater) {
            if (state.getValue(MOISTURE) == 0) {
                level.setBlockAndUpdate(pos, state.setValue(MOISTURE, 7));
            }
        }
        else if (temperature > 0 && isSoaked(state)) {
            level.setBlockAndUpdate(pos, state.setValue(MOISTURE, 0));
        }
    }

    private boolean isSoaked(BlockState state) {
        return state.getValue(MOISTURE) == 7;
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.isSecondaryUseActive()) {
            // empty bucket into mulch
            if (player.getItemInHand(hand).is(Items.WATER_BUCKET) && !isSoaked(state)) {
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                level.setBlockAndUpdate(pos, state.setValue(MOISTURE, 7));
                level.playSound(player, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                return InteractionResult.SUCCESS;
            }
            // fill bucket from mulch
            else if (player.getItemInHand(hand).is(Items.BUCKET) && isSoaked(state)) {
                if (!player.isCreative()) {
                    ItemStack stack = player.getItemInHand(hand);
                    ItemStack itemStack2 = ItemUtils.createFilledResult(stack, player, Items.WATER_BUCKET.getDefaultInstance());
                    player.setItemInHand(hand, itemStack2);
                }
                level.setBlockAndUpdate(pos, state.setValue(MOISTURE, 0));
                level.playSound(player, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
