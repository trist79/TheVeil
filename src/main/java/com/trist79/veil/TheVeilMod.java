/*
 * The Veil Mod
 * File: TheVeilMod.java
 * Description: Main class for The Veil Mod
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil;

import java.util.Optional;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import com.trist79.veil.common.registry.VeilItemRegistry;
import com.trist79.veil.common.world.VeilDimension;
import com.trist79.veil.common.world.VeilDimensionRegistry;

import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.LevelStem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(value = TheVeilMod.MODID)
public class TheVeilMod {
    public static final String MODID = "theveil";
    public static final Logger LOGGER = LogUtils.getLogger();
    static {
        LOGGER.info("STATIC BLOCK IN THE VEIL MOD FIRED");
    }
    public TheVeilMod(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);
        //modEventBus.addListener(VeilDataGenerators::gatherData);
        //NeoForge.EVENT_BUS.register(this);
        VeilItemRegistry.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        WritableRegistry<LevelStem> stemRegistry = (WritableRegistry<LevelStem>) server.registryAccess().registryOrThrow(Registries.LEVEL_STEM);

        if (stemRegistry.get(VeilDimensionRegistry.THE_VEIL_STEM.location()) != null) {
            System.out.println("Veil stem already registered.");
            return;
        }

        // Use your bootstrap method to create the LevelStem
        ServerLevel overworld = server.getLevel(server.overworld().dimension()); // just need any server level
        LevelStem runtimeStem = VeilDimension.bootstrapRuntimeStem(overworld);
        // Register the stem at runtime
        RegistrationInfo runtimeInfo = new RegistrationInfo(Optional.empty(), Lifecycle.stable());
        stemRegistry.register(
                VeilDimensionRegistry.THE_VEIL_STEM,
                runtimeStem,
                runtimeInfo
        );

        System.out.println("Veil dimension stem registered at runtime!");

        System.out.println("Veil dimension stem registered at runtime!");



        if (server.getLevel(VeilDimensionRegistry.VEIL_DIM) != null) {
            TheVeilMod.LOGGER.info("✅ Veil dimension loaded successfully.");
        } else {
            TheVeilMod.LOGGER.error("❌ Veil dimension missing! Check your datapack output.");
        }
    }
}

