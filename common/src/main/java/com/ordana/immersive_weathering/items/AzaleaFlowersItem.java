package com.ordana.immersive_weathering.items;


import com.ordana.immersive_weathering.configs.CommonConfigs;
import com.ordana.immersive_weathering.reg.ModParticles;
import com.ordana.immersive_weathering.WeatheringHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class AzaleaFlowersItem extends Item {
    public AzaleaFlowersItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(CommonConfigs.AZALEA_SHEARING.get()) {
            BlockPos pos = context.getClickedPos();
            Level level = context.getLevel();
            Player player = context.getPlayer();
            BlockState state = level.getBlockState(pos);
            var flowery = WeatheringHelper.getAzaleaGrowth(state).orElse(null);
            if (flowery != null) {
                level.playSound(player, pos, SoundEvents.FLOWERING_AZALEA_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ModParticles.AZALEA_FLOWER.get(), UniformInt.of(3, 5));
                if (player != null && !player.getAbilities().instabuild) context.getItemInHand().shrink(1);
                level.setBlockAndUpdate(pos, flowery);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }
}
