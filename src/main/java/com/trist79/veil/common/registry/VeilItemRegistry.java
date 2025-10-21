/*
 * The Veil Mod
 * File: VeilItemRegistry.java
 * Description: Registration Keys and Registrations for Veil Items
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.registry;

import java.util.function.Supplier;

import com.trist79.veil.TheVeilMod;
import com.trist79.veil.common.items.VeilItems;
import com.trist79.veil.common.items.food.CrystallizedChorusFruit;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VeilItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TheVeilMod.MODID);

    public static final Supplier<Item> CRYSTALLIZED_CHORUS_FRUIT = VeilItemRegistry.ITEMS.registerItem(
        "crystallized_chorus_fruit", CrystallizedChorusFruit::new, new CrystallizedChorusFruit.Properties().food(VeilItems.crystallizedChorusFruitProperties)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
