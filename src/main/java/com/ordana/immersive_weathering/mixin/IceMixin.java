package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModDamageSource;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.ThinIceBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(IceBlock.class)
abstract public class IceMixin extends Block {

    @Shadow protected abstract void melt(BlockState state, World world, BlockPos pos);

    public IceMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        int rand = random.nextInt(4);
        Direction direction = Direction.fromHorizontal(rand);
        if (world.getBlockState(pos.up()).isOf(Blocks.AIR) && (world.isRaining() || world.isNight()) && world.getBiome(pos).isIn(ModTags.ICY) && (world.getLightLevel(LightType.BLOCK, pos) < 7 - state.getOpacity(world, pos))) {
            if (world.getFluidState(pos.offset(direction)).isOf(Fluids.WATER)) {
                world.setBlockState(pos.offset(direction), ModBlocks.THIN_ICE.getDefaultState().with(ThinIceBlock.WATERLOGGED, true));
            }
        }
    }

    //TODO: is day is broken on client side
    private boolean canMelt(BlockState state, World level, BlockPos pos) {
        if(ImmersiveWeathering.getConfig().fireAndIceConfig.naturalIceMelt) {
            return level.getDimension().isUltrawarm() || (level.getBiome(pos).value().isHot(pos) && level.isDay()) ||
                    (level.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(level, pos));

        }
        return false;
    }

    @Override
    public void randomDisplayTick(BlockState state, World level, BlockPos pos, Random random) {
        if (random.nextInt(25) == 1) {
            if (this.canMelt(state, level, pos) || level.isDay()) {

                BlockPos blockpos = pos.down();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!blockstate.isOpaque() || !blockstate.isSideSolidFullSquare(level, blockpos, Direction.UP)) {
                    double d0 = (double) pos.getX() + random.nextDouble();
                    double d1 = (double) pos.getY() - 0.05D;
                    double d2 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }
}
