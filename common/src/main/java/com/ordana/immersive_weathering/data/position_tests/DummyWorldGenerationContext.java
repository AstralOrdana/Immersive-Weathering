package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DummyWorldGenerationContext extends WorldGenerationContext {

    public DummyWorldGenerationContext(Level level) {
    super(new DummyGenerator(new FixedBiomeSource(level.registryAccess().lookupOrThrow(Registries.BIOME).getOrThrow(Biomes.PLAINS))), level);
    }

    private static class DummyGenerator extends ChunkGenerator {


        public DummyGenerator(BiomeSource biomeSource) {
            super(biomeSource);
        }

        //use these 2
        @Override
        public int getGenDepth() {
            return 10000;
        }

        @Override
        public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
            return CompletableFuture.completedFuture(chunkAccess);
        }

        @Override
        public int getMinY() {
            return -10000;
        }

        @Override
        public int getBaseHeight(int i, int j, Heightmap.Types types, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
            return 0;
        }

        @Override
        public NoiseColumn getBaseColumn(int i, int j, LevelHeightAccessor levelHeightAccessor, RandomState randomState) {
            return null;
        }

        @Override
        public void addDebugScreenInfo(List<String> list, RandomState randomState, BlockPos blockPos) {

        }
        //these will never get called

        @Override
        protected MapCodec<? extends ChunkGenerator> codec() {
            return MapCodec.unit(this);
        }

        @Override
        public void applyCarvers(WorldGenRegion worldGenRegion, long l, RandomState randomState, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunkAccess, GenerationStep.Carving carving) {

        }

        @Override
        public void buildSurface(WorldGenRegion worldGenRegion, StructureManager structureManager, RandomState randomState, ChunkAccess chunkAccess) {

        }

        @Override
        public void spawnOriginalMobs(WorldGenRegion p_62167_) {

        }

        @Override
        public int getSeaLevel() {
            return 0;
        }

    }
}