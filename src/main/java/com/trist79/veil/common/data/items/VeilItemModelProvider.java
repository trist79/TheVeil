package com.trist79.veil.common.data.items;

import com.trist79.veil.TheVeilMod;
import com.trist79.veil.common.items.VeilItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VeilItemModelProvider extends ItemModelProvider {

    public VeilItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, TheVeilMod.MODID, helper);
    }

    @Override
    protected void registerModels() {
        basicItem(VeilItems.CRYSTALLIZED_CHORUS_FRUIT.get());
    }
}