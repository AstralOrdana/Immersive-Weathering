package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.blocks.frosted.Frosty;
import com.ordana.immersive_weathering.reg.ModBlocks;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.mehvahdjukaar.moonlight.api.client.util.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class FrostItem extends BlockItem {
    public FrostItem(Block block, Properties properties) {
        super(block, properties);
    }

    //hack for roots
    @PlatformOnly(PlatformOnly.FABRIC)
    @Override
    public void registerBlocks(Map<Block, Item> map, Item item) {
        super.registerBlocks(map, item);
        map.put(ModBlocks.HANGING_ROOTS_WALL.get(), Items.HANGING_ROOTS);
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
            if (level.isClientSide()) ParticleUtil.spawnParticlesOnBlockFaces(level, pos, ParticleTypes.SNOWFLAKE, UniformInt.of(3, 5), -0.05f, 0.05f, false);
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
