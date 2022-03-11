package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class WeedsBlock extends CropBlock {

    protected WeedsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = this.getAge(state);
        if (i < this.getMaxAge()) {
            float f = getAvailableMoisture(this, world, pos);
            if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                world.setBlockState(pos, this.withAge(i + 1), 2);
            }
        }
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.WEEDS;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (this.getAge(state) == this.getMaxAge() && random.nextInt(10)==0) {
            double r = 0.3;
            double x = (double) pos.getX() + 0.5 + (random.nextDouble() - 0.5) * r;
            double y = (double) pos.getY() + 0.8 + (random.nextDouble() - 0.5) * r;
            double z = (double) pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * r;
            world.addParticle(ParticleTypes.WHITE_ASH, x, y, z, 0.1D, 0.5D, 0.1D);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos below = pos.down();
        return this.canPlantOnTop(world.getBlockState(below), world, below);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(ModTags.FERTILE_BLOCKS) || floor.isIn(BlockTags.DIRT);
    }
}
