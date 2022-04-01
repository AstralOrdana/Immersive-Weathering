package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.SootBlock;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FireBlock.class)
public abstract class FireMixin {


    @Shadow
    public abstract boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos);

    @Inject(method = "scheduledTick", at = @At("HEAD"))
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {

            //if it's upwards only (normal fire) place soot next to it
            if (!state.get(FireBlock.UP) && !state.get(FireBlock.NORTH) && !state.get(FireBlock.SOUTH) && !state.get(FireBlock.EAST) && !state.get(FireBlock.WEST)) {
                if (random.nextFloat() > 0.8f) {
                    if (!WeatheringHelper.hasEnoughBlocksAround(pos, 2, world, b -> b.isOf(ModBlocks.SOOT), 7)) {

                        Direction dir = Direction.Type.HORIZONTAL.random(random);
                        var targetPos = pos.offset(dir);
                        if (world.getBlockState(targetPos).isAir()) {
                            BlockPos belowPos = targetPos.down();
                            BlockState below = world.getBlockState(belowPos); //Block.isFaceFull(below.getCollisionShape(world, targetPos.below()), Direction.UP)
                            if (below.isSideSolidFullSquare(world, belowPos, Direction.UP)) {
                                world.setBlockState(targetPos, ModBlocks.SOOT.getDefaultState()
                                        .with(Properties.DOWN, true), Block.NOTIFY_LISTENERS);
                            }
                        }
                    }
                }
            }
            //TODO: see if this can be optimized
            int smokeHeight = 6;
            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.up();
                BlockState above = world.getBlockState(sootPos.up());
                if (Block.isFaceFullSquare(above.getCollisionShape(world, sootPos.up()), Direction.DOWN)) {
                    if (world.getBlockState(sootPos).isAir()) {
                        world.setBlockState(sootPos, ModBlocks.SOOT.getDefaultState().with(Properties.UP, true), 3);
                    }
                    smokeHeight = i + 1;
                }
            }
            BlockState targetBlock = world.getBlockState(pos);
            if (random.nextFloat() < 0.3f) {
                var targetPos = pos.down();
                BlockState neighborState = world.getBlockState(targetPos);
                if ((world.getRegistryKey() == World.OVERWORLD) && (!neighborState.isIn(BlockTags.INFINIBURN_OVERWORLD)) || ((world.getRegistryKey() == World.END) && (!neighborState.isIn(BlockTags.INFINIBURN_END))) || ((world.getRegistryKey() == World.NETHER) && (!neighborState.isIn(BlockTags.INFINIBURN_NETHER)))) {
                    if (random.nextFloat() > 0.4f) {
                        BlockState placementState = ModBlocks.SOOT.getDefaultState().with(SootBlock.LIT, true).with(Properties.DOWN, true);
                        BlockState placementState2 = ModBlocks.SOOT.getStateWithProperties(targetBlock).with(SootBlock.LIT, true);
                        if (!state.get(FireBlock.UP) && !state.get(FireBlock.NORTH) && !state.get(FireBlock.SOUTH) && !state.get(FireBlock.EAST) && !state.get(FireBlock.WEST) && placementState.canPlaceAt(world, pos)) {
                            world.setBlockState(pos, ModBlocks.SOOT.getDefaultState().with(SootBlock.LIT, true).with(Properties.DOWN, true), Block.NOTIFY_LISTENERS);
                        }
                        else if (placementState2.canPlaceAt(world, pos)) {
                            world.setBlockState(pos, ModBlocks.SOOT.getStateWithProperties(targetBlock).with(SootBlock.LIT, true), Block.NOTIFY_LISTENERS);
                        }
                        else if (random.nextFloat() > 0.5f) {
                            world.setBlockState(pos, ModBlocks.ASH_BLOCK.getDefaultState().with(SootBlock.LIT, true), Block.NOTIFY_LISTENERS);
                        }
                        else world.setBlockState(pos, ModBlocks.ASH_BLOCK.getDefaultState(), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }
}