package com.ordana.immersive_weathering.common.entity;

import com.ordana.immersive_weathering.common.entity.ModEntities;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import org.jetbrains.annotations.Nullable;

public class IcicleBlockEntity extends BlockEntity implements GameEventListener {

    protected final PositionSource listenerSource;
    public static final Object2IntMap<GameEvent> VOLUME_FOR_EVENT = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), (map) -> {
        map.put(GameEvent.STEP, 1);
        map.put(GameEvent.FLAP, 2);
        map.put(GameEvent.SWIM, 3);
        map.put(GameEvent.ELYTRA_FREE_FALL, 4);
        map.put(GameEvent.HIT_GROUND, 5);
        map.put(GameEvent.SPLASH, 6);
        map.put(GameEvent.WOLF_SHAKING, 6);
        map.put(GameEvent.MINECART_MOVING, 6);
        map.put(GameEvent.RING_BELL, 6);
        map.put(GameEvent.BLOCK_CHANGE, 6);
        map.put(GameEvent.PROJECTILE_SHOOT, 7);
        map.put(GameEvent.DRINKING_FINISH, 7);
        map.put(GameEvent.PRIME_FUSE, 7);
        map.put(GameEvent.PROJECTILE_LAND, 8);
        map.put(GameEvent.EAT, 8);
        map.put(GameEvent.MOB_INTERACT, 8);
        map.put(GameEvent.ENTITY_DAMAGED, 8);
        map.put(GameEvent.EQUIP, 9);
        map.put(GameEvent.SHEAR, 9);
        map.put(GameEvent.RAVAGER_ROAR, 9);
        map.put(GameEvent.BLOCK_CLOSE, 10);
        map.put(GameEvent.BLOCK_UNSWITCH, 10);
        map.put(GameEvent.BLOCK_UNPRESS, 10);
        map.put(GameEvent.BLOCK_DETACH, 10);
        map.put(GameEvent.DISPENSE_FAIL, 10);
        map.put(GameEvent.BLOCK_OPEN, 11);
        map.put(GameEvent.BLOCK_SWITCH, 11);
        map.put(GameEvent.BLOCK_PRESS, 11);
        map.put(GameEvent.BLOCK_ATTACH, 11);
        map.put(GameEvent.ENTITY_PLACE, 12);
        map.put(GameEvent.BLOCK_PLACE, 12);
        map.put(GameEvent.FLUID_PLACE, 12);
        map.put(GameEvent.ENTITY_KILLED, 13);
        map.put(GameEvent.BLOCK_DESTROY, 13);
        map.put(GameEvent.FLUID_PICKUP, 13);
        map.put(GameEvent.FISHING_ROD_REEL_IN, 14);
        map.put(GameEvent.CONTAINER_CLOSE, 14);
        map.put(GameEvent.PISTON_CONTRACT, 14);
        map.put(GameEvent.SHULKER_CLOSE, 14);
        map.put(GameEvent.PISTON_EXTEND, 15);
        map.put(GameEvent.CONTAINER_OPEN, 15);
        map.put(GameEvent.FISHING_ROD_CAST, 15);
        map.put(GameEvent.EXPLODE, 15);
        map.put(GameEvent.LIGHTNING_STRIKE, 15);
        map.put(GameEvent.SHULKER_OPEN, 15);
    }));

    public IcicleBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.ICICLE_TILE.get(), pos, state);
        this.listenerSource = new BlockPositionSource(this.worldPosition);
    }

    @Override
    public PositionSource getListenerSource() {
        return listenerSource;
    }

    @Override
    public int getListenerRadius() {
        return 8;
    }

    @Override
    public boolean handleGameEvent(Level level, GameEvent gameEvent, @Nullable Entity entity, BlockPos pos) {
        if(pos != this.worldPosition) {
            int volume = VOLUME_FOR_EVENT.getInt(gameEvent);
            double distanceSqr = this.worldPosition.distToCenterSqr(pos.getX(), pos.getY(), pos.getZ());
            if (volume * volume > distanceSqr) {
                float distScaling = 1;
                int delay =  Math.max(1, (int) Mth.sqrt((float) (distScaling*distanceSqr))) + level.getRandom().nextInt(3)-1;
                level.scheduleTick(this.worldPosition, this.getBlockState().getBlock(),delay);
            }
        }
        return false;
    }
}
