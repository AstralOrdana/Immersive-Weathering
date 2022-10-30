package com.ordana.immersive_weathering.mixins;

import com.google.common.collect.ImmutableList;
import com.ordana.immersive_weathering.IWPlatformStuff;
import com.ordana.immersive_weathering.data.fluid_generators.FluidGeneratorsHandler;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModTags;
import com.ordana.immersive_weathering.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin extends Block implements BucketPickup {

    @Shadow
    @Final
    public static ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS;

    @Shadow
    protected abstract void fizz(LevelAccessor p_54701_, BlockPos p_54702_);

    @Shadow
    public abstract FluidState getFluidState(BlockState blockState);

    @Shadow
    @Final
    protected FlowingFluid fluid;

    public LiquidBlockMixin(Properties settings, FlowingFluid fluid) {
        super(settings);
    }

    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    private void shouldSpreadLiquid(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        var fluid = this.getOwnFluid();
        var successPos = FluidGeneratorsHandler.applyGenerators(fluid, POSSIBLE_FLOW_DIRECTIONS, pos, world);
        if(successPos.isPresent()){
            if(fluid.is(FluidTags.LAVA)) {
                this.fizz(world, successPos.get().getFirst());
            }
            cir.setReturnValue(false);
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && world.getBiome(pos).is(ModTags.ICY) && this.getOwnFluid().is(FluidTags.WATER)) {
            var freezing = CommonConfigs.FREEZING_WATER_SEVERITY.get();
            if (!(entity instanceof LivingEntity) || EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, (LivingEntity) entity) > 0 || ((LivingEntity) entity).hasEffect(MobEffects.CONDUIT_POWER) || entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
                return;
            }
            if (entity.isInWater()) WeatheringHelper.applyFreezing(entity, freezing, true);
        }
    }

    @Unique
    public FlowingFluid getOwnFluid() {
        var f = IWPlatformStuff.getFlowingFluid((LiquidBlock) (Object) this);
        if (f == null)
            return this.fluid;
        return f;
    }

}
