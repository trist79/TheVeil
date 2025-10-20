/*
 * The Veil Mod
 * File: VeilRecipeProvider.java
 * Description: Recipe json data provider
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.data;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;

import com.trist79.veil.common.registry.VeilItemRegistry;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

public class VeilRecipeProvider extends RecipeProvider {

    // Construct the provider to run
    protected VeilRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.DIAMOND)
            .requires(Items.STICK)
            .unlockedBy("has_stick", has(Items.STICK))
            .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VeilItemRegistry.CRYSTALLIZED_CHORUS_FRUIT.get())
            .pattern(" A ")
            .pattern("ABA")
            .pattern(" A ")
            .define('A', Items.AMETHYST_SHARD)
            .define('B', Items.CHORUS_FRUIT)
            .unlockedBy("has_chorus_fruit", has(Items.CHORUS_FRUIT))
            .save(output);
    }
}
