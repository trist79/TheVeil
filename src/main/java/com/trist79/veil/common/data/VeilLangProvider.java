/*
 * The Veil Mod
 * File: VeilLangProvider.java
 * Description: Language .json Builder
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.data;

import com.trist79.veil.common.data.blocks.VeilBlocks;
import com.trist79.veil.common.items.VeilItems;
import com.trist79.veil.common.world.VeilDimensionRegistry;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class VeilLangProvider extends LanguageProvider {

    public VeilLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add(VeilItems.CRYSTALLIZED_CHORUS_FRUIT.get(), "Crystallized Chorus Fruit");
        add(VeilBlocks.BRYOPHYTE_COVERED_PEAT.get(), "Bryophyte-Covered Peat");
        add(VeilBlocks.PEAT.get(), "Peat");
        add(VeilBlocks.GLEYSOL.get(), "Gleysol");
        add(VeilBlocks.VEILSTONE.get(), "Veilstone");
        addBiome(VeilDimensionRegistry.CRYSTAL_PEATLANDS_BIOME, "Crystal Peatlands");
        addDimension(VeilDimensionRegistry.VEIL_DIM, "The Veil");
    }

    protected void addBiome(ResourceKey<Biome> biome, String name){
        add("biome." + biome.location(), name);
    }
}