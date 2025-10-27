/*
 * The Veil Mod
 * File: VeilDimensionRegistry.java
 * Description: Veil Dimension Registry Keys File
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.world;

import com.mojang.serialization.MapCodec;
import com.trist79.veil.TheVeilMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class VeilDimensionRegistry {

    public static final ResourceKey<Level> VEIL_DIM = ResourceKey.create(
        Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil")
    );

    public static final ResourceKey<DimensionType> THE_VEIL_DIM_TYPE = ResourceKey.create(
        Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_dim_type")
    );

    public static final ResourceKey<MapCodec<? extends ChunkGenerator>> VEIL_CHUNK_GENERATOR = ResourceKey.create(
        Registries.CHUNK_GENERATOR, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_chunk_gen")
    );

    public static final ResourceKey<LevelStem> THE_VEIL_STEM = ResourceKey.create(
        Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil")
    );

    public static final ResourceKey<Biome> CRYSTAL_PEATLANDS_BIOME = ResourceKey.create(
        Registries.BIOME, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "crystal_peatlands_biome")
    );

    public static final ResourceKey<NoiseGeneratorSettings> VEIL_NOISE_GEN_SETTINGS = ResourceKey.create(
        Registries.NOISE_SETTINGS, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_noise_gen")
    );

    // Register Veil Noises
    public static final ResourceKey<NoiseParameters> VEIL_NOISE_1 = ResourceKey.create(
        Registries.NOISE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_noise_1")
    );
    public static final ResourceKey<NoiseParameters> VEIL_NOISE_2 = ResourceKey.create(
        Registries.NOISE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_noise_2")
    );
    public static final ResourceKey<NoiseParameters> VEIL_NOISE_3 = ResourceKey.create(
        Registries.NOISE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_noise_3")
    );
    public static final ResourceKey<NoiseParameters> VEIL_NOISE_4 = ResourceKey.create(
        Registries.NOISE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_noise_4")
    );




}
