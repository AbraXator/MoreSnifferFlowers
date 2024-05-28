package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.DAWNBERRY_VINE_SEEDS, new Compostable(0.3F), false)
                .add(ModItems.DAWNBERRY, new Compostable(0.3F), false)
                .add(ModItems.AMBUSH_SEEDS, new Compostable(0.3F), false)
                .add(Holder.direct(ModBlocks.CAULORFLOWER.asItem()), new Compostable(0.4F), false)
                .add(ModItems.DYESPRIA_SEEDS, new Compostable(0.4F), false)
                .add(ModItems.BONMEELIA_SEEDS, new Compostable(0.5F), false)
                .add(ModItems.CROPRESSED_BEETROOT, new Compostable(1.0F), false)
                .add(ModItems.CROPRESSED_NETHERWART, new Compostable(1.0F), false)
                .add(ModItems.CROPRESSED_WHEAT, new Compostable(1.0F), false)
                .add(ModItems.CROPRESSED_POTATO, new Compostable(1.0F), false)
                .add(ModItems.CROPRESSED_CARROT, new Compostable(1.0F), false)
                .add(Holder.direct(ModBlocks.CORRUPTED_LEAVES.asItem()), new Compostable(0.4F), false)
                .add(Holder.direct(ModBlocks.CORRUPTED_SAPLING.asItem()), new Compostable(0.4F), false);
    }
}
