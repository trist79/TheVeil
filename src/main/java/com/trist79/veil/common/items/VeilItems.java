/*
 * The Veil Mod
 * File: VeilItems.java
 * Description: Registration Keys and Registrations for Veil Items
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.items;

import java.util.function.Supplier;

import com.trist79.veil.TheVeilMod;
import com.trist79.veil.common.data.blocks.VeilBlocks;
import com.trist79.veil.common.items.food.CrystallizedChorusFruit;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VeilItems {

    // Create Deferred Register
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TheVeilMod.MODID);
    //public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(TheVeilMod.MODID);
    // Build Item Properties
    public static final FoodProperties crystallizedChorusFruitProperties = new FoodProperties.Builder().nutrition(1).saturationModifier(0.2f).alwaysEdible().build();

    // Register Items
    public static final Supplier<Item> CRYSTALLIZED_CHORUS_FRUIT = VeilItems.ITEMS.registerItem(
        "crystallized_chorus_fruit", CrystallizedChorusFruit::new, new CrystallizedChorusFruit.Properties().food(VeilItems.crystallizedChorusFruitProperties)
    );

    // Register Block Items
    public static final DeferredItem<BlockItem> PEAT = ITEMS.registerSimpleBlockItem(
        "peat", VeilBlocks.PEAT::get, new Item.Properties()
    );
    public static final DeferredItem<BlockItem> BRYOPHYTE_COVERED_PEAT = ITEMS.registerSimpleBlockItem(
        "bryophyte_covered_peat", VeilBlocks.BRYOPHYTE_COVERED_PEAT::get, new Item.Properties()
    );

    public static final DeferredItem<BlockItem> GLEYSOL = ITEMS.registerSimpleBlockItem(
        "gleysol", VeilBlocks.GLEYSOL::get, new Item.Properties()
    );

    public static final DeferredItem<BlockItem> VEILSTONE = ITEMS.registerSimpleBlockItem(
        "veilstone", VeilBlocks.VEILSTONE::get, new Item.Properties()
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        //BLOCK_ITEMS.register(eventBus);
    }
}
