package com.ordana.immersive_weathering.mixins.fabric;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FarmBlock.class)
public abstract class FarmlandBlockMixin extends Block {
    public FarmlandBlockMixin(Properties settings) {
        super(settings);
    }

    /*
    //TODO: use forge event for forge
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;fallOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;F)V"), method="fallOn", cancellable = true)
    public void checkFeatherFallingOnLanding(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo info) {
        if (entity != null) {
            if(CommonConfigs.FEATHER_FALLING_FARMERS.get()) {
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, (LivingEntity) entity) > 0) {
                    super.fallOn(level, state, pos, entity, fallDistance);
                    info.cancel();
                }
            }
        }
    }
     */
}
