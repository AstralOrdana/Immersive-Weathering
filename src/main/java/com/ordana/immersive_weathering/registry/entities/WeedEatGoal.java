package com.ordana.immersive_weathering.registry.entities;

import com.ordana.immersive_weathering.registry.blocks.WeedsBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class WeedEatGoal extends MoveToTargetPosGoal {
    private final RabbitEntity rabbit;
    private boolean wantsWeeds;
    private boolean hasTarget;
    int moreWeedsTicks;

    public WeedEatGoal(AnimalEntity rabbit) {
        super(rabbit, 0.699999988079071D, 16);
        this.rabbit = (RabbitEntity) rabbit;
    }

    public boolean canStart() {
        if (this.cooldown <= 0) {
            if (!this.rabbit.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return false;
            }

            this.hasTarget = false;
            this.wantsWeeds = true;
        }

        return super.canStart();
    }

    public boolean shouldContinue() {
        return this.hasTarget && super.shouldContinue();
    }

    public void tick() {
        super.tick();
        this.rabbit.getLookControl().lookAt((double)this.targetPos.getX() + 0.5D, (double)(this.targetPos.getY() + 1), (double)this.targetPos.getZ() + 0.5D, 10.0F, (float)this.rabbit.getMaxLookPitchChange());
        if (this.hasReached()) {
            World world = this.rabbit.world;
            BlockPos blockPos = this.targetPos.up();
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (this.hasTarget && block instanceof WeedsBlock) {
                int i = blockState.get(WeedsBlock.AGE);
                if (i == 0) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
                    world.breakBlock(blockPos, true, this.rabbit);
                } else {
                    world.breakBlock(blockPos, true, this.rabbit);
                    world.syncWorldEvent(2001, blockPos, Block.getRawIdFromState(blockState));
                }

                this.moreWeedsTicks = 40;
            }

            this.hasTarget = false;
            this.cooldown = 10;
        }

    }

    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isOf(Blocks.FARMLAND) && this.wantsWeeds && !this.hasTarget) {
            blockState = world.getBlockState(pos.up());
            if (blockState.getBlock() instanceof WeedsBlock && ((WeedsBlock)blockState.getBlock()).isMature(blockState)) {
                this.hasTarget = true;
                return true;
            }
        }

        return false;
    }
}