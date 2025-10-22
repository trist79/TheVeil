package com.trist79.veil.common.data.blocks;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import com.trist79.veil.TheVeilMod;

public class VeilBlockProvider extends BlockStateProvider {

    public VeilBlockProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, TheVeilMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ModelFile bryophyteModelFile = models().cubeBottomTop(
            "bryophyte_covered_peat",
            modLoc("block/bryophyte_covered_peat_side"),
            modLoc("block/simpleblock/peat"),
            modLoc("block/bryophyte_covered_peat_top")
        );
        ModelFile peatModelFile = models().cubeAll("peat", modLoc("block/simpleblock/peat"));
        ModelFile gleysonModelFile = models().cubeAll("gleysol", modLoc("block/simpleblock/gleysol"));
        ModelFile veilstoneModelFile = models().cubeAll("veilstone", modLoc("block/simpleblock/veilstone"));


        simpleBlock(VeilBlocks.BRYOPHYTE_COVERED_PEAT.get(), bryophyteModelFile);
        simpleBlock(VeilBlocks.PEAT.get(), peatModelFile);
        simpleBlock(VeilBlocks.GLEYSOL.get(), gleysonModelFile);
        simpleBlock(VeilBlocks.VEILSTONE.get(), veilstoneModelFile);

        simpleBlockItem(VeilBlocks.BRYOPHYTE_COVERED_PEAT.get(), bryophyteModelFile);
        simpleBlockItem(VeilBlocks.PEAT.get(), peatModelFile);
        simpleBlockItem(VeilBlocks.GLEYSOL.get(), gleysonModelFile);
        simpleBlockItem(VeilBlocks.VEILSTONE.get(), veilstoneModelFile);
    }
}