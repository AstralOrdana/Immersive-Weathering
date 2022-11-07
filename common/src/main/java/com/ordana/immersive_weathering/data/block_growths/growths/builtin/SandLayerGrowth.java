package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.blocks.LayerBlock;
import com.ordana.immersive_weathering.blocks.ModBlockProperties;
import com.ordana.immersive_weathering.blocks.sandy.Sandy;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SandLayerGrowth extends BuiltinBlockGrowth {
    protected SandLayerGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources) {
        super(name, owners, sources);
    }

    int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    IntegerProperty getAgeProperty() {
        return LayerBlock.LAYERS_8;
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level, Holder<Biome> biome) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        var sandyBlock = Sandy.getSandy(belowState);
        if (state.getValue(LayerBlock.LAYERS_8) > 1 && sandyBlock.isPresent()) {
            RandomSource random = level.random;
            int rand = random.nextInt(2);
            int rand2 = random.nextInt(5);
            level.setBlockAndUpdate(belowPos, sandyBlock.get().setValue(Sandy.SANDINESS, rand).setValue(Sandy.SAND_AGE, rand2));
            level.setBlockAndUpdate(pos, state.setValue(LayerBlock.LAYERS_8, getAge(state) - 1));
        }
    }
}
