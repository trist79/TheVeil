/*
 * The Veil Mod
 * File: VeilDimension.java
 * Description: Initializations and configs for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.world;

import java.util.List;
import java.util.OptionalLong;

import com.mojang.serialization.MapCodec;
import com.trist79.veil.TheVeilMod;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.material.Fluids;

public class VeilDimension {

    public static MonsterSettings veilMonsterSettings = new MonsterSettings(
        false, false, UniformInt.of(0, 8),15);

    public static DimensionType getDimensionType() {
        return new DimensionType(
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
    }
    public static void bootstrapDimType(BootstrapContext<DimensionType> context) {
        DimensionType theveildimtype = getDimensionType();
        context.register(VeilDimensionRegistry.THE_VEIL_DIM_TYPE, theveildimtype);
    }

    public static LevelStem bootstrapRuntimeStem(ServerLevel level) {
        Holder<DimensionType> typeHolder = level.registryAccess()
            .registryOrThrow(Registries.DIMENSION_TYPE)
            .getHolder(VeilDimensionRegistry.THE_VEIL_DIM_TYPE).orElseThrow();

        Holder<Biome> biomeHolder = level.registryAccess()
            .registryOrThrow(Registries.BIOME)
            .getHolder(VeilDimensionRegistry.VEIL_BIOME_1).orElseThrow();

        Holder<NoiseGeneratorSettings> noiseHolder = level.registryAccess()
            .registryOrThrow(Registries.NOISE_SETTINGS)
            .getHolder(VeilDimensionRegistry.VOID_NOISE_GEN_KEY).orElseThrow();

        FixedBiomeSource biomeSource = new FixedBiomeSource(biomeHolder);
        VeilChunkGenerator runtimeGenerator = new VeilChunkGenerator(biomeSource, noiseHolder);

        return new LevelStem(typeHolder, runtimeGenerator);
    }

    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
         // Use holders so the generator references properly registered entries

        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);


        Holder<DimensionType> typeHolder = dimTypes.getOrThrow(VeilDimensionRegistry.THE_VEIL_DIM_TYPE);
        Holder<Biome> biomeHolder = biomes.getOrThrow(VeilDimensionRegistry.VEIL_BIOME_1);
        Holder<NoiseGeneratorSettings> noiseHolder = noiseSettings.getOrThrow(VeilDimensionRegistry.VOID_NOISE_GEN_KEY);

        //FixedBiomeSource biomeSource = new FixedBiomeSource(biomeHolder);
        //VeilChunkGenerator chunkGenerator = new VeilChunkGenerator(biomeSource, noiseHolder);


        NoiseBasedChunkGenerator stubGenerator = new NoiseBasedChunkGenerator(
        new FixedBiomeSource(biomeHolder),
        noiseHolder
    );

        LevelStem stem = new LevelStem(typeHolder, stubGenerator);

        context.register(VeilDimensionRegistry.THE_VEIL_STEM, stem);
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
        context.register(VeilDimensionRegistry.VEIL_BIOME_1, biome);
    }

    public static void bootstrapChunkGen(BootstrapContext<MapCodec<? extends ChunkGenerator>> context) {
        // Register our custom chunk generator codec in the CHUNK_GENERATOR registry
        context.register(
            VeilDimensionRegistry.VEIL_CHUNK_GENERATOR,
            VeilChunkGenerator.CODEC
        );
    }

    public static ChunkGenerator createChunkGenerator(RegistryAccess registryAccess) {
        // Get the NoiseGeneratorSettings holder from the provided RegistryAccess
        Holder<NoiseGeneratorSettings> noiseHolder = registryAccess
            .registryOrThrow(Registries.NOISE_SETTINGS)
            .getHolder(VeilDimensionRegistry.VOID_NOISE_GEN_KEY)
            .orElseThrow(() -> new IllegalStateException("Missing Veil noise settings"));
        // Get the Biome holder
        Holder<Biome> biomeHolder = registryAccess
            .registryOrThrow(Registries.BIOME)
            .getHolder(VeilDimensionRegistry.VEIL_BIOME_1)
            .orElseThrow(() -> new IllegalStateException("Missing biome: " + VeilDimensionRegistry.VEIL_BIOME_1.location()));

        FixedBiomeSource biomeSource = new FixedBiomeSource(biomeHolder);

        return new VeilChunkGenerator(biomeSource, noiseHolder);
    }

    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        // Create a flat NoiseSettings instance
        NoiseSettings noiseSettings = new NoiseSettings(
            0,    // minY
            128,  // height
            1,    // sizeHorizontal
            1
        );

        SurfaceRules.RuleSource voidSurface = SurfaceRules.sequence(SurfaceRules.state(Blocks.AIR.defaultBlockState()));
        NoiseRouter router = new NoiseRouter(
            DensityFunctions.constant(0.0D), // barrierNoise
            DensityFunctions.constant(0.0D), // fluidLevelFloodednessNoise
            DensityFunctions.constant(0.0D), // fluidLevelSpreadNoise
            DensityFunctions.constant(0.0D), // lavaNoise
            DensityFunctions.constant(0.0D), // temperature
            DensityFunctions.constant(0.0D), // vegetation
            DensityFunctions.constant(0.0D), // continents
            DensityFunctions.constant(0.0D), // erosion
            DensityFunctions.constant(0.0D), // depth
            DensityFunctions.constant(0.0D), // ridges
            DensityFunctions.constant(0.0D), // initialDensityWithoutJaggedness
            DensityFunctions.constant(0.0D), // finalDensity
            DensityFunctions.constant(0.0D), // veinToggle
            DensityFunctions.constant(0.0D), // veinRidged
            DensityFunctions.constant(0.0D)  // veinGap
        );
        // Create the NoiseGeneratorSettings record
        NoiseGeneratorSettings generatorSettings = new NoiseGeneratorSettings(
            noiseSettings,
            Blocks.STONE.defaultBlockState(),
            Fluids.FLOWING_WATER.defaultFluidState().createLegacyBlock(),
            router,
            voidSurface,
            List.of(),
            63,                                               // sea level
            false,                                            // disable mob generation
            false,                                            // aquifers enabled
            false,                                            // ore veins enabled
            false                                             // use legacy random source
        );


        // Register to context
        context.register(VeilDimensionRegistry.VOID_NOISE_GEN_KEY, generatorSettings);
    }

// Helper for vanilla-like sky color
    private static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Math.clamp(f, -1.0F, 1.0F);
        return java.awt.Color.HSBtoRGB(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
