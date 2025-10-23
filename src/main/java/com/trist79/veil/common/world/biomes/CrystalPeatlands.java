/*
 * The Veil Mod
 * File: CrystalPeatlands.java
 * Description: Biome Generation for Crystal Peatlands
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.world.biomes;

import com.trist79.veil.common.data.blocks.VeilBlocks;
import com.trist79.veil.common.world.VeilDimension;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.placement.CaveSurface;


public class CrystalPeatlands {

    public static RuleSource makeSurfaceRules() {
        // Top layer: bryophyte only on top of terrain

        SurfaceRules.RuleSource bryophyteTop = SurfaceRules.ifTrue(
            SurfaceRules.ON_FLOOR,
            //SurfaceRules.stoneDepthCheck(0, false, CaveSurface.FLOOR), // only on top 1 block, ignore caves
            SurfaceRules.state(VeilBlocks.BRYOPHYTE_COVERED_PEAT.get().defaultBlockState())
        );

        // Subsurface: peat (2–3 blocks below surface)
        SurfaceRules.RuleSource peatLayer = SurfaceRules.ifTrue(
            SurfaceRules.stoneDepthCheck(3, true, CaveSurface.FLOOR), // applies 2–3 blocks under top surface
            SurfaceRules.state(VeilBlocks.PEAT.get().defaultBlockState())
        );

        // Lower soil: gleysol (4–7 blocks below surface)
        SurfaceRules.RuleSource gleysolLayer = SurfaceRules.ifTrue(
            SurfaceRules.stoneDepthCheck(7, true, CaveSurface.FLOOR), // applies 4–7 blocks under top
            SurfaceRules.state(VeilBlocks.GLEYSOL.get().defaultBlockState())
        );

    /*
        // Occasional peat patches on the surface
        SurfaceRules.RuleSource peatPatch = SurfaceRules.ifTrue(
            SurfaceRules.noiseCondition(VeilDimensionRegistry.PATCH_NOISE, 0.2),
            SurfaceRules.state(VeilBlocks.PEAT.get().defaultBlockState())
        );

        // Occasional lichen patches
        SurfaceRules.RuleSource lichenPatch = SurfaceRules.ifTrue(
            SurfaceRules.noiseThreshold(VeilDimensionRegistry.PATCH_NOISE, 0.65),
            SurfaceRules.state(VeilBlocks.BRYOPHYTE_LICHEN.get().defaultBlockState())
        ); */

        // Combine all layers in a sequence
        SurfaceRules.RuleSource surfaceRule = SurfaceRules.sequence(
            bryophyteTop,
            peatLayer,
            gleysolLayer
        );
        return surfaceRule;
    }

    public static Biome getCrystalPeatBiome(BootstrapContext<Biome> context) {
        HolderGetter<net.minecraft.world.level.levelgen.placement.PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        BiomeGenerationSettings generationSettings = new BiomeGenerationSettings.Builder(placedFeatures, carvers).build();

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(0.8F)
            .downfall(0.4F)
            .mobSpawnSettings(new MobSpawnSettings.Builder().build())
            .generationSettings(generationSettings)
            .specialEffects(VeilDimension.getBasicBiomeSpecialEffects())
            .build();
    }

}
