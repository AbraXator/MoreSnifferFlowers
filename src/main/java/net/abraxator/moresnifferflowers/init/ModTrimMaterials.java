package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import java.util.Map;

public class ModTrimMaterials {
    public static final RegistryKey<ArmorTrimMaterial> AMBER = RegistryKey.of(RegistryKeys.TRIM_MATERIAL, new Identifier("amber"));

    public static void bootstrap(Registerable<ArmorTrimMaterial> context) {
        register(context, AMBER, Registries.ITEM.getEntry(ModItems.AMBER_SHARD), Style.EMPTY.withColor(TextColor.parse("#df910b").get().orThrow()), 0.6F, Map.of());
    }

    private static void register(Registerable<ArmorTrimMaterial> p_268244_, RegistryKey<ArmorTrimMaterial> p_268139_, RegistryEntry<Item> p_268311_, Style p_268232_, float p_268197_, Map<ArmorMaterials, String> p_268352_) {
        ArmorTrimMaterial trimmaterial = new ArmorTrimMaterial(p_268139_.getValue().getPath(), p_268311_, p_268197_, Map.of(), Text.translatable(Util.createTranslationKey("trim_material", p_268139_.getValue())).fillStyle(p_268232_));
        p_268244_.register(p_268139_, trimmaterial);
    }
}
