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
import com.trist79.veil.common.data.VeilDataGenerators;
import com.trist79.veil.common.world.VeilDimensionRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

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

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        TheVeilMod.LOGGER.info("The Veil: Server starting — registering Veil dimension...");
        VeilDimensionRegistry.registerVeilDimensionRuntime(server);
    }
}

