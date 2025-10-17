/*
 * The Veil Mod
 * File: TestDungeonLootSubProvider.java
 * Description: Test Subprovider for dungeon loot tables
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.data.loot.subproviders;

import java.util.function.BiConsumer;
import javax.annotation.Nonnull;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import com.trist79.veil.TheVeilMod;

public class TestDungeonLootSubProvider implements LootTableSubProvider {

    // The parameter is provided by the lambda (see below). It can be stored and used to lookup other registry entries.
    public TestDungeonLootSubProvider(HolderLookup.Provider lookupProvider) {
    }

    @Override
    public void generate(@Nonnull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {

        ResourceKey<LootTable> key = ResourceKey.create(
            Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "dungeon/test_dungeon_loot_table")
        );

        // LootTable.lootTable() returns a loot table builder we can add loot tables to.
        consumer.accept(
            key,
            //ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "dungeon/test_dungeon_loot_table"),
            LootTable.lootTable()
                // Add a loot table-level loot function. This example uses a number provider (see below).
                // Add a loot pool.
                .withPool(
                    LootPool.lootPool()
                        .when(WeatherCheck.weather().setRaining(true))
                        .setRolls(UniformGenerator.between(5, 9))
                        .setBonusRolls(ConstantValue.exactly(1))
                        // Add a loot entry. This example returns an item loot entry. See below for more loot entries.
                        .add(LootItem.lootTableItem(net.minecraft.world.item.Items.DIAMOND)
                            // Add a loot entry-level loot function. This example sets the item count.
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))
                        )
                )
        );
    }
}