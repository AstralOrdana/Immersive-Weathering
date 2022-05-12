package com.ordana.immersive_weathering.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
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
public abstract class FluidMixin extends Block implements FluidDrainable {
    private static final IntProperty LEVEL;
    @Shadow
    @Final
    protected FlowableFluid fluid;

    @Shadow public abstract FluidState getFluidState(BlockState state);

    private static final ImmutableList<Direction> FLOW_DIRECTIONS;

    public FluidMixin(Settings settings, FlowableFluid fluid) {
        super(settings);
    }

    private void playExtinguishSound(WorldAccess world, BlockPos pos) {
        world.syncWorldEvent(1501, pos, 0);
    }

    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    private void receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if(ImmersiveWeathering.getConfig().generatorsConfig.allGenerators) {
            if (this.fluid.isIn(FluidTags.LAVA)) {
                boolean hasWater = false;
                boolean blueIceDown = false;
                boolean blueIceUp = false;
                boolean basaltDown = false;
                boolean hasBlueIce = false;
                boolean hasQuartz = false;
                boolean hasDiorite = false;
                boolean hasAsh = false;
                boolean hasMagma = false;
                boolean hasBubbles = false;
                boolean hasSoulfire = false;
                boolean hasClay = false;
                boolean hasSand = false;
                boolean hasRedSand = false;
                for (Direction direction : DIRECTIONS) {
                    BlockPos blockPos = pos.offset(direction.getOpposite());
                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                        hasWater = true;
                    }
                    if (world.getBlockState(pos.down()).isOf(Blocks.BASALT)) {
                        basaltDown = true;
                    }
                    if (world.getBlockState(pos.down()).isOf(Blocks.BLUE_ICE)) {
                        blueIceDown = true;
                    }
                    if (world.getBlockState(pos.up()).isOf(Blocks.BLUE_ICE)) {
                        blueIceUp = true;
                    }
                    if (world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                        hasBlueIce = true;
                    }
                    if (world.getBlockState(blockPos).isOf(Blocks.MAGMA_BLOCK)) {
                        hasMagma = true;
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
                    if (world.getBlockState(pos.down()).isOf(Blocks.BUBBLE_COLUMN)) {
                        hasBubbles = true;
                    }
                    if (world.getBlockState(blockPos).isOf(Blocks.SOUL_FIRE)) {
                        hasSoulfire = true;
                    }
                    if (world.getBlockState(blockPos).isOf(Blocks.CLAY)) {
                        hasClay = true;
                    }
                    if (world.getBlockState(blockPos).isOf(Blocks.SAND)) {
                        hasSand = true;
                    }
                    if (world.getBlockState(blockPos).isOf(Blocks.RED_SAND)) {
                        hasRedSand = true;
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.basaltGenerator) {
                        if (!world.getFluidState(pos).isStill()) {
                            if (basaltDown) {
                                world.setBlockState(pos, Blocks.BASALT.getDefaultState());
                                this.playExtinguishSound(world, pos);
                                cir.setReturnValue(false);
                            }
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.deepslateGenerator) {
                        if (blueIceDown && blueIceUp) {
                            world.setBlockState(pos, Blocks.DEEPSLATE.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.dioriteGenerator) {
                        if (hasWater && hasQuartz) {
                            world.setBlockState(pos, Blocks.DIORITE.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.andesiteGenerator) {
                        if (hasWater && hasDiorite) {
                            world.setBlockState(pos, Blocks.ANDESITE.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.graniteGenerator) {
                        if (hasWater && hasQuartz && hasDiorite) {
                            world.setBlockState(pos, Blocks.GRANITE.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.tuffGenerator) {
                        if (hasWater && hasAsh) {
                            world.setBlockState(pos, Blocks.TUFF.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.blackstoneGenerator) {
                        if (hasMagma && hasBlueIce) {
                            world.setBlockState(pos, Blocks.BLACKSTONE.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.magmaGenerator) {
                        if (hasBubbles) {
                            world.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.terracottaGenerator) {
                        if (hasClay) {
                            if (world.getBlockState(blockPos).isOf(Blocks.CLAY)) {
                                world.setBlockState(blockPos, Blocks.TERRACOTTA.getDefaultState());
                                this.playExtinguishSound(world, pos);
                                cir.setReturnValue(false);
                            }
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.vitrifiedSandGenerator) {
                        if (hasSand || hasRedSand) {
                            if (world.getBlockState(blockPos).isIn(BlockTags.SAND)) {
                                world.setBlockState(blockPos, ModBlocks.VITRIFIED_SAND.getDefaultState());
                                this.playExtinguishSound(world, pos);
                                cir.setReturnValue(false);
                            }
                        }
                    }
                    if(ImmersiveWeathering.getConfig().generatorsConfig.cryingObsidianGenerator) {
                        if (world.getFluidState(blockPos).isIn(FluidTags.WATER) && hasSoulfire) {
                            world.setBlockState(pos, Blocks.CRYING_OBSIDIAN.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
                    }
                }
            } else if (this.fluid.isIn(FluidTags.WATER)) {
                boolean hasBlueIce = false;
                if(ImmersiveWeathering.getConfig().generatorsConfig.iceGenerator) {
                    for (Direction direction : DIRECTIONS) {
                        BlockPos blockPos = pos.offset(direction.getOpposite());
                        if (world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                            hasBlueIce = true;
                        }
                        if (hasBlueIce) {
                            world.setBlockState(pos, Blocks.ICE.getDefaultState());
                            this.playExtinguishSound(world, pos);
                            cir.setReturnValue(false);
                        }
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
