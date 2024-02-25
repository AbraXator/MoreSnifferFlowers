package net.abraxator.moresnifferflowers.data;

import java.util.List;
import java.util.Map;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.loottable.LootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

public class ModLoottableProvider extends LootTableProvider {
    public ModLoottableProvider(DataOutput pOutput) {
        super(pOutput, LootTables.getAll(), List.of(new LootTypeGenerator(ModBlockLoottableProvider::new, LootContextTypes.BLOCK)));
    }

    @Override
    protected void validate(Map<Identifier, LootTable> map, LootTableReporter validationcontext) {

    }
}
