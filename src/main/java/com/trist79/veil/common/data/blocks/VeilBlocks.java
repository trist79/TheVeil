/*
 * The Veil Mod
 * File: VeilBlocks.java
 * Description: New Blocks for Veil Mod
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.data.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.trist79.veil.TheVeilMod;
import com.trist79.veil.common.blocks.BryophyteCoveredPeat;

public class VeilBlocks {

    // Deferred register for all mod blocks
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TheVeilMod.MODID);

    // Register Blocks
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    // ----------------------------
    // Surface / Soil Blocks
    // ----------------------------
    public static final DeferredBlock<Block> BRYOPHYTE_COVERED_PEAT = BLOCKS.register(
        "bryophyte_covered_peat",
        () -> new BryophyteCoveredPeat(BlockBehaviour.Properties.of()
            .strength(0.5f)
            .sound(SoundType.GRASS)
            .friction(0.6f)
            .noOcclusion()
            .randomTicks()
        )
    );

    public static final DeferredBlock<Block> PEAT = BLOCKS.register(
        "peat",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(0.6f)
            .sound(SoundType.MUD)
            .friction(0.5f)
        )
    );

    public static final DeferredBlock<Block> GLEYSOL = BLOCKS.register(
        "gleysol",
        () -> new Block(BlockBehaviour.Properties.of()
                .strength(1.0f)
                .sound(SoundType.PACKED_MUD)
                .friction(0.7f)
                .requiresCorrectToolForDrops()
        )
    );

    // ----------------------------
    // Base Rock
    // ----------------------------
    public static final DeferredBlock<Block> VEILSTONE = BLOCKS.register(
        "veilstone",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(1.5f)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
        )
    );

    // ----------------------------
    // Optional Surface Decoration
    // ----------------------------
/*     public static final DeferredBlock<Block> BRYOPHYTE_LICHEN = BLOCKS.register("bryophyte_lichen",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(0.1f)
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .instabreak())); */

}
