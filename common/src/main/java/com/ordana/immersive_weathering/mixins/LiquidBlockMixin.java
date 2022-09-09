package com.ordana.immersive_weathering.mixins;

import com.google.common.collect.ImmutableList;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.platform.CommonPlatform;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModTags;
import com.ordana.immersive_weathering.utils.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin extends Block implements BucketPickup {

    @Shadow
    @Final
    public static ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS;

    @Shadow
    protected abstract void fizz(LevelAccessor p_54701_, BlockPos p_54702_);

    @Shadow
    public abstract FluidState getFluidState(BlockState blockState);

    @Shadow
    @Final
    protected FlowingFluid fluid;

    public LiquidBlockMixin(Properties settings, FlowingFluid fluid) {
        super(settings);
    }

    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    private void shouldSpreadLiquid(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        var fluid = this.getOwnFluid();
        if (fluid != null && fluid.is(FluidTags.LAVA)) {
            boolean hasWater = false;
            boolean blueIceDown = false;
            boolean blueIceUp = false;
            boolean hasBlueIce = false;
            boolean hasQuartz = false;
            boolean hasDiorite = false;
            boolean hasAsh = false;
            boolean hasMagma = false;
            boolean hasBubbles = false;
            boolean hasSoulfire = false;
            boolean hasClay = false;
            boolean hasSand = false;

            BlockState downState = world.getBlockState(pos.below());

            if (downState.is(Blocks.BLUE_ICE)) {
                blueIceDown = true;
            } else if (downState.is(Blocks.BUBBLE_COLUMN)) {
                hasBubbles = true;
            }

            if (world.getBlockState(pos.above()).is(Blocks.BLUE_ICE)) {
                blueIceUp = true;
            }

            for (Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.relative(direction.getOpposite());
                BlockState currentState = world.getBlockState(blockPos);

                if (currentState.getFluidState().is(FluidTags.WATER)) {
                    hasWater = true;
                }
                if (currentState.is(Blocks.SMOOTH_QUARTZ)) {
                    hasQuartz = true;
                } else if (currentState.is(Blocks.DIORITE)) {
                    hasDiorite = true;
                } else if (currentState.is(ModBlocks.ASH_BLOCK.get())) {
                    hasAsh = true;
                } else if (currentState.is(Blocks.BLUE_ICE)) {
                    hasBlueIce = true;
                } else if (currentState.is(Blocks.MAGMA_BLOCK)) {
                    hasMagma = true;
                } else if (currentState.is(Blocks.SOUL_FIRE)) {
                    hasSoulfire = true;
                } else if (currentState.is(Blocks.CLAY)) {
                    hasClay = true;
                } else if (currentState.is(BlockTags.SAND)) {
                    hasSand = true;
                }


                BlockState newState = null;

                if (hasWater && hasQuartz && hasDiorite) {
                    newState = Blocks.GRANITE.defaultBlockState();
                } else if (blueIceDown && blueIceUp) {
                    newState = Blocks.DEEPSLATE.defaultBlockState();
                } else if (hasWater && hasQuartz) {
                    newState = Blocks.DIORITE.defaultBlockState();
                } else if (hasWater && hasDiorite) {
                    newState = Blocks.ANDESITE.defaultBlockState();
                } else if (hasWater && hasAsh) {
                    newState = Blocks.TUFF.defaultBlockState();
                } else if (hasMagma && hasBlueIce) {
                    newState = Blocks.BLACKSTONE.defaultBlockState();
                }
                if (hasWater && hasSoulfire) {
                    newState = Blocks.CRYING_OBSIDIAN.defaultBlockState();
                } else if (hasBubbles) {
                    newState = Blocks.MAGMA_BLOCK.defaultBlockState();
                } else if (hasSoulfire) {
                    newState = Blocks.CRYING_OBSIDIAN.defaultBlockState();
                } else if (hasClay) {
                    pos = pos.relative(direction.getOpposite());
                    newState = Blocks.TERRACOTTA.defaultBlockState();
                } else if (hasSand) {
                    pos = pos.relative(direction.getOpposite());
                    newState = ModBlocks.VITRIFIED_SAND.get().defaultBlockState();
                }

                if (newState != null) {
                    world.setBlockAndUpdate(pos, newState);
                    this.fizz(world, pos);
                    cir.setReturnValue(false);
                    return;
                }
            }
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && world.getBiome(pos).is(ModTags.ICY) && this.getOwnFluid().is(FluidTags.WATER)) {
            var freezing = CommonConfigs.FREEZING_WATER_SEVERITY.get();
            WeatheringHelper.applyFreezing(entity, freezing, true);
        }
    }

    @Unique
    public FlowingFluid getOwnFluid() {
        //TODO: re add
       // var f = CommonPlatform.getFlowingFluid((LiquidBlock) (Object) this);
        //if (f == null)
            return this.fluid;
        //return f;
    }

}
