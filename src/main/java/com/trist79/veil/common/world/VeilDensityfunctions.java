/*
 * The Veil Mod
 * File: VeilDensityfunctions.java
 * Description: Generates
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.world;

import java.rmi.registry.Registry;

import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.CubicSpline;
import net.minecraft.world.level.levelgen.Density;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

public class VeilDensityfunctions {

    public NoiseRouter getNoiseRouter(BootstrapContext<NoiseGeneratorSettings> context) {
        HolderGetter<NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);




        return null;
    }

    public DensityFunction getOffset(BootstrapContext<NoiseGeneratorSettings> context){
        HolderGetter<NoiseParameters> noiseParameters = context.lookup(Registries.NOISE);
        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
        //CubicSpline.Builder<DensityFunctions.Spline.Coordinate, DensityFunctions.Spline.Point> testSplineBuilder = null;// new CubicSpline.Builder<DensityFunctions.Spline.Coordinate, DensityFunctions.Spline.Point>();
        return DensityFunctions.flatCache(
            DensityFunctions.cache2d(
                DensityFunctions.add(
                    DensityFunctions.mul(
                        DensityFunctions.blendOffset(),
                        DensityFunctions.add(
                            DensityFunctions.constant(1),
                            DensityFunctions.mul(
                                DensityFunctions.constant(-1),
                                DensityFunctions.cacheOnce(
                                    DensityFunctions.blendAlpha()
                                )
                            )
                        )
                    ),
                    DensityFunctions.mul(
                        DensityFunctions.add(
                            DensityFunctions.constant(-403/800),
                            DensityFunctions.spline(
                               //CubicSpline<DensityFunctions.Spline.Coordinate(densityFunctions.getOrThrow(VeilDimensionRegistry.DF_CONTINENTS))>.Builder()
                                    new DensityFunctions.Spline.Coordinate(densityFunctions.getOrThrow(VeilDimensionRegistry.DF_CONTINENTS)), )


                            )
                        )
                    )
                )
            )
        );
    }

    public DensityFunction getErosion(HolderGetter<NoiseParameters> noiseParameters){
        return DensityFunctions.flatCache(
            DensityFunctions.shiftedNoise2d(
                getShiftX(noiseParameters),
                getShiftY(noiseParameters),
                0.25,
                noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_3))
        );
    }

    public DensityFunction getRidges(HolderGetter<NoiseParameters> noiseParameters){
        return DensityFunctions.flatCache(
            DensityFunctions.shiftedNoise2d(
                getShiftX(noiseParameters),
                getShiftY(noiseParameters),
                0.25,
                noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_4))
        );
    }

    public DensityFunction getShiftX(HolderGetter<NoiseParameters> noiseParameters) {
        return DensityFunctions.flatCache(
            DensityFunctions.cache2d(
                DensityFunctions.shiftA(
                    noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_2)
                )
            )
        );
    }
    public DensityFunction getShiftY(HolderGetter<NoiseParameters> noiseParameters) {
        return DensityFunctions.flatCache(
            DensityFunctions.cache2d(
                DensityFunctions.shiftB(
                    noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_2)
                )
            )
        );
    }

    public DensityFunction getRidgesFolded(HolderGetter<NoiseParameters> noiseParameters) {
        return DensityFunctions.mul(
            DensityFunctions.constant(-3.0),
            DensityFunctions.add(
                DensityFunctions.constant(-1/3),
                DensityFunctions.add(
                    DensityFunctions.constant(-2/3),
                    getRidges(noiseParameters).abs()
                ).abs()
            )
        );
    }
    public DensityFunction getContinents(HolderGetter<NoiseParameters> noiseParameters){
        return DensityFunctions.flatCache(
            DensityFunctions.shiftedNoise2d(
                getShiftX(noiseParameters),
                getShiftY(noiseParameters),
                0.25,
                noiseParameters.getOrThrow(VeilDimensionRegistry.VEIL_NOISE_1))
        );
    }

    public DensityFunction getVegetation(){
        return null;
    }

    public DensityFunction getTemperature(){
        return null;
    }

    public DensityFunction getFinalDensity(){
        return null;
    }

    public DensityFunction getInitialDensity(){
        return null;
    }

}
