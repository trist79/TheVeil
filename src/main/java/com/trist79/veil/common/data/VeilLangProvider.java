package com.trist79.veil.common.data;

import com.trist79.veil.common.registry.VeilItemRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class VeilLangProvider extends LanguageProvider {

    public VeilLangProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add(VeilItemRegistry.CRYSTALLIZED_CHORUS_FRUIT.get(), "Crystallized Chorus Fruit");
    }
}