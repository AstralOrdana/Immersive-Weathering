package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(CampfireBlock.class)
public abstract class CampfireMixin extends Block {

    protected CampfireMixin(Properties settings) {
        super(settings);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {

        //TODO: move to soot class
        if (state.getValue(CampfireBlock.LIT)) {

            int smokeHeight = state.getValue(CampfireBlock.SIGNAL_FIRE) ? 23 : 8;

            BlockPos sootPos = pos;
            for (int i = 0; i < smokeHeight; i++) {
                sootPos = sootPos.above();
                BlockState above = world.getBlockState(sootPos.above());
                if (Block.isFaceFull(above.getCollisionShape(world, sootPos.above()), Direction.DOWN)) {
                    if (world.getBlockState(sootPos).isAir()) {
                        world.setBlock(sootPos, ModBlocks.SOOT.get().defaultBlockState().setValue(BlockStateProperties.UP, true), 3);
                    }
                    smokeHeight = i + 1;
                }
            }
            BlockPos sootBlock = pos.above(random.nextInt(smokeHeight) + 1);
            int rand = random.nextInt(4);
            Direction sootDir = Direction.from2DDataValue(rand);
            BlockPos testPos = sootBlock.relative(sootDir);
            BlockState testBlock = world.getBlockState(testPos);

            if (Block.isFaceFull(testBlock.getCollisionShape(world, testPos), sootDir.getOpposite())) {
                BlockState currentState = world.getBlockState(sootBlock);
                if (currentState.is(ModBlocks.SOOT.get())) {
                    world.setBlock(sootBlock, currentState.setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(sootDir), true), Block.UPDATE_CLIENTS);
                } else if (currentState.isAir()) {
                    world.setBlock(sootBlock, ModBlocks.SOOT.get().defaultBlockState().setValue(PipeBlock.PROPERTY_BY_DIRECTION.get(sootDir), true), Block.UPDATE_CLIENTS);
                }
            }
        }
    }

}