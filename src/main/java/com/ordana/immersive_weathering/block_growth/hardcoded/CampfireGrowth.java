package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.block_growth.IBlockGrowth;
import com.ordana.immersive_weathering.common.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Random;
import java.util.stream.Collectors;

public class CampfireGrowth implements IBlockGrowth {

    @Override
    public Iterable<Block> getOwners() {
        return Registry.BLOCK.getTag(BlockTags.CAMPFIRES).get().stream().map(Holder::value).collect(Collectors.toList());
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {

        //TODO: move to soot class
        if (state.getValue(CampfireBlock.LIT)) {
            Random random = level.random;

            int smokeHeight = state.getValue(CampfireBlock.SIGNAL_FIRE) ? 23 : 8;

            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.above();
                BlockState above = level.getBlockState(sootPos.above());
                if (Block.isFaceFull(above.getCollisionShape(level, sootPos.above()), Direction.DOWN)) {
                    if (level.getBlockState(sootPos).isAir()) {
                        level.setBlock(sootPos, ModBlocks.SOOT.get().defaultBlockState().setValue(BlockStateProperties.UP, true), 3);
                    }
                    smokeHeight = i + 1;
                }
            }
            BlockPos sootBlock = pos.above(random.nextInt(smokeHeight) + 1);
            int rand = random.nextInt(4);
            Direction sootDir = Direction.from2DDataValue(rand);
            BlockPos testPos = sootBlock.relative(sootDir);
            BlockState testBlock = level.getBlockState(testPos);

            if (Block.isFaceFull(testBlock.getCollisionShape(level, testPos), sootDir.getOpposite())) {
                BlockState currentState = level.getBlockState(sootBlock);
                if (currentState.is(ModBlocks.SOOT.get())) {
                    level.setBlock(sootBlock, currentState.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(sootDir), true), Block.UPDATE_CLIENTS);
                } else if (currentState.isAir()) {
                    level.setBlock(sootBlock, ModBlocks.SOOT.get().defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(sootDir), true), Block.UPDATE_CLIENTS);
                }
            }
        }
    }
}
