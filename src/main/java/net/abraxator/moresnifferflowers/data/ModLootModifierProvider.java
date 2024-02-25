package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.lootmodifers.AddItemsModifier;
import net.minecraft.data.DataOutput;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;

public class ModLootModifierProvider extends GlobalLootModifierProvider {
    public ModLootModifierProvider(DataOutput output) {
        super(output, MoreSnifferFlowers.MOD_ID);
    }

    @Override
    protected void start() {
        add("seeds_from_sniffing", new AddItemsModifier(new LootCondition[] {
                new LootTableIdCondition.Builder(new Identifier("gameplay/sniffer_digging")).build()
        }, List.of(ModItems.DAWNBERRY_VINE_SEEDS.get(), ModItems.AMBUSH_SEEDS.get(), ModBlocks.CAULORFLOWER.get().asItem(), ModItems.BONMEELIA_SEEDS.get())));
    }

}
