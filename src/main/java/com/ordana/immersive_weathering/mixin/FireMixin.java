package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(FireBlock.class)
public class FireMixin extends AbstractFireBlock{
    private final Object2IntMap<Block> burnChances = new Object2IntOpenHashMap();

    public FireMixin(Settings settings, float damage) {
        super(settings, damage);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int smokeHeight = 6;

        BlockPos sootPos = pos;
        for (int i = 0; i < smokeHeight; i++) {
            sootPos = sootPos.up();
            BlockState above = world.getBlockState(sootPos.up());
            if (Block.isFaceFullSquare(above.getCollisionShape(world, sootPos.up()), Direction.DOWN)) {
                if (world.getBlockState(sootPos).isAir()) {
                    world.setBlockState(sootPos, ModBlocks.SOOT.getDefaultState().with(Properties.UP, true), Block.NOTIFY_LISTENERS);
                }
                smokeHeight = i+1;
            }
        }
    }

    @Override
    public boolean isFlammable(BlockState state) {
        return this.getBurnChance(state) > 0;
    }

    private int getBurnChance(BlockState state) {
        return state.contains(Properties.WATERLOGGED) && (Boolean)state.get(Properties.WATERLOGGED) ? 0 : this.burnChances.getInt(state.getBlock());
    }
}