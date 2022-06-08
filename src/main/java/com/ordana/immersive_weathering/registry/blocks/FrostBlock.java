package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.Random;

public class FrostBlock extends MultifaceGrowthBlock implements Frostable {
    public FrostBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(NATURAL, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        for (Direction direction : DIRECTIONS) {
            if (!this.canHaveDirection(direction)) continue;
            builder.add(MultifaceGrowthBlock.getProperty(direction));
        }
        builder.add(NATURAL);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(ModItems.FROST) || super.canReplace(state, context);
    }

    @Override
    public LichenGrower getGrower() {
        return null;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
        if (state.get(NATURAL)) {
            if (world.getDimension().ultrawarm() || (!world.isRaining() && world.isDay()) || (world.getLightLevel(LightType.BLOCK, pos) > 7 - state.getOpacity(world, pos))) {
                world.removeBlock(pos, false);
            }
        }
    }
}
