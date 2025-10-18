/*
 * The Veil Mod
 * File: VeilDimension.java
 * Description: Initializations and configs for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.world;

import java.util.OptionalLong;

import com.trist79.veil.TheVeilMod;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class VeilDimension {

    public static MonsterSettings veilMonsterSettings = new MonsterSettings(
        false, false, UniformInt.of(0, 8),15);

    public static void bootstrapDimType(BootstrapContext<DimensionType> context) {
        DimensionType theveildimtype = new DimensionType(
            OptionalLong.empty(), //fixedTime
            false, //hasSkyLight
            true, //hasCeiling
            true, //ultraWarm
            false, //natural
            1.0, //coordinateScale
            false, //bedWorks
            true, //respawnAnchorWorks
            0, //minY
            256, //height
            256, //logicalHeight
            net.minecraft.tags.BlockTags.INFINIBURN_OVERWORLD, //infiniburn
            ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil"), //effectsLocation
            0.0f, //ambientLight
            veilMonsterSettings //DimensionType.MonsterSettings
        );
        context.register(VeilDimRegistryKeys.THE_VEIL_DIM_TYPE, theveildimtype);
    }

    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
         // Use holders so the generator references properly registered entries
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        Holder<DimensionType> typeHolder = dimTypes.getOrThrow(VeilDimRegistryKeys.THE_VEIL_DIM_TYPE);
        Holder<Biome> biomeHolder = biomes.getOrThrow(VeilDimRegistryKeys.VEIL_BIOME_1);
        Holder<NoiseGeneratorSettings> noiseHolder = noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);

        FixedBiomeSource biomeSource = new FixedBiomeSource(biomeHolder);
        VeilChunkGenerator chunkGenerator = new VeilChunkGenerator(biomeSource, noiseHolder);

        LevelStem stem = new LevelStem(typeHolder, chunkGenerator);

        context.register(VeilDimRegistryKeys.THE_VEIL_STEM, stem);
    }

    public static void bootstrapBiomes(BootstrapContext<Biome> context) {
        HolderGetter<net.minecraft.world.level.levelgen.placement.PlacedFeature> placedFeatures =
            context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> carvers =
            context.lookup(Registries.CONFIGURED_CARVER);
        BiomeGenerationSettings generationSettings =
            new BiomeGenerationSettings.Builder(placedFeatures, carvers).build();
        BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder()
            .fogColor(12638463)           // default plain fog
            .waterColor(4159204)          // default plain water
            .waterFogColor(329011)        // default water fog
            .skyColor(calculateSkyColor(0.8F)) // temperature-based sky color
            .build();
        // Example: create a simple biome
        Biome biome = new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(0.8F)
            .downfall(0.4F)
            .mobSpawnSettings(new MobSpawnSettings.Builder().build())
            .generationSettings(generationSettings)
            .specialEffects(effects) // Add BiomeSpecialEffects if needed
            .build();
        context.register(VeilDimRegistryKeys.VEIL_BIOME_1, biome);
    }

// Helper for vanilla-like sky color
    private static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Math.clamp(f, -1.0F, 1.0F);
        return java.awt.Color.HSBtoRGB(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
