package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.ModTags;
import java.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MossySlabBlock extends MossableSlabBlock implements BonemealableBlock {
    public MossySlabBlock(MossLevel mossLevel, Properties settings) {
        super(mossLevel, settings);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {

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
            var targetPos = pos.relative(direction);
            BlockState targetBlock = world.getBlockState(targetPos);
            float f = 0.5f;
            if (random.nextFloat() > 0.5f) {
                if (world.getBlockState(targetPos).is(ModTags.MOSSABLE)) {
                    CLEANED_BLOCKS.forEach((mossy, clean) -> {
                        if (targetBlock.is(clean)) {
                            world.setBlockAndUpdate(targetPos, mossy.withPropertiesOf(targetBlock));
                        }
                    });
                }
            }
        }
    }
}

