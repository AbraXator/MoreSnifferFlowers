package net.abraxator.moresnifferflowers.data.loot;

import net.abraxator.moresnifferflowers.init.ModBuiltintLoottables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.*;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModLoottableProivder {
    public static LootTableProvider create(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        return new LootTableProvider(
                pOutput,
                ModBuiltintLoottables.all(),
                List.of(
                        new LootTableProvider.SubProviderEntry(ModArcheologyLoot::new, LootContextParamSets.ARCHAEOLOGY)
                ),
                pRegistries
        );
    }
}
