package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements Fertilizable {

    public LeavesMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(ImmersiveWeathering.getConfig().leavesConfig.leafDecayPiles) {
            if (!state.get(LeavesBlock.PERSISTENT) && state.get(LeavesBlock.DISTANCE) == 7) {
                var leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
                BlockState baseLeaf = leafPile.getDefaultState().with(LeafPileBlock.LAYERS, 0);
                if (world.random.nextFloat() < 0.3f) {
                    world.setBlockState(pos, baseLeaf.with(LeafPileBlock.LAYERS, MathHelper.nextBetween(random, 1, 6)), 2);
                } else {
                    LeavesBlock.dropStacks(state, world, pos);
                    world.removeBlock(pos, false);
                }
            }
        }
        else if (!state.get(LeavesBlock.PERSISTENT) && state.get(LeavesBlock.DISTANCE) == 7) {
            LeavesBlock.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.isOf(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.isOf(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.offset(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockState(targetPos, s)
                );
            }
        }
    }
}

