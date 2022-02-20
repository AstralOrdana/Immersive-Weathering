package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Random;

@Mixin(LeavesBlock.class)
class LeavesMixin {

    private static final HashMap<Block, Block> LEAVES = new HashMap<>();

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {

        LEAVES.put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE);
        LEAVES.put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE);
        LEAVES.put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE);
        LEAVES.put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE);
        LEAVES.put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE);
        LEAVES.put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE);
        LEAVES.put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE);
        LEAVES.put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE);

        if (!(Boolean)state.get(LeavesBlock.PERSISTENT)) {
            int leafHeight = -16;
            BlockPos leafPos = pos;
            for (int i = 0; i > leafHeight; i--) {
                leafPos = leafPos.down();
                BlockState below = world.getBlockState(leafPos.down());
                if (Block.isFaceFullSquare(below.getCollisionShape(world, leafPos.down()), Direction.UP)) {
                    if (world.getBlockState(leafPos).isAir()) {
                        if (BlockPos.streamOutwards(pos, 2, 2, 2)
                                .map(world::getBlockState)
                                .map(BlockState::getBlock)
                                .filter(ImmersiveWeathering.LEAF_PILES::contains)
                                .toList().size() <= 8) {
                            for (Direction direction : Direction.values()) {
                                var targetPos = pos.offset(direction);
                                if (world.getBlockState(targetPos).isIn(ImmersiveWeathering.RAW_LOGS)) {
                                    BlockPos finalLeafPos = leafPos;
                                    LEAVES.forEach((leaves, pile) -> world.setBlockState(finalLeafPos, pile.getDefaultState().with(LeafPileBlock.LAYERS, 2)));
                                }
                                else if (world.getBlockState(pos.offset(direction)).isIn(ImmersiveWeathering.LEAF_PILES)) {
                                    BlockPos finalLeafPos = leafPos;
                                    LEAVES.forEach((leaves, pile) -> world.setBlockState(finalLeafPos, pile.getDefaultState()));
                                }
                            }
                        }
                    }
                    leafHeight = i - 1;
                }
            }
        }
    }
}
