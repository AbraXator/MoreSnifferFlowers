package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapsProvider extends DataMapProvider {
    public ModDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void gather() {
        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(ModItems.DAWNBERRY_VINE_SEEDS, new Compostable(0.3F), false);
        compostables.add(ModItems.DAWNBERRY, new Compostable(0.3F), false);
        compostables.add(ModItems.AMBUSH_SEEDS, new Compostable(0.3F), false);
        compostables.add(ModBlocks.CAULORFLOWER.asItem().builtInRegistryHolder(), new Compostable(0.4F), false);
        compostables.add(ModItems.DYESPRIA_SEEDS, new Compostable(0.4F), false);
        compostables.add(ModItems.BONMEELIA_SEEDS, new Compostable(0.5F), false);
        compostables.add(ModItems.CROPRESSED_BEETROOT, new Compostable(1.0F), false);
        compostables.add(ModItems.CROPRESSED_NETHERWART, new Compostable(1.0F), false);
        compostables.add(ModItems.CROPRESSED_WHEAT, new Compostable(1.0F), false);
        compostables.add(ModItems.CROPRESSED_POTATO, new Compostable(1.0F), false);
        compostables.add(ModItems.CROPRESSED_CARROT, new Compostable(1.0F), false);
    }
}
