package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.block_growth.IBlockGrowth;
import com.ordana.immersive_weathering.mixin.IceInvoker;
import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Thickness;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Random;

public class IceGrowth implements IBlockGrowth {

    @Override
    public Iterable<Block> getOwners() {
        return List.of(Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE);
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerWorld world, RegistryEntry<Biome> b) {
        Biome biome = b.value();
        Random random = world.random;

        //melt ice
        if (state.getBlock() instanceof IceInvoker ice) {
            if (world.getDimension().isUltrawarm()) {
                world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.3F, 2.9F + (random.nextFloat() - random.nextFloat()) * 0.6F);

                float i = pos.getX() + 0.5f;
                float j = pos.getY() + 0.5f;
                float k = pos.getZ() + 0.5f;
                world.spawnParticles(ParticleTypes.LARGE_SMOKE, i, j, k, 12, 0.2D, 0.2D, 0.2D, 0);
                ice.invokeMelt(state, world, pos);
            } else if (biome.isHot(pos) && world.isDay()) {
                ice.invokeMelt(state, world, pos);
            }
        }
    }
}
