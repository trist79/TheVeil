/*
 * The Veil Mod
 * File: VeilBlockTagsProvider.java
 * Description: Block Tags json data provider
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.data.tags;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import com.trist79.veil.TheVeilMod;
import static com.trist79.veil.common.registry.VeilRegistry.*;

public class VeilBlockTagsProvider extends BlockTagsProvider {

    public VeilBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper) {
        super(output, provider, TheVeilMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        this.tag(VEIL)
            .add(Blocks.DIRT, Blocks.STONE);
    }

}
