package com.ordana.immersive_weathering.block_growth.growths.builtin;

import com.ordana.immersive_weathering.block_growth.TickSource;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FireSootGrowth extends BuiltinBlockGrowth {

    public FireSootGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        if (level.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            spawnSootAboveFire(level, pos, 6);
        }
    }

    public static void spawnSootAboveFire(ServerLevel level, BlockPos pos, int smokeHeight) {
        BlockPos sootPos = pos;
        for (int i = 0; i < smokeHeight; i++) {
            sootPos = sootPos.above();
            BlockState above = level.getBlockState(sootPos.above());
            if (Block.isFaceFull(above.getCollisionShape(level, sootPos.above()), Direction.DOWN)) {
                if (level.getBlockState(sootPos).isAir()) {
                    level.setBlock(sootPos, ModBlocks.SOOT.get().defaultBlockState().setValue(BlockStateProperties.UP, true), Block.UPDATE_CLIENTS);
                }
                return;
            }
        }
    }
}
