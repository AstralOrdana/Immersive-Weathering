package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerWorld.class)
public abstract class SnowMixin {

    @Redirect(method = {"tickChunk"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;precipitationTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/biome/Biome$Precipitation;)V"))
            public void tickChunk(Block instance, BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW && WeatheringHelper.isIciclePos(pos)) {
            BlockPos p = pos.down(state.isIn(BlockTags.SNOW) ? 2 : 1);
            BlockState placement =  ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);
            if (world.getBlockState(p).isAir() && placement.canPlaceAt(world, p)) {
                if (Direction.Type.HORIZONTAL.stream().anyMatch(d -> world.isSkyVisible(p.offset(d)))) {
                    world.setBlockState(p, placement, 3);
                }
            }
        }
        instance.precipitationTick(state, world, pos, precipitation);
    }
}