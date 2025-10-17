/*
 * The Veil Mod
 * File: VeilLootTableProvider.java
 * Description: Recipe json data provider
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.data.loot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

import com.trist79.veil.common.data.loot.subproviders.TestDungeonLootSubProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;


public class VeilLootTableProvider extends LootTableProvider {
    public VeilLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(
            output,
            Set.of(),
            List.of(new SubProviderEntry(TestDungeonLootSubProvider::new, LootContextParamSets.EMPTY)),
            lookupProvider
        );
    }

    @SubscribeEvent
    public static void onGatherData(@Nonnull GatherDataEvent event) {
        event.createProvider(VeilLootTableProvider::new);
    }
}