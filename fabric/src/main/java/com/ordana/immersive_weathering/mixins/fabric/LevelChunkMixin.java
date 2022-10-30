package com.ordana.immersive_weathering.mixins.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin {

    //fixing a vanilla bug that causes log spam when a block that can provide a tile doesn't actually provide it (they can do this now)
    @Redirect(method = "promotePendingBlockEntity",
            require = 0,
            at = @At(value = "INVOKE",
                    target = "org/slf4j/Logger.warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    ordinal = 1))
    private void warn(org.slf4j.Logger instance, String s, Object b, Object p) {
        if (b instanceof BlockState state && p instanceof BlockPos pos) {
            if (state.getBlock() instanceof EntityBlock block) {
                if (block.newBlockEntity(pos, state) == null) return;
            }
        }
        instance.warn(s, b, p);
    }


}