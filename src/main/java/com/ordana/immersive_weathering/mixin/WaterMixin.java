package com.ordana.immersive_weathering.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(LiquidBlock.class)
public class WaterMixin extends Block implements BucketPickup {
    private static final IntegerProperty LEVEL;
    @Shadow
    @Final
    public FlowingFluid fluid;
    private static final ImmutableList<Direction> FLOW_DIRECTIONS;

    public WaterMixin(Properties settings, FlowingFluid fluid) {
        super(settings);
    }

    private void playExtinguishSound(LevelAccessor world, BlockPos pos) {
        world.levelEvent(1501, pos, 0);
    }

    @Inject(method = "receiveNeighborFluids", at = @At("TAIL"), cancellable = true)
    public void receiveNeighborFluids(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid.is(FluidTags.LAVA)) {
            boolean bl = world.getBlockState(pos.below()).is(Blocks.NETHERRACK);
            UnmodifiableIterator var5 = FLOW_DIRECTIONS.iterator();
            while(var5.hasNext()) {
                Direction direction = (Direction)var5.next();
                BlockPos blockPos = pos.relative(direction.getOpposite());
                if (bl && world.getBlockState(blockPos).is(Blocks.PACKED_ICE)) {
                    world.setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
                    this.playExtinguishSound(world, pos);
                    cir.setReturnValue(false);
                }
            }
        }
        cir.setReturnValue(true);
    }

    static {
        LEVEL = BlockStateProperties.LEVEL;
        FLOW_DIRECTIONS = ImmutableList.of(Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);
    }

    @Override
    public ItemStack pickupBlock(LevelAccessor world, BlockPos pos, BlockState state) {
        if ((Integer)state.getValue(LEVEL) == 0) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            return new ItemStack(this.fluid.getBucket());
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return this.fluid.getPickupSound();
    }
}
