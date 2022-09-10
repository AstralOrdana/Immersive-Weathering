package com.ordana.immersive_weathering.data.position_tests;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;


public class DummyWorldGenerationContext extends WorldGenerationContext {

    private static final DummyGenerator DUMMY_GENERATOR = new DummyGenerator();

    public DummyWorldGenerationContext(Level level) {
        super(DUMMY_GENERATOR, level);
    }

    private static class DummyGenerator extends ChunkGenerator {

        public DummyGenerator() {
            super(null, null, null);
        }

        //use these 2
        @Override
        public int getGenDepth() {
            return 10000;
        }

        @Override
        public int getMinY() {
            return -10000;
        }
        //these will never get called

        @Override
        protected Codec<? extends ChunkGenerator> codec() {
            return null;
        }

        @Override
        public ChunkGenerator withSeed(long p_62156_) {
            return null;
        }

        @Override
        public Climate.Sampler climateSampler() {
            return null;
        }

        @Override
        public void applyCarvers(WorldGenRegion p_187691_, long p_187692_, BiomeManager p_187693_, StructureFeatureManager p_187694_, ChunkAccess p_187695_, GenerationStep.Carving p_187696_) {

        }

        @Override
        public void buildSurface(WorldGenRegion p_187697_, StructureFeatureManager p_187698_, ChunkAccess p_187699_) {

        }

        @Override
        public void spawnOriginalMobs(WorldGenRegion p_62167_) {

        }


        @Override
        public CompletableFuture<ChunkAccess> fillFromNoise(Executor p_187748_, Blender p_187749_, StructureFeatureManager p_187750_, ChunkAccess p_187751_) {
            return null;
        }

        @Override
        public int getSeaLevel() {
            return 0;
        }


        @Override
        public int getBaseHeight(int p_156153_, int p_156154_, Heightmap.Types p_156155_, LevelHeightAccessor p_156156_) {
            return 0;
        }

        @Override
        public NoiseColumn getBaseColumn(int p_156150_, int p_156151_, LevelHeightAccessor p_156152_) {
            return null;
        }

        @Override
        public void addDebugScreenInfo(List<String> p_208054_, BlockPos p_208055_) {

        }
    }
}