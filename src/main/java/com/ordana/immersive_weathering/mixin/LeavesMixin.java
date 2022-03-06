package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements BonemealableBlock {

    public LeavesMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {

        //Drastically reduced this chance to help lag
        if (random.nextFloat() > 0.1f) {
            if (!state.getValue(LeavesBlock.PERSISTENT)) {

                int maxFallenLeavesReach = 16;
                var leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
                if (leafPile != null) {
                    for (int i = 0; i < maxFallenLeavesReach; i++) {

                        pos = pos.below();
                        BlockState below = world.getBlockState(pos);
                        if (!below.isAir()) {
                            //if we find a non-air block we check if its upper face is sturdy. Given previous iteration if we are not on the first cycle blocks above must be air
                            if (i != 0 && below.isFaceSturdy(world, pos, Direction.UP)) {
                                BlockPos targetPos = pos.above();

                                if (!WeatheringHelper.hasEnoughBlocksAround(targetPos, 2, 1, 2,
                                        world, b -> b.getBlock() instanceof LeafPileBlock, 7)) {
                                    int pileHeight = 0;
                                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                                        BlockState neighbor = world.getBlockState(targetPos.relative(direction));
                                        if (neighbor.is(BlockTags.LOGS)) { //TODO: replace with mod tag
                                            pileHeight = 2;
                                            break;
                                        } else if (neighbor.getBlock() instanceof LeafPileBlock) {
                                            pileHeight = 1;
                                        }
                                    }
                                    if (pileHeight > 0) {
                                        world.setBlock(targetPos, leafPile.defaultBlockState()
                                                .setValue(LeafPileBlock.LAYERS, pileHeight),2);
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        return state.is(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.relative(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockAndUpdate(targetPos, s)
                );
            }
        }
    }
}
