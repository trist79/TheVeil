/*
 * The Veil Mod
 * File: VeilChunkGenerator.java
 * Description: Chunk Generator for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.world;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

public class VeilChunkGenerator extends NoiseBasedChunkGenerator {

    public static final MapCodec<VeilChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(gen -> gen.settings)
            ).apply(instance, VeilChunkGenerator::new)
    );
    private final Holder<NoiseGeneratorSettings> settings;
    public VeilChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> noiseSettings) {
        super(biomeSource, noiseSettings);
        this.settings = noiseSettings;
    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager, RandomState random, ChunkAccess chunk) {
        // Get the world-space base position of this chunk
        int chunkX = chunk.getPos().getMinBlockX();
        int chunkZ = chunk.getPos().getMinBlockZ();

        int surfaceY = level.getMinBuildHeight(); // Usually 0, but read from level
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                // Place a single bedrock block at the base level
                chunk.setBlockState(
                    new net.minecraft.core.BlockPos(chunkX + x, surfaceY, chunkZ + z),
                    net.minecraft.world.level.block.Blocks.BEDROCK.defaultBlockState(),
                    false
                );
            }
        }
    }

    @Override
    public void applyBiomeDecoration(WorldGenLevel level, ChunkAccess chunk, StructureManager structureManager) {
        // no decorations
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState random, BiomeManager biomeManager, StructureManager structureManager, ChunkAccess chunk, GenerationStep.Carving step) {
        // no carvers
    }


    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return (MapCodec<? extends ChunkGenerator>) CODEC;
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {
        // Spawn Nothing
    }

    @Override
    public int getGenDepth() {
        return 256;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState,
            StructureManager structureManager, ChunkAccess chunk) {

        int chunkX = chunk.getPos().getMinBlockX();
        int chunkZ = chunk.getPos().getMinBlockZ();
        int minY = chunk.getMinBuildHeight();

        // Fill only the bottom layer with bedrock
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlockState(
                    new BlockPos(chunkX + x, minY, chunkZ + z),
                    Blocks.BEDROCK.defaultBlockState(),
                    false
                );
            }
        }

    // Everything above remains air by default

    // Return a completed future
    return CompletableFuture.completedFuture(chunk);
}

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int x, int z, Types type, LevelHeightAccessor level, RandomState random) {
        return 1;
    }

    @Override
public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
    int minY = height.getMinBuildHeight();
    int maxY = height.getMaxBuildHeight();

    // Create a column array
    var blocks = new net.minecraft.world.level.block.state.BlockState[maxY - minY];

    // Bedrock at bottom
    blocks[0] = Blocks.BEDROCK.defaultBlockState();

    // Everything else is air
    for (int i = 1; i < blocks.length; i++) {
        blocks[i] = Blocks.AIR.defaultBlockState();
    }
    return new NoiseColumn(minY, blocks);
}

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState random, BlockPos pos) {
        // No, I don't think I will
    }
}
