/*
 * The Veil Mod
 * File: TheVeilMod.java
 * Description: Main class for The Veil Mod
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import com.trist79.veil.common.data.VeilDataGenerators;
import com.trist79.veil.common.registry.VeilRegistry;
import com.trist79.veil.common.world.VeilChunkGenerator;
import com.trist79.veil.common.world.VeilWorldSetup;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(value = TheVeilMod.MODID, dist = Dist.DEDICATED_SERVER)
public class TheVeilMod {
    public static final String MODID = "theveil";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TheVeilMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(VeilDataGenerators::gatherData);

        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        var server = event.getServer();
        if (server.getLevel(VeilWorldSetup.THE_VEIL_KEY) != null) {
            LOGGER.info("Veil dimension already exists.");
            return;
        }
        LOGGER.info("Creating Veil dimension...");
        Holder<Biome> plainsHolder = server.registryAccess().registryOrThrow(Registries.BIOME).getHolder(Biomes.PLAINS).orElseThrow(() -> new IllegalStateException("Missing PLAINS biome"));
        FixedBiomeSource biomeSource = new FixedBiomeSource(plainsHolder);
        var generator = new VeilChunkGenerator(biomeSource, holder -> new BiomeGenerationSettings.Builder(null, null).build());
        Holder<DimensionType> veilDimTypeHolder = server.registryAccess()
            .registryOrThrow(Registries.DIMENSION_TYPE)
            .getHolder(VeilWorldSetup.THE_VEIL_DIM_TYPE)
            .orElseThrow(() -> new IllegalStateException("Veil DimensionType not found"));
        // Create the LevelStem with your dimension type
        LevelStem veilStem = new LevelStem(veilDimTypeHolder, generator);

var dimensionRegistry = server.registryAccess()
            .registryOrThrow(Registries.DIMENSION)
            .asRegistry(); // <-- NeoForge 1.21.x method

    if (!dimensionRegistry.containsKey(VeilWorldSetup.THE_VEIL_KEY)) {
        dimensionRegistry.registerOrOverride(
                VeilWorldSetup.THE_VEIL_KEY,
                veilStem,
                net.minecraft.core.Holder.Reference.BUILTIN
        );
        LOGGER.info("Veil dimension registered in server registry.");
    } else {
        LOGGER.warn("Veil dimension key already present in registry!");
    }

    LOGGER.info("Veil dimension created!");
}
