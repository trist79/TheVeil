package com.trist79.veil.common.world;

import com.trist79.veil.TheVeilMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class VeilWorldSetup {

    public static final ResourceKey<Level> THE_VEIL_KEY = ResourceKey.create(
        Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil")
    );

    public static final ResourceKey<DimensionType> THE_VEIL_DIM_TYPE = ResourceKey.create(
            Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil")
    );


}