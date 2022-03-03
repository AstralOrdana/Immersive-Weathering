package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.SootBlock;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(FireBlock.class)
public class FireMixin {

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            if (!state.get(FireBlock.UP) && !state.get(FireBlock.NORTH) && !state.get(FireBlock.SOUTH) && !state.get(FireBlock.EAST) && !state.get(FireBlock.WEST)) {
                for (var direction : Direction.values()) {
                    var targetPos = pos.offset(direction);
                    BlockState below = world.getBlockState(targetPos.down());
                    if (BlockPos.streamOutwards(pos, 2, 2, 2)
                            .map(world::getBlockState)
                            .map(BlockState::getBlock)
                            .filter(ModBlocks.SOOT::equals)
                            .toList().size() <= 8) {
                        float f = 0.5f;
                        if (random.nextFloat() > 0.8f) {
                            if (world.getBlockState(targetPos).isAir()) {
                                if (Block.isFaceFullSquare(below.getCollisionShape(world, targetPos.down()), Direction.UP)) {
                                    world.setBlockState(targetPos, ModBlocks.SOOT.getDefaultState().with(Properties.DOWN, true), Block.NOTIFY_LISTENERS);
                                }
                            }
                        }
                    }
                }
            }
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
            BlockState targetBlock = world.getBlockState(pos);
            if (random.nextFloat() < 0.3f) {
                var targetPos = pos.down();
                BlockState neighborState = world.getBlockState(targetPos);
                if (!neighborState.isIn(BlockTags.INFINIBURN_OVERWORLD)) {
                    if (random.nextFloat() > 0.4f) {
                        if (!state.get(FireBlock.UP) && !state.get(FireBlock.NORTH) && !state.get(FireBlock.SOUTH) && !state.get(FireBlock.EAST) && !state.get(FireBlock.WEST)) {
                            world.setBlockState(pos, ModBlocks.SOOT.getDefaultState().with(SootBlock.LIT, true).with(Properties.DOWN, true), Block.NOTIFY_LISTENERS);
                        } else
                            world.setBlockState(pos, ModBlocks.SOOT.getStateWithProperties(targetBlock).with(SootBlock.LIT, true), Block.NOTIFY_LISTENERS);
                    } else if (random.nextFloat() > 0.5f) {
                        world.setBlockState(pos, ModBlocks.ASH_BLOCK.getDefaultState().with(SootBlock.LIT, true), Block.NOTIFY_LISTENERS);
                    } else world.setBlockState(pos, ModBlocks.ASH_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
                }
            }
        }
    }
}