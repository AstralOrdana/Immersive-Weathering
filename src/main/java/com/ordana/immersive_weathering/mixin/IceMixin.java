package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
public class IceMixin extends Block {
    public IceMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockPos icePos = pos.down();
        if (random.nextFloat() < 0.01f) {
            if (world.getBlockState(icePos).isOf(Blocks.AIR)) {
                if (world.getFluidState(pos.up()).isOf(Fluids.WATER) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                    world.setBlockState(icePos, ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN).with(IcicleBlock.THICKNESS, Thickness.TIP));
                }
                var biome = world.getBiome(pos);
                if (biome.isIn(ModTags.HOT) && world.isDay() && !world.isRaining() && !world.isThundering()) {
                    world.setBlockState(icePos, ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN).with(IcicleBlock.THICKNESS, Thickness.TIP));
                }
            }
        }
        var biome = world.getBiome(pos);
        if ((world.getLightLevel(LightType.BLOCK, pos) > 13 - state.getOpacity(world, pos)) || (world.getRegistryKey() == World.NETHER) || (biome.isIn(ModTags.HOT) && world.isDay())) {
            this.melt(state, world, pos);
        }
    }

    public void melt(BlockState state, World world, BlockPos pos) {
        if (world.getDimension().isUltrawarm()) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, Blocks.WATER.getDefaultState());
            world.updateNeighbor(pos, Blocks.WATER, pos);
        }
    }
}
