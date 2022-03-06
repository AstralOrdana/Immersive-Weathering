package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.rustable.SootBlock;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FireBlock.class)
public class FireMixin {

    @Inject(method = "scheduledTick", at = @At("HEAD"), cancellable = true)
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
            if (!state.getValue(FireBlock.UP) && !state.getValue(FireBlock.NORTH) && !state.getValue(FireBlock.SOUTH) && !state.getValue(FireBlock.EAST) && !state.getValue(FireBlock.WEST)) {
                for (var direction : Direction.values()) {
                    var targetPos = pos.relative(direction);
                    BlockState below = world.getBlockState(targetPos.below());
                    if (BlockPos.withinManhattanStream(pos, 2, 2, 2)
                            .map(world::getBlockState)
                            .map(BlockState::getBlock)
                            .filter(ModBlocks.SOOT::equals)
                            .toList().size() <= 8) {
                        float f = 0.5f;
                        if (random.nextFloat() > 0.8f) {
                            if (world.getBlockState(targetPos).isAir()) {
                                if (Block.isFaceFull(below.getCollisionShape(world, targetPos.below()), Direction.UP)) {
                                    world.setBlock(targetPos, ModBlocks.SOOT.defaultBlockState().setValue(BlockStateProperties.DOWN, true), Block.UPDATE_CLIENTS);
                                }
                            }
                        }
                    }
                }
            }
            int smokeHeight = 6;
            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.above();
                BlockState above = world.getBlockState(sootPos.above());
                if (Block.isFaceFull(above.getCollisionShape(world, sootPos.above()), Direction.DOWN)) {
                    if (world.getBlockState(sootPos).isAir()) {
                        world.setBlock(sootPos, ModBlocks.SOOT.defaultBlockState().setValue(BlockStateProperties.UP, true), Block.UPDATE_CLIENTS);
                    }
                    smokeHeight = i+1;
                }
            }
            BlockState targetBlock = world.getBlockState(pos);
            if (random.nextFloat() < 0.3f) {
                var targetPos = pos.below();
                BlockState neighborState = world.getBlockState(targetPos);
                if (!neighborState.is(BlockTags.INFINIBURN_OVERWORLD)) {
                    if (random.nextFloat() > 0.4f) {
                        if (!state.getValue(FireBlock.UP) && !state.getValue(FireBlock.NORTH) && !state.getValue(FireBlock.SOUTH) && !state.getValue(FireBlock.EAST) && !state.getValue(FireBlock.WEST)) {
                            world.setBlock(pos, ModBlocks.SOOT.defaultBlockState().setValue(SootBlock.LIT, true).setValue(BlockStateProperties.DOWN, true), Block.UPDATE_CLIENTS);
                        } else
                            world.setBlock(pos, ModBlocks.SOOT.withPropertiesOf(targetBlock).setValue(SootBlock.LIT, true), Block.UPDATE_CLIENTS);
                    } else if (random.nextFloat() > 0.5f) {
                        world.setBlock(pos, ModBlocks.ASH_BLOCK.defaultBlockState().setValue(SootBlock.LIT, true), Block.UPDATE_CLIENTS);
                    } else world.setBlock(pos, ModBlocks.ASH_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
            }
        }
    }
}