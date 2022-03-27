package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.IcicleBlock;
import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.blocks.WeatheringHelper;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;
/*
@Mixin(ServerWorld.class)
public abstract class SnowMixin {
    protected float rainGradientPrev;
    protected float rainGradient;
    private Supplier<Profiler> profiler;

    protected void World(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> registryEntry, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        this.profiler = profiler;
    }

    public float getRainGradient(float delta) {
        return MathHelper.lerp(delta, this.rainGradientPrev, this.rainGradient);
    }

    public boolean isRaining() {
        return (double)this.getRainGradient(1.0F) > 0.2D;
    }

    public Profiler getProfiler() {
        return (Profiler)this.profiler.get();
    }

    BlockPos getTopPosition(Heightmap.Type heightmap, BlockPos pos) {
        return super.getTopPosition(heightmap, pos);
    }

    @Redirect(method = "tickChunk", at = @At("HEAD"))
    public void tickChunk(WorldChunk chunk, int randomTickSpeed) {
        ChunkPos chunkPos = chunk.getPos();
        boolean bl = this.isRaining();
        int i = chunkPos.getStartX();
        int j = chunkPos.getStartZ();
        Profiler profiler = this.getProfiler();
        profiler.push("thunder");
        BlockPos blockPos = blockPos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);

        if (bl && WeatheringHelper.isIciclePos(blockPos)) {
            BlockPos p = blockPos.down();
            BlockState placement =  ModBlocks.ICICLE.getDefaultState().with(IcicleBlock.VERTICAL_DIRECTION, Direction.DOWN);
            if (world.getBlockState(p).isAir() && placement.canPlaceAt(world, p)) {
                if (Direction.Type.HORIZONTAL.stream().anyMatch(d -> world.isSkyVisible(p.offset(d)))) {
                    world.setBlockState(p, placement, 2);
                }
            }
        }
        instance.precipitationTick(state, level, pos, precipitation);
    }
}*/
