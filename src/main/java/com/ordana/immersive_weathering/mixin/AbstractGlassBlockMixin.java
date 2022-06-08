package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.FrostyGrassBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(AbstractGlassBlock.class)
public class AbstractGlassBlockMixin extends Block {

    public AbstractGlassBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if(ImmersiveWeathering.getConfig().fireAndIceConfig.glassFrosting) {
            if (state.isOf(Blocks.GLASS)) {
                if ((world.isRaining() || world.isNight()) && world.getBiome(pos).isIn(ModTags.ICY)) {
                    world.setBlockState(pos, ModBlocks.FROSTY_GLASS.getDefaultState().with(FrostyGrassBlock.NATURAL, true));
                }
            }
        }
    }
}
