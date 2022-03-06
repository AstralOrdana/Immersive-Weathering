package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
abstract public class IceMixin extends Block {
    public IceMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        BlockPos icePos = pos.below();
        if (random.nextFloat() < 0.01f) {
            if (world.getBlockState(icePos).is(Blocks.AIR)) {
                //TODO: this logic doesn't seem right
                //to form we need hot weather in a cold biome or water above & cold biome
                var biome = ResourceKey.create(ForgeRegistries.Keys.BIOMES,
                        world.getBiome(pos).value().getRegistryName());
                if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)) {
                    if (world.getFluidState(pos.above()).is(FluidTags.WATER) || ( world.isDay() && !world.isRaining() && !world.isThundering())) {
                        world.setBlockAndUpdate(icePos, ModBlocks.ICICLE.get().defaultBlockState()
                                .setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.DOWN)
                                .setValue(IcicleBlock.THICKNESS, DripstoneThickness.TIP));
                    }
                }
            }
        }

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
