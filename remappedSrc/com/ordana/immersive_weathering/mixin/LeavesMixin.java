package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Random;

@Mixin(LeavesBlock.class)
class LeavesMixin extends Block implements Fertilizable {

    private static final HashMap<Block, Block> LEAVES = new HashMap<>();

    public LeavesMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

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
        if (random.nextFloat() > 0.25f) {
            if (!(Boolean) state.get(LeavesBlock.PERSISTENT)) {
                int leafHeight = -16;
                BlockPos leafPos = pos;
                BlockState leafState = world.getBlockState(pos);
                for (int i = 0; i > leafHeight; i--) {
                    leafPos = leafPos.down();
                    BlockState below = world.getBlockState(leafPos.down());
                    if (Block.isFaceFullSquare(below.getCollisionShape(world, leafPos.down()), Direction.UP)) {
                        if (world.getBlockState(leafPos).isAir()) {
                            if (BlockPos.streamOutwards(leafPos, 2, 2, 2)
                                    .map(world::getBlockState)
                                    .map(BlockState::getBlock)
                                    .filter(ModTags.LEAF_PILES::contains)
                                    .toList().size() <= 7) {
                                for (Direction direction : Direction.values()) {
                                    if (world.getBlockState(leafPos.offset(direction)).isIn(ModTags.RAW_LOGS)) {
                                        BlockPos finalLeafPos = leafPos;
                                        LEAVES.forEach((leaves, pile) -> {
                                            if (leafState.isOf(leaves)) {
                                                world.setBlockState(finalLeafPos, pile.getDefaultState().with(LeafPileBlock.LAYERS, 2));
                                            }
                                        });
                                    } else if (world.getBlockState(leafPos.offset(direction)).isIn(ModTags.LEAF_PILES)) {
                                        BlockPos finalLeafPos = leafPos;
                                        LEAVES.forEach((leaves, pile) -> {
                                            if (leafState.isOf(leaves)) {
                                                world.setBlockState(finalLeafPos, pile.getDefaultState());
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
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        if(state.isOf(Blocks.FLOWERING_AZALEA_LEAVES)) {
            return true;
        }
        else return false;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        if(state.isOf(Blocks.FLOWERING_AZALEA_LEAVES)) {
            return true;
        }
        else return false;
    }

    private static final HashMap<Block, Block> FLOWERY_BLOCKS = new HashMap<>();

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {

        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA);
        FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES);
        FLOWERY_BLOCKS.put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, ModBlocks.AZALEA_LEAF_PILE);

        for (var direction : Direction.values()) {
            var targetPos = pos.offset(direction);
            BlockState targetBlock = world.getBlockState(targetPos);
            float f = 0.5f;
            if (random.nextFloat() > 0.5f) {
                if (world.getBlockState(targetPos).isIn(ModTags.FLOWERABLE)) {
                    FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                        if (targetBlock.isOf(shorn)) {
                            world.setBlockState(targetPos, flowery.getStateWithProperties(targetBlock));
                        }
                    });
                }
            }
        }
    }
}
