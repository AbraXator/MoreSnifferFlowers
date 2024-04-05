package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class ModTrimMaterials {
    public static final ResourceKey<TrimMaterial> AMBER = ResourceKey.create(Registries.TRIM_MATERIAL, MoreSnifferFlowers.loc("amber"));
    public static final ResourceKey<TrimMaterial> NETHER_WART = ResourceKey.create(Registries.TRIM_MATERIAL, MoreSnifferFlowers.loc("nether_wart"));

    public static void bootstrap(BootstapContext<TrimMaterial> context) {
        register(context, AMBER, ModItems.AMBER_SHARD.getHolder().get(), Style.EMPTY.withColor(TextColor.parseColor("#df910b")), 0.6F, Map.of());
        register(context, NETHER_WART, ModItems.CROPRESSED_NETHERWART.getHolder().get(), Style.EMPTY.withColor(TextColor.parseColor("#831c20")), 0.6F, Map.of());
    }

    private static void register(BootstapContext<TrimMaterial> p_268244_, ResourceKey<TrimMaterial> p_268139_, Holder<Item> p_268311_, Style p_268232_, float p_268197_, Map<ArmorMaterials, String> p_268352_) {
        TrimMaterial trimmaterial = new TrimMaterial(p_268139_.location().getPath(), p_268311_, p_268197_, Map.of(), Component.translatable(Util.makeDescriptionId("trim_material", p_268139_.location())).withStyle(p_268232_));
        p_268244_.register(p_268139_, trimmaterial);
    }
}
