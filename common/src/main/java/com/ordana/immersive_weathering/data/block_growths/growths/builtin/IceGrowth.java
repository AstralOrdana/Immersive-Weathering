package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.blocks.IcicleBlock;
import com.ordana.immersive_weathering.data.block_growths.TickSource;
import com.ordana.immersive_weathering.mixins.accessors.IceInvoker;
import com.ordana.immersive_weathering.reg.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class IceGrowth extends BuiltinBlockGrowth {


    public IceGrowth(String name, @Nullable HolderSet<Block> owners, List<TickSource> sources, float chance) {
        super(name, owners, sources, chance);
    }

    @Override
    public void tryGrowing(BlockPos pos, BlockState state, ServerLevel level,  Supplier<Holder<Biome>> b) {
        if (!(growthChance == 1 || level.random.nextFloat() < growthChance)) return;

        RandomSource random = level.random;

        //move to json??
        if (random.nextFloat() < 0.003f) {
            BlockPos icePos = pos.below();

            if (level.getBlockState(icePos).is(Blocks.AIR)) {
                Biome biome = b.get().value();
                //to form we need hot weather in a cold biome or water above & cold biome
                if (biome.coldEnoughToSnow(pos)) {
                    if (level.getFluidState(pos.above()).is(FluidTags.WATER) || (level.isDay() && !level.isRaining() && !level.isThundering())) {
                        level.setBlockAndUpdate(icePos, ModBlocks.ICICLE.get().defaultBlockState()
                                .setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.DOWN)
                                .setValue(IcicleBlock.THICKNESS, DripstoneThickness.TIP));
                    }
                }
            }
        }

        //melt ice
        if (state.getBlock() instanceof IceInvoker ice) {
            if (level.dimensionType().ultraWarm()) {
                level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.3F, 2.9F + (random.nextFloat() - random.nextFloat()) * 0.6F);

                float i = pos.getX() + 0.5f;
                float j = pos.getY() + 0.5f;
                float k = pos.getZ() + 0.5f;
                level.sendParticles(ParticleTypes.LARGE_SMOKE, i, j, k, 12, 0.2D, 0.2D, 0.2D, 0);
                ice.invokeMelt(state, level, pos);
            } else if (b.get().value().shouldSnowGolemBurn(pos) && level.isDay()) {
                ice.invokeMelt(state, level, pos);
            }
        }
    }



}
