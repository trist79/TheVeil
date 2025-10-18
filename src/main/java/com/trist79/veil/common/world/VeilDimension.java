/*
 * The Veil Mod
 * File: VeilDimension.java
 * Description: Initializations and configs for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.world;

import java.util.OptionalLong;

import com.trist79.veil.TheVeilMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.DimensionType.MonsterSettings;

public class VeilDimension {

    public static MonsterSettings veilMonsterSettings = new MonsterSettings(
        false, false, UniformInt.of(0, 8),15);

    public static final DimensionType THE_VEIL_DIMENSION_TYPE = new DimensionType(
        OptionalLong.empty(), //fixedTime
        false, //hasSkyLight
        true, //hasCeiling
        true, //ultraWarm
        false, //natural
        1.0, //coordinateScale
        false, //bedWorks
        true, //respawnAnchorWorks
        0, //minY
        256, //height
        256, //logicalHeight
        net.minecraft.tags.BlockTags.INFINIBURN_OVERWORLD, //infiniburn
        ResourceLocation.fromNamespaceAndPath(TheVeilMod.MODID, "the_veil"), //effectsLocation
        0.0f, //ambientLight
        veilMonsterSettings //DimensionType.MonsterSettings
    );
}
