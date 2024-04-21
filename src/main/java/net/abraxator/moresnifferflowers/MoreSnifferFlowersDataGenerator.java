package net.abraxator.moresnifferflowers;

import net.abraxator.moresnifferflowers.data.ModItemTagsProvider;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MoreSnifferFlowersDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModItemTagsProvider::new);
    }
}
