/*
 * The Veil Mod
 * File: VeilDimension.java
 * Description: Initializations and configs for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.world;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.trist79.veil.TheVeilMod;
import com.trist79.veil.common.data.blocks.VeilBlocks;
import com.trist79.veil.common.world.biomes.CrystalPeatlands;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.FixedBiomeSource;
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
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;

public class VeilDimension {

    public static MonsterSettings veilMonsterSettings = new MonsterSettings(
        false, false, UniformInt.of(0, 7),0);

    public static DimensionType getDimensionType() {
        return new DimensionType(
            OptionalLong.empty(), //fixedTime
            false, //hasSkyLight
            false, //hasCeiling
            false, //ultraWarm
            false, //natural
            1.0, //coordinateScale
            false, //bedWorks
            true, //respawnAnchorWorks
            -64, //minY
            384, //height
            384, //logicalHeight
            net.minecraft.tags.BlockTags.INFINIBURN_OVERWORLD, //infiniburn
            ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil"), //effectsLocation
            0.5f, //ambientLight
            veilMonsterSettings //DimensionType.MonsterSettings
        );
    }

    public static BiomeSpecialEffects getBasicBiomeSpecialEffects() {
        return new BiomeSpecialEffects.Builder().fogColor(0xa794b5).waterColor(0x94b5b5).waterFogColor(0x91acba).skyColor(0x6A0DAD).build();
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
            .getHolder(VeilDimensionRegistry.VEIL_NOISE_GEN_SETTINGS).orElseThrow();
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
        List<Pair<ParameterPoint, Holder<Biome>>> biomeList = new ArrayList<>();

        Parameter emptyClimateParameter = Climate.Parameter.point(0.0F);

        Holder<Biome> crystalPeatlandsBiomeHolder = biomes.getOrThrow(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME);
        ParameterPoint crystalPeatlandsBiomeParams = new Climate.ParameterPoint(emptyClimateParameter, emptyClimateParameter, emptyClimateParameter, emptyClimateParameter, emptyClimateParameter, emptyClimateParameter, 0);
        biomeList.add(Pair.of(crystalPeatlandsBiomeParams, crystalPeatlandsBiomeHolder));

        Holder<NoiseGeneratorSettings> noiseHolder = noiseSettings.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_GEN_SETTINGS);

        NoiseBasedChunkGenerator stemGenerator = new NoiseBasedChunkGenerator(
            MultiNoiseBiomeSource.createFromList(new ParameterList<Holder<Biome>>(biomeList)),
            noiseHolder
        );

        LevelStem stem = new LevelStem(typeHolder, stemGenerator);

        context.register(VeilDimensionRegistry.THE_VEIL_STEM, stem);
    }


    public static void bootstrapBiomes(BootstrapContext<Biome> context) {

        // Placeholder for more biomes in the future
        // context.register(VeilDimensionRegistry.BIOME_REGISTRY_KEY_NAME, BiomeName.getBiomeName(context));

        context.register(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME, CrystalPeatlands.getCrystalPeatBiome(context));
    }

    public static void bootstrapChunkGen(BootstrapContext<MapCodec<? extends ChunkGenerator>> context) {
        // Register our custom chunk generator codec
        context.register(VeilDimensionRegistry.VEIL_CHUNK_GENERATOR, VeilChunkGenerator.CODEC);
    }

    public static void bootstrapNoises(BootstrapContext<NoiseParameters> context) {
        context.register(VeilDimensionRegistry.VEIL_NOISE_1, new NoiseParameters(-6, DoubleList.of(1.0, 0.5, 0.25)));
        context.register(VeilDimensionRegistry.VEIL_NOISE_2, new NoiseParameters(-6, DoubleList.of(1.0, 0.5, 0.25)));
        context.register(VeilDimensionRegistry.VEIL_NOISE_3, new NoiseParameters(-6, DoubleList.of(1.0, 0.5, 0.25)));
        context.register(VeilDimensionRegistry.VEIL_NOISE_4, new NoiseParameters(-6, DoubleList.of(1.0, 0.5, 0.25)));
    }

    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {
        // Create a flat NoiseSettings instance
        NoiseSettings noiseSettings = new NoiseSettings(
            -64,    // minY
            384,  // Total height
            1,    // sizeHorizontal (0 to 4)
            2 // sizeVertical (0 to 4)
        );

        HolderGetter<NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);



        DensityFunction scaledNoise =
            DensityFunctions.mul( // mull 1
                DensityFunctions.constant(0.5),
                DensityFunctions.add( // add 1
                    DensityFunctions.noise(noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_1)),
                    DensityFunctions.mul( // mull 2
                        DensityFunctions.constant(0.5),
                        DensityFunctions.noise(noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_2))
                    ) // mull 2
                ) // add 1
            );
        DensityFunction initialDensity =
            DensityFunctions.add( // add 1
                DensityFunctions.yClampedGradient(-64, 256, 1.5, -1.0), // steeper density curve
                scaledNoise
            ); // add 1

        DensityFunction jaggedness =
            DensityFunctions.mul( // mul 1
                DensityFunctions.constant(0.5),
                DensityFunctions.noise(noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_3))
            ); //mull 1

        DensityFunction finalDensity =
        DensityFunctions.add(
            DensityFunctions.yClampedGradient(-64, 512, 0.3, -0.3),
            DensityFunctions.add(initialDensity, jaggedness)
        );


        NoiseRouter router = new NoiseRouter( // Density Functions for Terrain Generation - see https://minecraft.wiki/w/World_generation#Randomness
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5), // AQUIFER - Barier Noise
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 2.0/3.0),// AQUIFER - Fluid Level Floodness
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 5.0/7.0), // AQUIFER - Fluid Level Spread
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.AQUIFER_LAVA), 1.0), // AQUIFER - Lava overriding water for aquifers
            DensityFunctions.shiftedNoise2d(
                DensityFunctions.shiftA(noiseParameters.getOrThrow(Noises.SHIFT)),
                DensityFunctions.shiftB(noiseParameters.getOrThrow(Noises.SHIFT)),
                0.25, noiseParameters.getOrThrow(Noises.TEMPERATURE)), // BIOME - Temperature Map, mimics vanilla
            DensityFunctions.shiftedNoise2d(
                DensityFunctions.shiftA(noiseParameters.getOrThrow(Noises.SHIFT)),
                DensityFunctions.shiftB(noiseParameters.getOrThrow(Noises.SHIFT)),
                0.25, noiseParameters.getOrThrow(Noises.VEGETATION)), // BIOME - Vegetation map
            DensityFunctions.shiftedNoise2d(
                DensityFunctions.shiftA(noiseParameters.getOrThrow(Noises.SHIFT)),
                DensityFunctions.shiftB(noiseParameters.getOrThrow(Noises.SHIFT)),
                0.25, noiseParameters.getOrThrow(Noises.CONTINENTALNESS)),// BIOME - Continents
            DensityFunctions.shiftedNoise2d(
                DensityFunctions.shiftA(noiseParameters.getOrThrow(Noises.SHIFT)),
                DensityFunctions.shiftB(noiseParameters.getOrThrow(Noises.SHIFT)),
                0.25, noiseParameters.getOrThrow(Noises.EROSION)), // BIOME - Erosion map
            DensityFunctions.constant(0.0), // BIOME - Depth map (0.0 for overworld)
            DensityFunctions.shiftedNoise2d(
                DensityFunctions.shiftA(noiseParameters.getOrThrow(Noises.SHIFT)),
                DensityFunctions.shiftB(noiseParameters.getOrThrow(Noises.SHIFT)),
                0.25, noiseParameters.getOrThrow(Noises.RIDGE)), // BIOME - Ridge (Weirdness) map
            initialDensity, // TERRAIN - Initial Density (without Jaggedness)
            finalDensity, // TERRAIN - Final Density
            DensityFunctions.constant(0), // ORES - Toggle (Ore Vein type and vertical range)
            DensityFunctions.constant(0), // ORES - Ridged (Decides whether block is blank or ore)
            DensityFunctions.noise(noiseParameters.getOrThrow(Noises.ORE_GAP), 1.0, 1.0) // ORES - Gap (Decides between stone ore or raw ore block)
            );
        NoiseGeneratorSettings generatorSettings = new NoiseGeneratorSettings(
            noiseSettings,
            VeilBlocks.VEILSTONE.get().defaultBlockState(),
            Blocks.WATER.defaultBlockState(),
            router,
            makeGlobalSurfaceRules(),
            List.of(),
            100, // sea level
            true, // disable mob generation
            true, // aquifers enabled
            false, // ore veins enabled
            false // use legacy random source
        );
        // Register NoiseGeneratorSettings to context
        context.register(VeilDimensionRegistry.VEIL_NOISE_GEN_SETTINGS, generatorSettings);
    }

    // Add Biomes Here
    public static RuleSource makeGlobalSurfaceRules() {
        RuleSource crystalPeatlandsRules = CrystalPeatlands.makeSurfaceRules();
        return SurfaceRules.sequence(
            //makeBedrockRuleSource(),
            SurfaceRules.ifTrue(SurfaceRules.isBiome(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME), crystalPeatlandsRules)
        );
    }

    // Generate a bedrock floor with gradient
    public static RuleSource makeBedrockRuleSource() {
        return SurfaceRules.sequence(
            SurfaceRules.ifTrue(
                SurfaceRules.yBlockCheck(VerticalAnchor.bottom(), 0),
                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
            ),
                SurfaceRules.ifTrue(
                SurfaceRules.verticalGradient(
                    "bedrock_gradient",
                    VerticalAnchor.bottom(),
                    VerticalAnchor.aboveBottom(2) // dynamically 2 blocks above min height
                ),
                SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
            )
        );
    }

// Helper for vanilla-like sky color
    private static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Math.clamp(f, -1.0F, 1.0F);
        return java.awt.Color.HSBtoRGB(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
