package net.abraxator.moresnifferflowers.datagen.loot;

import net.abraxator.moresnifferflowers.init.MSFLoot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MSFLootGenerator {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        return new LootTableProvider(
                output,
                MSFLoot.LootTables.all(),
                List.of(
                        new LootTableProvider.SubProviderEntry(MSFBlockLoot::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(MSFArcheologyLoot::new, LootContextParamSets.ARCHAEOLOGY),
                        new LootTableProvider.SubProviderEntry(MSFChestLoot::new, LootContextParamSets.CHEST),
                        new LootTableProvider.SubProviderEntry(MSFEntityLoot::new, LootContextParamSets.ENTITY)
                ),
                registries
        );
    }
}
