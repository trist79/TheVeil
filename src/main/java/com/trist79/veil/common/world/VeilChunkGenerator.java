/*
 * The Veil Mod
 * File: VeilChunkGenerator.java
 * Description: Chunk Generator for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.world;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class VeilChunkGenerator extends NoiseBasedChunkGenerator {

    public static final MapCodec<VeilChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource),
            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(gen -> gen.settings)
        ).apply(instance, VeilChunkGenerator::new)
    );

    public static final Supplier<Codec<VeilChunkGenerator>> RUNTIME_CODEC =
        Suppliers.memoize(() -> CODEC.codec());

    private final Holder<NoiseGeneratorSettings> settings;
    public VeilChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> noiseSettings) {
        super(biomeSource, noiseSettings);
        this.settings = noiseSettings;
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return  CODEC;
    }

    // @Override
    // public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor height, RandomState random) {
    //     int minY = height.getMinBuildHeight();
    //     int maxY = height.getMaxBuildHeight();
    //     int heightSize = maxY - minY;

    //     // Create a block array
    //     var blocks = new net.minecraft.world.level.block.state.BlockState[heightSize];

    //     // Bedrock at bottom
    //     blocks[0] = Blocks.BEDROCK.defaultBlockState();

    //     // Fill the rest with your base terrain (Veilstone)
    //     for (int y = 1; y < heightSize; y++) {
    //         blocks[y] = VeilBlocks.VEILSTONE.get().defaultBlockState();
    //     }
    //     return new NoiseColumn(minY, blocks);
    // }
}
