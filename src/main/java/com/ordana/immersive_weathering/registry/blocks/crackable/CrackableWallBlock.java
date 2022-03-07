package com.ordana.immersive_weathering.registry.blocks.crackable;

import com.ordana.immersive_weathering.registry.ModTags;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class CrackableWallBlock extends WallBlock {

    public CrackableWallBlock(Settings settings) {
        super(settings);
    }

}