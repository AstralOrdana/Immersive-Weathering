package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Random;

public class CrackedWallBlock extends WallBlock {
    public CrackedWallBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    private static final HashMap<Block, Block> CRACKED_BLOCKS = new HashMap<>();

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random){

        CRACKED_BLOCKS.put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);

        CRACKED_BLOCKS.put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB);

        CRACKED_BLOCKS.put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS);

        CRACKED_BLOCKS.put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL);
        CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL);

        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.offset(direction);
            BlockState targetBlock = world.getBlockState(targetPos);
            if (BlockPos.streamOutwards(pos, 1, 1, 1)
                    .map(world::getBlockState)
                    .filter(b->b.isIn(ModTags.CRACKED))
                    .toList().size() < 5) {
                if (random.nextFloat() < 0.5F) {
                    CRACKED_BLOCKS.forEach((solid, cracked) -> {
                        if (targetBlock.isOf(solid)) {
                            world.setBlockState(targetPos, cracked.getStateWithProperties(targetBlock));
                        }
                    });
                }
            }
        }
    }
}