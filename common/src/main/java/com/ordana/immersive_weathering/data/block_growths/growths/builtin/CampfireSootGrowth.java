package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.util.RandomSource;

public class CampfireSootGrowth extends BuiltinBlockGrowth {

    public CampfireSootGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        if (state.getValue(CampfireBlock.LIT)) {
            Random random = level.random;

            int smokeHeight = state.getValue(CampfireBlock.SIGNAL_FIRE) ? 23 : 8;

            FireSootGrowth.spawnSootAboveFire(level, pos, smokeHeight);

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
