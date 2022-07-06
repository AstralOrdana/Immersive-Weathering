package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.blocks.frostable.Frosty;
import com.ordana.immersive_weathering.configs.CommonConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FrostItem extends BlockItem {
    public FrostItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        var frosty = Frosty.getFrosty(state.getBlock());
        if (frosty.isPresent()) {
            Player player = context.getPlayer();
            level.playSound(player, pos, SoundEvents.POWDER_SNOW_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
            ParticleUtils.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SNOWFLAKE, UniformInt.of(3, 5));
            if (player instanceof ServerPlayer) {
                if (!player.getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
                level.setBlockAndUpdate(pos, frosty.get().withPropertiesOf(state));
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }


}
