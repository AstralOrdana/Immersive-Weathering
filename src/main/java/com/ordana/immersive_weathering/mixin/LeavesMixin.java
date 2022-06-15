package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.registry.ModParticles;
import com.ordana.immersive_weathering.registry.ModTags;
import com.ordana.immersive_weathering.registry.blocks.LeafPileBlock;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesMixin extends Block implements Fertilizable {

    public LeavesMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if(ImmersiveWeathering.getConfig().leavesConfig.leafDecayPiles) {
            if (state.contains(LeavesBlock.PERSISTENT) && !state.get(LeavesBlock.PERSISTENT) && state.contains(LeavesBlock.DISTANCE) && state.get(LeavesBlock.DISTANCE) == 7 && state.isIn(ModTags.VANILLA_LEAVES)) {
                var leafPile = WeatheringHelper.getFallenLeafPile(state).orElse(null);
                if (leafPile == null) return;
                BlockState baseLeaf = leafPile.getDefaultState().with(LeafPileBlock.LAYERS, 0);
                var leafParticle = WeatheringHelper.getFallenLeafParticle(state).orElse(null);
                if(ImmersiveWeathering.getConfig().leavesConfig.leafDecayParticles) {
                    world.spawnParticles(leafParticle, (double) pos.getX() + 0.5D,
                            (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 10,
                            0.5D, 0.5D, 0.5D, 0.0D);
                }
                if(ImmersiveWeathering.getConfig().leavesConfig.leafDecaySound) {
                    world.playSound(null, pos, SoundEvents.BLOCK_AZALEA_LEAVES_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
                }
                if (world.random.nextFloat() < 0.3f) {
                    world.setBlockState(pos, baseLeaf.with(LeafPileBlock.LAYERS, MathHelper.nextBetween(random, 1, 6)), 2);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "randomDisplayTick", at = @At("HEAD"))
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        var leafParticle = WeatheringHelper.getFallenLeafParticle(state).orElse(null);
        if (leafParticle == null) return;
        if(ImmersiveWeathering.getConfig().leavesConfig.fallingLeafParticles && state.isIn(ModTags.VANILLA_LEAVES)) {
            if (random.nextInt(32) == 0 && !world.getBlockState(pos.down()).isSolidBlock(world, pos)) {
                if (!(world.getBlockState(pos.down()).getBlock() instanceof LeavesBlock) && state.isIn(ModTags.VANILLA_LEAVES)) {
                    ParticleUtil.spawnParticle(world, pos, leafParticle, UniformIntProvider.create(0, 1));
                }
            }
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.isOf(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.isOf(Blocks.FLOWERING_AZALEA_LEAVES);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        for (var direction : Direction.values()) {
            if (random.nextFloat() > 0.5f) {
                var targetPos = pos.offset(direction);
                BlockState targetBlock = world.getBlockState(targetPos);
                WeatheringHelper.getAzaleaGrowth(targetBlock).ifPresent(s ->
                        world.setBlockState(targetPos, s)
                );
            }
        }
    }
}

