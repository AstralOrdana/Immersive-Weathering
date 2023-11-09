package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


public class DummyWorldGenerationContext extends WorldGenerationContext {

    private static final DummyGenerator DUMMY_GENERATOR = new DummyGenerator(null);

    public DummyWorldGenerationContext(Level level) {
        super(DUMMY_GENERATOR, level);
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
        public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState randomState, StructureManager structureManager, ChunkAccess chunkAccess) {
            return null;
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
        protected Codec<? extends ChunkGenerator> codec() {
            return null;
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