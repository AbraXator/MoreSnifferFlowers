package net.abraxator.moresnifferflowers.data;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.loottable.LootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

public class ModLootGenerator extends LootTableProvider {
    public ModLootGenerator(DataOutput output, Set<Identifier> lootTableIds, List<LootTypeGenerator> lootTypeGenerators) {
        super(output, lootTableIds, lootTypeGenerators);
    }
//    public ModLootGenerator(DataOutput pOutput) {
//        super(pOutput, Set.of(), List.of(
//                new LootTableProvider.LootTypeGenerator(ModBlockLoottableProvider::new, LootContextTypes.BLOCK)
//        ));
//    }
//
//    @Override
//    protected void validate(Map<Identifier, LootTable> map, LootTableReporter validationcontext) {
//
//    }
}
