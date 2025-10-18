/*
 * The Veil Mod
 * File: VeilRegistry.java
 * Description: Registry Key class for The Veil Mod
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.loot.LootTable;

import com.trist79.veil.TheVeilMod;
public class VeilRegistry {
    public static final TagKey<Block> VEIL = TagKey.create(
        Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil_tag")
    );

    public static final ResourceKey<LootTable> TEST_DUNGEON_LOOT = ResourceKey.create(
        Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "dungeon/test_dungeon_loot_table")
    );

      // ResourceKey for the Veil dimension
    public static final ResourceKey<Level> VEIL_DIMENSION = ResourceKey.create(
            Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "veil")
    );

}
