package com.trist79.veil.common.data;

import com.trist79.veil.common.data.blocks.VeilBlocks;
import com.trist79.veil.common.items.VeilItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class VeilLangProvider extends LanguageProvider {

    public VeilLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add(VeilItems.CRYSTALLIZED_CHORUS_FRUIT.get(), "Crystallized Chorus Fruit");
        add(VeilBlocks.BRYOPHYTE_COVERED_PEAT.get(), "Bryophyte-Covered Peat");
        add(VeilBlocks.PEAT.get(), "Peat");
        add(VeilBlocks.GLEYSOL.get(), "Gleysol");
        add(VeilBlocks.VEILSTONE.get(), "Veilstone");
    }
}