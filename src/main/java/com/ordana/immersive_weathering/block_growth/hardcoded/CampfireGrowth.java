package com.ordana.immersive_weathering.block_growth.hardcoded;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.block_growth.IBlockGrowth;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ConnectingBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.util.Random;
import java.util.stream.Collectors;

public class CampfireGrowth implements IBlockGrowth {

    @Override
    public Iterable<Block> getOwners() {
        return Registry.BLOCK.getEntryList(BlockTags.CAMPFIRES).get().stream().map(RegistryEntry::value).collect(Collectors.toList());
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerWorld world, RegistryEntry<Biome> biome) {
        if(ImmersiveWeathering.getConfig().fireAndIceConfig.campfiresCreateSoot) {
            //TODO: move to soot class
            if (state.contains(CampfireBlock.SIGNAL_FIRE) && state.contains(CampfireBlock.LIT) && state.get(CampfireBlock.LIT)) {

                net.minecraft.util.math.random.Random random = world.getRandom();

                int smokeHeight = state.get(CampfireBlock.SIGNAL_FIRE) ? 23 : 8;

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
                BlockPos sootBlock = pos.up(random.nextInt(smokeHeight) + 1);
                int rand = random.nextInt(4);
                Direction sootDir = Direction.fromHorizontal(rand);
                BlockPos testPos = sootBlock.offset(sootDir);
                BlockState testBlock = world.getBlockState(testPos);

                if (Block.isFaceFullSquare(testBlock.getCollisionShape(world, testPos), sootDir.getOpposite())) {
                    BlockState currentState = world.getBlockState(sootBlock);
                    if (currentState.isOf(ModBlocks.SOOT)) {
                        world.setBlockState(sootBlock, currentState.with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                    } else if (currentState.isAir()) {
                        world.setBlockState(sootBlock, ModBlocks.SOOT.getDefaultState().with(ConnectingBlock.FACING_PROPERTIES.get(sootDir), true), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }
}
