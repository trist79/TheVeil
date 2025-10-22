/*
 * The Veil Mod
 * File: CrystalPeatlands.java
 * Description: Biome Generation for Crystal Peatlands
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.world.biomes;

import com.trist79.veil.common.data.blocks.VeilBlocks;

import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;


public class CrystalPeatlands {

    public static RuleSource makeSurfaceRules() {
        // Top layer: bryophyte only on top of terrain

SurfaceRules.RuleSource bryophyteTop = SurfaceRules.ifTrue(
        SurfaceRules.stoneDepthCheck(1, false, CaveSurface.FLOOR), // only on top 1 block, ignore caves
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


        // Base layer: veilstone everywhere else
        SurfaceRules.RuleSource veilstoneBase = SurfaceRules.state(VeilBlocks.VEILSTONE.get().defaultBlockState());

/*         // Occasional peat patches on the surface
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
            gleysolLayer,
            veilstoneBase
        );
        return surfaceRule;
    }
}
