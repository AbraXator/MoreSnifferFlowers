package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class ModTrimPatterns {
    public static final RegistryKey<ArmorTrimPattern> AROMA = RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier("aroma"));
    public static final RegistryKey<ArmorTrimPattern> TATER = RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier("tater"));
    public static final RegistryKey<ArmorTrimPattern> CAROTENE = RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier("carotene"));
    public static final RegistryKey<ArmorTrimPattern> GRAIN = RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier("grain"));
    public static final RegistryKey<ArmorTrimPattern> NETHER_WART = RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier("nether_wart"));
    public static final RegistryKey<ArmorTrimPattern> BEAT = RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier("beat"));

    public static void bootstrap(Registerable<ArmorTrimPattern> context) {
        register(context, ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE, AROMA);
        register(context, ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE, TATER);
        register(context, ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE, CAROTENE);
        register(context, ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE, GRAIN);
        register(context, ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE, NETHER_WART);
        register(context, ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE, BEAT);

    }

    private static void register(Registerable<ArmorTrimPattern> context, Item item, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern trimPattern = new ArmorTrimPattern(key.getValue(), Registries.ITEM.getEntry(item), Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())), false);
        context.register(key, trimPattern);
    }
}
