package com.ordana.immersive_weathering.registry.entities;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;
import org.jetbrains.annotations.Nullable;

public class IcicleBlockEntity extends BlockEntity implements GameEventListener {

    protected final PositionSource listenerSource;
    public static final Object2IntMap<GameEvent> VOLUME_FOR_EVENT = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), (map) -> {
        map.put(GameEvent.PROJECTILE_LAND, 1);
        map.put(GameEvent.BLOCK_DESTROY, 2);
        map.put(GameEvent.HIT_GROUND, 2);
        map.put(GameEvent.PISTON_CONTRACT, 3);
        map.put(GameEvent.PISTON_EXTEND, 3);
        //map.put(GameEvent.MINECART_MOVING, 4);
        map.put(GameEvent.BLOCK_OPEN, 6);
        map.put(GameEvent.BLOCK_CLOSE, 7);
        //map.put(GameEvent.RING_BELL, 8);
        map.put(GameEvent.ENTITY_ROAR, 10);
        map.put(GameEvent.EXPLODE, 15);
        map.put(GameEvent.LIGHTNING_STRIKE, 15);
        Registry.GAME_EVENT.getOrEmpty(new Identifier("moyai", "moyai_boom"))
                .ifPresent(e -> map.put(e, 15));
    }));

    public IcicleBlockEntity(BlockPos pos, BlockState state) {
        super(ModEntities.ICICLE_TILE, pos, state);
        this.listenerSource = new BlockPositionSource(this.pos);
    }

    @Override
    public PositionSource getPositionSource() {
        return listenerSource;
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    public boolean listen(ServerWorld level, GameEvent.Message gameEvent) {
        if(pos != this.pos) {
            int volume = VOLUME_FOR_EVENT.getInt(gameEvent);
            double distanceSqr = this.pos.getSquaredDistanceFromCenter(pos.getX(), pos.getY(), pos.getZ());
            if (volume * volume > distanceSqr * 0.9 + level.random.nextFloat() * distanceSqr) {
                float distScaling = 2f;
                int o = level.getRandom().nextInt(3)-1;
                int delay =  Math.max(0, (int) (MathHelper.sqrt((float) (distScaling*distanceSqr)))) + o;
                level.createAndScheduleBlockTick(this.pos, this.getCachedState().getBlock(),delay);
            }
        }
        return false;
    }
}