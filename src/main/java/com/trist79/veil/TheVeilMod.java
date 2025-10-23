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
import com.trist79.veil.common.data.blocks.VeilBlocks;
import com.trist79.veil.common.items.VeilItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(value = TheVeilMod.MODID)
public class TheVeilMod {
    public static final String MODID = "theveil";
    public static final Logger LOGGER = LogUtils.getLogger();
    static {
        LOGGER.info("Veil Mod Initializing...");
    }
    public TheVeilMod(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(TheVeilMod.class);
        modEventBus.addListener(this::commonSetup);
        VeilItems.register(modEventBus);
        VeilBlocks.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("Veil Mod Common Setup Starting");
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {

    }

}

