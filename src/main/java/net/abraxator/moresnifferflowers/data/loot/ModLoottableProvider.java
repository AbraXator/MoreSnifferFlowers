package net.abraxator.moresnifferflowers.data.loot;

import net.abraxator.moresnifferflowers.data.loot.ModBlockLoottableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModLoottableProvider extends LootTableProvider {
    public ModLoottableProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> future) {
        super(pOutput, BuiltInLootTables.all(), List.of(new SubProviderEntry(ModBlockLoottableProvider::new, LootContextParamSets.BLOCK)), future);
    }

    @Override
    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
        
    }
}
