package com.ordana.immersive_weathering.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;


@Mixin(FireBlock.class)
public abstract class FireMixin extends BaseFireBlock{

    @Unique
    private BlockState bs;

    public FireMixin(Properties settings, float damage) {
        super(settings, damage);
    }


    //TODO: re add

    /*
    @Inject(method = "trySpreadingFire",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z",
                    shift = At.Shift.AFTER))
    private void afterRemoveBlock(Level world, BlockPos pos, int spreadFactor, Random rand, int currentAge, CallbackInfo ci) {
        if(ImmersiveWeathering1.getConfig().fireAndIceConfig.fireCharsWood) {
            WeatheringHelper.tryCharBlock(world, pos, bs);
        }
    }

    @Inject(method = "trySpreadingFire",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private void beforeRemoveBlock(Level world, BlockPos pos, int spreadFactor, Random rand, int currentAge, CallbackInfo ci) {
        bs = world.getBlockState(pos);
    }*/
}