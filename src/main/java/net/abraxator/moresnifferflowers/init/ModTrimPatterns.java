package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

public class ModTrimPatterns {
    public static final ResourceKey<TrimPattern> AROMA = ResourceKey.create(Registries.TRIM_PATTERN, MoreSnifferFlowers.loc("aroma"));

    public static void bootstrap(BootstapContext<TrimPattern> context) {
        register(context, ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get(), AROMA);
    }

    private static void register(BootstapContext<TrimPattern> context, Item item, ResourceKey<TrimPattern> key) {
        TrimPattern trimPattern = new TrimPattern(key.location(), BuiltInRegistries.ITEM.wrapAsHolder(item), Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())), false);
        context.register(key, trimPattern);
    }
}
