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
import com.trist79.veil.common.data.loot.VeilLootTableProvider;
import com.trist79.veil.common.data.tags.VeilBlockTagsProvider;
import com.trist79.veil.common.world.VeilDimension;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
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

        // Add Providers
        generator.addProvider(event.includeServer(), new VeilRecipeProvider(output, provider));
        generator.addProvider(event.includeServer(), new VeilLootTableProvider(output, provider));
        generator.addProvider(event.includeServer(), new VeilBlockTagsProvider(output, provider, existingFileHelper));

        RegistrySetBuilder builder = new RegistrySetBuilder()
                .add(Registries.LEVEL_STEM, VeilDimension::bootstrapLevelStem)
                .add(Registries.DIMENSION_TYPE, VeilDimension::bootstrapDimType)
                .add(Registries.BIOME, VeilDimension::bootstrapBiomes);

        generator.addProvider(event.includeServer(), new DataProvider.Factory<DatapackBuiltinEntriesProvider>() {
        @Override
        public DatapackBuiltinEntriesProvider create(PackOutput packOutput) {
            return new DatapackBuiltinEntriesProvider(
                packOutput,
                provider,
                builder,
                Set.of(TheVeilMod.MODID)
            );
        }
});
    }
}
