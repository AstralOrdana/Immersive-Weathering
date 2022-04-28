package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.charred.CharredPillarBlock;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

public class MulchBlock extends Block {

    public MulchBlock(Settings settings, List<DefaultParticleType> particle) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SOAKED, false));
        this.particles = particle;
    }

    public static final BooleanProperty SOAKED = BooleanProperty.of("soaked");

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.shouldCancelInteraction()) {
            // empty bucket into mulch
            if (player.getStackInHand(hand).isOf(Items.WATER_BUCKET) && !state.get(SOAKED)) {
                if (!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(Items.BUCKET));
                }
                world.setBlockState(pos, state.with(SOAKED, true));
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
            // fill bucket from mulch
            else if (player.getStackInHand(hand).isOf(Items.BUCKET) && state.get(SOAKED)) {
                if (!player.isCreative()) {
                    player.setStackInHand(hand, new ItemStack(Items.WATER_BUCKET));
                }
                world.setBlockState(pos, state.with(SOAKED, false));
                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World level, BlockPos pos, Random random) {
        if (state.get(SOAKED)) {
            if (random.nextInt(25) == 1) {
                BlockPos blockpos = pos.down();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.isOpaque() || !blockstate.isSideSolidFullSquare(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        /*
        BlockState cropState = world.getBlockState(pos.up());
        if (state.isOf(ModBlocks.MULCH_BLOCK) && state.get(MulchBlock.SOAKED) && cropState.getBlock() instanceof CropBlock) {
            if (world.getBaseLightLevel(pos.up(), 0) >= 9) {
                int i = cropState.get(CropBlock.AGE);
                if (i < CropBlock.MAX_AGE) {
                    world.setBlockState(pos.up(), cropState.getBlock().getStateWithProperties(cropState).with(CropBlock.AGE, i + 1), 3);
                }
            }
        }*/

        int temperature = 0;
        boolean isTouchingWater = false;
        for (Direction direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            var biome = world.getBiome(pos);
            BlockState neighborState = world.getBlockState(targetPos);
            if (neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                isTouchingWater = true;
            }
            if (world.hasRain(pos.offset(direction)) || biome.isIn(ModTags.WET) || neighborState.getFluidState().getFluid() == Fluids.FLOWING_WATER || neighborState.getFluidState().getFluid() == Fluids.WATER) {
                temperature--;
            } else if (neighborState.isIn(ModTags.MAGMA_SOURCE) || biome.isIn(ModTags.HOT) || world.getRegistryKey() == World.NETHER) {
                temperature++;
            }
        }
        if (temperature < 0 || isTouchingWater) {
            if (!state.get(SOAKED)) {
                world.setBlockState(pos, state.with(SOAKED, true));
            }
        }
        else if (temperature > 0 && state.get(SOAKED)) {
            world.setBlockState(pos, state.with(SOAKED, false));
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.handleFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    private final List<DefaultParticleType> particles;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            if (world.isClient) {
                Random random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    for (var p : particles) {
                        world.addParticle(p,
                                entity.getX() + MathHelper.nextBetween(random,-0.2f,0.2f),
                                entity.getY() + 0.125,
                                entity.getZ() +MathHelper.nextBetween(random,-0.2f,0.2f),
                                MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f,
                                0.05D,
                                MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.001f);
                    }
                }
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(SOAKED);
    }
}
