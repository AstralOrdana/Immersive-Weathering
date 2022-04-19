package com.ordana.immersive_weathering.common.entity;

import com.ordana.immersive_weathering.common.ModEntities;
import com.ordana.immersive_weathering.configs.ServerConfigs;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import org.jetbrains.annotations.Nullable;

public class IcicleBlockEntity extends BlockEntity implements GameEventListener {
    private final int radius;
    protected final PositionSource listenerSource;
    public static final Object2IntMap<GameEvent> VOLUME_FOR_EVENT = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), (map) -> {
        map.put(GameEvent.HIT_GROUND, 2);
        map.put(GameEvent.BLOCK_DESTROY, 1);
        map.put(GameEvent.MINECART_MOVING, 2);
        map.put(GameEvent.RING_BELL, 8);
        map.put(GameEvent.BLOCK_CHANGE, 1);
        map.put(GameEvent.RAVAGER_ROAR, 8);
        map.put(GameEvent.BLOCK_CLOSE, 6);
        map.put(GameEvent.BLOCK_OPEN, 6);
        map.put(GameEvent.CONTAINER_CLOSE, 3);
        map.put(GameEvent.CONTAINER_OPEN, 3);
        map.put(GameEvent.PISTON_CONTRACT, 3);
        map.put(GameEvent.SHULKER_CLOSE, 3);
        map.put(GameEvent.SHULKER_OPEN, 3);
        map.put(GameEvent.PISTON_EXTEND, 3);
        map.put(GameEvent.EXPLODE, 15);
        map.put(GameEvent.LIGHTNING_STRIKE, 15);
        Registry.GAME_EVENT.getOptional(new ResourceLocation("moyai", "moyai_boom"))
                .ifPresent(e -> map.put(e, 7));
    }));

    public IcicleBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.ICICLE_TILE.get(), pos, state);
        this.listenerSource = new BlockPositionSource(this.worldPosition);
        this.radius = ServerConfigs.ICICLES_FALLING.get() ? 15 : 0;
    }

    @Override
    public PositionSource getListenerSource() {
        return listenerSource;
    }

    @Override
    public int getListenerRadius() {
        return radius;
    }

    @Override
    public boolean handleGameEvent(Level level, GameEvent gameEvent, @Nullable Entity entity, BlockPos pos) {
        if (pos != this.worldPosition) {
            int volume = VOLUME_FOR_EVENT.getInt(gameEvent);
            double distanceSqr = this.worldPosition.distToLowCornerSqr(pos.getX(), pos.getY(), pos.getZ());
            if (volume * volume > distanceSqr * 0.5 + level.random.nextFloat() * distanceSqr) {
                float distScaling = 2f;
                int o = level.getRandom().nextInt(3) - 1;
                int delay = Math.max(0, (int) (Mth.sqrt((float) (distScaling * distanceSqr)))) + o;
                level.scheduleTick(this.worldPosition, this.getBlockState().getBlock(), delay);
            }
        }
        return false;
    }
}
