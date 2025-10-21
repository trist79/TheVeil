/*
 * The Veil Mod
 * File: VeilDataGenerators.java
 * Description: Data Generator aggregator for The Veil Mod
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.trist79.veil.TheVeilMod;
import com.trist79.veil.common.data.items.VeilItemModelProvider;
import com.trist79.veil.common.data.loot.VeilLootTableProvider;
import com.trist79.veil.common.data.tags.VeilBlockTagsProvider;
import com.trist79.veil.common.world.VeilDimension;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

@EventBusSubscriber(modid = TheVeilMod.MODID)
public class VeilDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        System.out.println("GATHERDATA EVENT FIRED");
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeServer(), new VeilRecipeProvider(output, provider));
        generator.addProvider(event.includeServer(), new VeilLootTableProvider(output, provider));
        generator.addProvider(event.includeServer(), new VeilBlockTagsProvider(output, provider, existingFileHelper));
        generator.addProvider(event.includeServer(), new VeilItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new VeilLangProvider(output, TheVeilMod.MODID, net.minecraft.locale.Language.DEFAULT));


        RegistrySetBuilder builder = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, VeilDimension::bootstrapDimType)
            .add(Registries.BIOME, VeilDimension::bootstrapBiomes)
            .add(Registries.NOISE_SETTINGS, VeilDimension::bootstrapNoiseSettings)
            .add(Registries.LEVEL_STEM, VeilDimension::bootstrapLevelStem);

        generator.addProvider(
            true, // includeServer
            new DatapackBuiltinEntriesProvider(
                output,
                provider,
                builder,
                Set.of(TheVeilMod.MODID)
            )
        );
    }
}
