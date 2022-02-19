package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.*;

public class MossySlabBlock extends MossableSlabBlock implements Fertilizable {
    public MossySlabBlock(MossLevel mossLevel, Settings settings) {
        super(mossLevel, settings);
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {

        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.COBBLESTONE_SLAB);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
        CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICKS, Blocks.STONE_BRICKS);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.STONE_BRICK_SLAB);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS);
        CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.STONE_BRICK_WALL);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICKS, Blocks.BRICKS);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_SLAB, Blocks.BRICK_SLAB);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_STAIRS, Blocks.BRICK_STAIRS);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_WALL, Blocks.BRICK_WALL);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE, Blocks.STONE);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE_SLAB, Blocks.STONE_SLAB);
        CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE_STAIRS, Blocks.STONE_STAIRS);


        for (var direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState targetBlock = world.getBlockState(targetPos);
            float f = 0.5f;
            if (random.nextFloat() > 0.5f) {
                if (world.getBlockState(targetPos).isIn(ImmersiveWeathering.MOSSABLE)) {
                    CLEANED_BLOCKS.forEach((mossy, clean) -> {
                        if (targetBlock.isOf(clean)) {
                            world.setBlockState(targetPos, mossy.getStateWithProperties(targetBlock));
                        }
                    });
                }
            }
        }
    }
}

