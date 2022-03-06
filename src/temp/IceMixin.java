package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
public class IceMixin extends Block {
    public IceMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockPos icePos = pos.below();
        if (random.nextFloat() < 0.01f) {
            if (world.getBlockState(icePos).is(Blocks.AIR)) {
                if (world.getFluidState(pos.above()).is(Fluids.WATER) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                    world.setBlockAndUpdate(icePos, ModBlocks.ICICLE.defaultBlockState().setValue(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN).setValue(IcicleBlock.THICKNESS, DripstoneThickness.TIP));
                }
                var biome = world.getBiome(pos);
                if (biome.is(ModTags.HOT) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                    world.setBlockAndUpdate(icePos, ModBlocks.ICICLE.defaultBlockState().setValue(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN).setValue(IcicleBlock.THICKNESS, DripstoneThickness.TIP));
                }
                if(has water above && isDay && not raining && not thundering){
                    create icicle
                }
                if(is biome hot && is day &&  is not raining && is not thundering){
                    create icicle
                }
            }
        }
        var biome = world.getBiome(pos);
        if ((world.getBrightness(LightLayer.BLOCK, pos) > 13 - state.getLightBlock(world, pos)) || (world.dimension() == Level.NETHER) || (biome.is(ModTags.HOT) && world.isDay())) {
            this.melt(state, world, pos);
        }
    }

    public void melt(BlockState state, Level world, BlockPos pos) {
        if (world.dimensionType().ultraWarm()) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            world.neighborChanged(pos, Blocks.WATER, pos);
        }
    }
}
