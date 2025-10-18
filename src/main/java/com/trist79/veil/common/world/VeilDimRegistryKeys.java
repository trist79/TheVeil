package com.trist79.veil.common.world;

import com.trist79.veil.TheVeilMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;

public class VeilDimRegistryKeys {

    public static final ResourceKey<Level> THE_VEIL_LEVEL = ResourceKey.create(
        Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "dim")
    );

    public static final ResourceKey<DimensionType> THE_VEIL_DIM_TYPE = ResourceKey.create(
            Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "dim")
    );

    public static final ResourceKey<LevelStem> THE_VEIL_STEM = ResourceKey.create(
            Registries.LEVEL_STEM, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "dim")
    );

    public static final ResourceKey<Biome> VEIL_BIOME_1 = ResourceKey.create(
            Registries.BIOME, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "dim")
    );

}