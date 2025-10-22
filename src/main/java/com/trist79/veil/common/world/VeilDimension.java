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
import com.trist79.veil.common.data.blocks.VeilBlocks;
import com.trist79.veil.common.world.biomes.CrystalPeatlands;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
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
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;

public class VeilDimension {

    public static MonsterSettings veilMonsterSettings = new MonsterSettings(
        false, false, UniformInt.of(0, 8),15);

    public static DimensionType getDimensionType() {
        return new DimensionType(
            OptionalLong.empty(), //fixedTime
            false, //hasSkyLight
            true, //hasCeiling
            false, //ultraWarm
            false, //natural
            1.0, //coordinateScale
            false, //bedWorks
            true, //respawnAnchorWorks
            0, //minY
            256, //height
            256, //logicalHeight
            net.minecraft.tags.BlockTags.INFINIBURN_OVERWORLD, //infiniburn
            ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil"), //effectsLocation
            0.25f, //ambientLight
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
            .getHolder(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME).orElseThrow();

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
        Holder<Biome> biomeHolder = biomes.getOrThrow(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME);
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
            .skyColor(0x6A0DAD) // temperature-based sky color
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
        context.register(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME, biome);
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
            .getHolder(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME)
            .orElseThrow(() -> new IllegalStateException("Missing biome: " + VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME.location()));

        FixedBiomeSource biomeSource = new FixedBiomeSource(biomeHolder);

        return new VeilChunkGenerator(biomeSource, noiseHolder);
    }

    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        // Create a flat NoiseSettings instance
        NoiseSettings noiseSettings = new NoiseSettings(
            0,    // minY
            128,  // height
            1,    // sizeHorizontal
            1 //sizeVertical
        );

        HolderGetter<NormalNoise.NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);
        NoiseRouter router = new NoiseRouter(
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 1.0),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 1.0),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 1.0),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA), 1.0),

            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.TEMPERATURE), 0.25),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.VEGETATION), 0.25),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CONTINENTALNESS), 1.0), // full freq
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.EROSION), 0.5),

            // Depth: gentle variation
            DensityFunctions.add(
                DensityFunctions.mul(
                    DensityFunctions.noise(noiseParameters.getOrThrow(Noises.RIDGE), 1.0D),
                    DensityFunctions.constant(2.0D)
                ),
                DensityFunctions.constant(0.0D)
            ),

            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.JAGGED), 0.25), // low jaggedness

            DensityFunctions.add(
                DensityFunctions.mul(
                    DensityFunctions.noise(noiseParameters.getOrThrow(Noises.CONTINENTALNESS_LARGE), 1.0D),
                    DensityFunctions.constant(0.5D)
                ),
                DensityFunctions.constant(0.0D)
            ),

            DensityFunctions.add(
                DensityFunctions.mul(DensityFunctions.noise(noiseParameters.getOrThrow(Noises.EROSION_LARGE), 1.0D), DensityFunctions.constant(16.0D)),
                DensityFunctions.constant(60.0D)
            ),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEININESS), 1.0D),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_VEIN_A), 1.0D),
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP), 1.0D)
        );
        // Create the NoiseGeneratorSettings record
        NoiseGeneratorSettings generatorSettings = new NoiseGeneratorSettings(
            noiseSettings,
            VeilBlocks.VEILSTONE.get().defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            router,
            CrystalPeatlands.makeSurfaceRules(),
            List.of(),
            60,                                               // sea level
            true,                                            // disable mob generation
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
