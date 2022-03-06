package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Random;

@Mixin(LeavesBlock.class)
class LeavesMixin extends Block implements BonemealableBlock {

    private static final HashMap<Block, Block> LEAVES = new HashMap<>();

    public LeavesMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random, CallbackInfo ci) {

        LEAVES.put(Blocks.OAK_LEAVES, ModBlocks.OAK_LEAF_PILE);
        LEAVES.put(Blocks.SPRUCE_LEAVES, ModBlocks.SPRUCE_LEAF_PILE);
        LEAVES.put(Blocks.BIRCH_LEAVES, ModBlocks.BIRCH_LEAF_PILE);
        LEAVES.put(Blocks.JUNGLE_LEAVES, ModBlocks.JUNGLE_LEAF_PILE);
        LEAVES.put(Blocks.ACACIA_LEAVES, ModBlocks.ACACIA_LEAF_PILE);
        LEAVES.put(Blocks.DARK_OAK_LEAVES, ModBlocks.DARK_OAK_LEAF_PILE);
        LEAVES.put(Blocks.AZALEA_LEAVES, ModBlocks.AZALEA_LEAF_PILE);
        LEAVES.put(Blocks.FLOWERING_AZALEA_LEAVES, ModBlocks.FLOWERING_AZALEA_LEAF_PILE);
        if (random.nextFloat() > 0.25f) {
            if (!(Boolean) state.getValue(LeavesBlock.PERSISTENT)) {
                int leafHeight = -16;
                BlockPos leafPos = pos;
                BlockState leafState = world.getBlockState(pos);
                for (int i = 0; i > leafHeight; i--) {
                    leafPos = leafPos.below();
                    BlockState below = world.getBlockState(leafPos.below());
                    if (Block.isFaceFull(below.getCollisionShape(world, leafPos.below()), Direction.UP)) {
                        if (world.getBlockState(leafPos).isAir()) {
                            if (BlockPos.withinManhattanStream(leafPos, 2, 2, 2)
                                    .map(world::getBlockState)
                                    .filter(b->b.is(ModTags.LEAF_PILES))
                                    .toList().size() <= 7) {
                                for (Direction direction : Direction.values()) {
                                    if (world.getBlockState(leafPos.relative(direction)).is(ModTags.RAW_LOGS)) {
                                        BlockPos finalLeafPos = leafPos;
                                        LEAVES.forEach((leaves, pile) -> {
                                            if (leafState.is(leaves)) {
                                                world.setBlockAndUpdate(finalLeafPos, pile.defaultBlockState().setValue(LeafPileBlock.LAYERS, 2));
                                            }
                                        });
                                    } else if (world.getBlockState(leafPos.relative(direction)).is(ModTags.LEAF_PILES)) {
                                        BlockPos finalLeafPos = leafPos;
                                        LEAVES.forEach((leaves, pile) -> {
                                            if (leafState.is(leaves)) {
                                                world.setBlockAndUpdate(finalLeafPos, pile.defaultBlockState());
                                            }
                                        });
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

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        if(state.is(Blocks.FLOWERING_AZALEA_LEAVES)) {
            return true;
        }
        else return false;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random random, BlockPos pos, BlockState state) {
        if(state.is(Blocks.FLOWERING_AZALEA_LEAVES)) {
            return true;
        }
        else return false;
    }

    private static final HashMap<Block, Block> FLOWERY_BLOCKS = new HashMap<>();

    @Override
    public void performBonemeal(ServerLevel world, Random random, BlockPos pos, BlockState state) {

        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA);
        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES);
        FLOWERY_BLOCKS.put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, ModBlocks.AZALEA_LEAF_PILE);

        for (var direction : Direction.values()) {
            var targetPos = pos.relative(direction);
            BlockState targetBlock = world.getBlockState(targetPos);
            float f = 0.5f;
            if (random.nextFloat() > 0.5f) {
                if (world.getBlockState(targetPos).is(ModTags.FLOWERABLE)) {
                    FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                        if (targetBlock.is(shorn)) {
                            world.setBlockAndUpdate(targetPos, flowery.withPropertiesOf(targetBlock));
                        }
                    });
                }
            }
        }
    }
}
