package com.ordana.immersive_weathering.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FluidBlock.class)
public class FluidMixin extends Block implements FluidDrainable {
    private static final IntProperty LEVEL;
    @Shadow
    @Final
    protected FlowableFluid fluid;
    private static final ImmutableList<Direction> FLOW_DIRECTIONS;

    public FluidMixin(Settings settings, FlowableFluid fluid) {
        super(settings);
    }

    private void playExtinguishSound(WorldAccess world, BlockPos pos) {
        world.syncWorldEvent(1501, pos, 0);
    }

    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    public void receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        FluidState fluidState = world.getFluidState(pos);
        if (this.fluid.isIn(FluidTags.LAVA)) {
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
            for (Direction direction : DIRECTIONS) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    hasWater = true;
                }
                if (world.getBlockState(pos.down()).isOf(Blocks.BLUE_ICE)) {
                    blueIceDown = true;
                }
                if (world.getBlockState(pos.up()).isOf(Blocks.BLUE_ICE)) {
                    blueIceUp = true;
                }
                if (world.getBlockState(blockPos).isOf(Blocks.SMOOTH_QUARTZ)) {
                    hasQuartz = true;
                }
                if (world.getBlockState(blockPos).isOf(Blocks.DIORITE)) {
                    hasDiorite = true;
                }
                if (world.getBlockState(blockPos).isOf(ModBlocks.ASH_BLOCK)) {
                    hasAsh = true;
                }
                if (world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                    hasBlueIce = true;
                }
                if (world.getBlockState(blockPos).isOf(Blocks.MAGMA_BLOCK)) {
                    hasMagma = true;
                }
                if (world.getBlockState(pos.down()).isOf(Blocks.BUBBLE_COLUMN)) {
                    hasBubbles = true;
                }
                if (world.getBlockState(blockPos).isOf(Blocks.SOUL_FIRE)) {
                    hasSoulfire = true;
                }
                if (world.getBlockState(blockPos).isOf(Blocks.CLAY)) {
                    hasClay = true;
                }
                if (world.getBlockState(blockPos).isIn(BlockTags.SAND)) {
                    hasSand = true;
                }
                if (blueIceDown && blueIceUp) {
                    world.setBlockState(pos, Blocks.DEEPSLATE.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasWater && hasQuartz) {
                    world.setBlockState(pos, Blocks.DIORITE.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasWater && hasDiorite) {
                    world.setBlockState(pos, Blocks.ANDESITE.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasWater && hasQuartz && hasDiorite) {
                    world.setBlockState(pos, Blocks.GRANITE.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasWater && hasAsh) {
                    world.setBlockState(pos, Blocks.TUFF.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasMagma && hasBlueIce) {
                    world.setBlockState(pos, Blocks.BLACKSTONE.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasBubbles) {
                    world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasSoulfire) {
                    world.setBlockState(pos, Blocks.CRYING_OBSIDIAN.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
                if (hasClay) {
                    if (world.getBlockState(blockPos).isOf(Blocks.CLAY)) {
                        world.setBlockState(blockPos, Blocks.TERRACOTTA.getDefaultState());
                        this.playExtinguishSound(world, pos);
                        cir.setReturnValue(false);
                    }
                }
                if (hasSand) {
                    if (world.getBlockState(blockPos).isIn(BlockTags.SAND)) {
                        world.setBlockState(blockPos, ModBlocks.VITRIFIED_SAND.getDefaultState());
                        this.playExtinguishSound(world, pos);
                        cir.setReturnValue(false);
                    }
                }
            }
        }
        else if (this.fluid.isIn(FluidTags.WATER) && !this.fluid.isStill(fluidState)) {
            boolean hasSandstone = false;
            for (Direction direction : FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getBlockState(blockPos).isOf(Blocks.SANDSTONE)) {
                    hasSandstone = true;
                }
                if (hasSandstone) {
                    if (world.getBlockState(pos.down()).isOf(Blocks.AIR)) {
                        world.setBlockState(pos.down(), Blocks.SAND.getDefaultState());
                        this.playExtinguishSound(world, pos);
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }

    static {
        LEVEL = Properties.LEVEL_15;
        FLOW_DIRECTIONS = ImmutableList.of(Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        if ((Integer)state.get(LEVEL) == 0) {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            return new ItemStack(this.fluid.getBucketItem());
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return this.fluid.getBucketFillSound();
    }
}
