package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface MSFTrims {

    interface Materials {
        ResourceKey<TrimMaterial> AMBER = key("amber");
        ResourceKey<TrimMaterial> GARNET = key("garnet");
        ResourceKey<TrimMaterial> NETHER_WART = key("nether_wart");
        ResourceKey<TrimMaterial> POTATO = key("potato");
        ResourceKey<TrimMaterial> WHEAT = key("wheat");
        ResourceKey<TrimMaterial> BEETROOT = key("beetroot");
        ResourceKey<TrimMaterial> CARROT = key("carrot");

        static void bootstrap(BootstrapContext<TrimMaterial> context) {
            register(context, AMBER, MSFItems.AMBER_SHARD, 0xdf910b, ItemModelIndex.GOLD);
            register(context, GARNET, MSFItems.GARNET_SHARD, 0x8d182b, ItemModelIndex.REDSTONE);
            register(context, NETHER_WART, MSFItems.CROPRESSED_NETHERWART, 0x831c20, ItemModelIndex.REDSTONE);
            register(context, POTATO, MSFItems.CROPRESSED_POTATO, 0xd9aa51, ItemModelIndex.GOLD);
            register(context, WHEAT, MSFItems.CROPRESSED_WHEAT, 0xcdb159, ItemModelIndex.GOLD);
            register(context, BEETROOT, MSFItems.CROPRESSED_BEETROOT, 0xa4272c, ItemModelIndex.REDSTONE);
            register(context, CARROT, MSFItems.CROPRESSED_CARROT, 0xe67022, ItemModelIndex.COPPER);
        }
        
        private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> materialKey, Holder<Item> ingredient, int color, ItemModelIndex itemModelIndex, Map<Holder<ArmorMaterial>, String> overrideArmorMaterials) {
            TrimMaterial trimmaterial = new TrimMaterial(materialKey.location().getPath(), ingredient, itemModelIndex.index, overrideArmorMaterials, Component.translatable(Util.makeDescriptionId("trim_material", materialKey.location())).withStyle(Style.EMPTY.withColor(color)));
            context.register(materialKey, trimmaterial);
        }

        private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> materialKey, Holder<Item> ingredient, int color, ItemModelIndex itemModelIndex) {
             register(context, materialKey, ingredient, color, itemModelIndex, Map.of());
        }

        enum ItemModelIndex{
            QUARTZ(0.1f), IRON(0.2f), NETHERITE(0.3f), REDSTONE(0.4f), COPPER(0.5f),
            GOLD(0.6f), EMERALD(0.7f), DIAMOND(0.8f), LAPIS(0.8f), AMETHYST(1.0f);

            final float index;
            ItemModelIndex(float index) {
                this.index = index;
            }
        }

        private static @NotNull ResourceKey<TrimMaterial> key(String name) {
            return ResourceKey.create(Registries.TRIM_MATERIAL, MoreSnifferFlowers.loc(name));
        }
    }

    interface Patterns {
         ResourceKey<TrimPattern> AROMA = key("aroma");
         ResourceKey<TrimPattern> CARNAGE = key("carnage");
         ResourceKey<TrimPattern> NETHER_WART = key("nether_wart");
         ResourceKey<TrimPattern> TATER = key("tater");
         ResourceKey<TrimPattern> CAROTENE = key("carotene");
         ResourceKey<TrimPattern> GRAIN = key("grain");
         ResourceKey<TrimPattern> BEAT = key("beat");

        static void bootstrap(BootstrapContext<TrimPattern> context) {
            register(context, MSFItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE, AROMA);
            register(context, MSFItems.CARNAGE_ARMOR_TRIM_SMITHING_TEMPLATE, CARNAGE);
            register(context, MSFItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE, NETHER_WART);
            register(context, MSFItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE, TATER);
            register(context, MSFItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE, CAROTENE);
            register(context, MSFItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE, GRAIN);
            register(context, MSFItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE, BEAT);
        }

        static void register(BootstrapContext<TrimPattern> context, Holder<Item> templateItem, ResourceKey<TrimPattern> trimPatternKey) {
            TrimPattern trimpattern = new TrimPattern(
                    trimPatternKey.location(),
                    templateItem,
                    Component.translatable(Util.makeDescriptionId("trim_pattern", trimPatternKey.location())),
                    false
            );
            context.register(trimPatternKey, trimpattern);
        }

        private static @NotNull ResourceKey<TrimPattern> key(String name) {
            return ResourceKey.create(Registries.TRIM_PATTERN, MoreSnifferFlowers.loc(name));
        }
    }
}
