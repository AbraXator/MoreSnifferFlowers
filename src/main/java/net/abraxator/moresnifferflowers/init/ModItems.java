package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<Item> DAWNBERRY_VINE_SEEDS = ITEMS.register("dawnberry_vine_seeds", () -> new ItemNameBlockItem(ModBlocks.DAWNBERRY_VINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DAWNBERRY = ITEMS.register("dawnberry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).fast().build())));
    public static final RegistryObject<Item> AMBUSH_SEEDS = ITEMS.register("ambush_seeds", () -> new ItemNameBlockItem(ModBlocks.AMBUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item> AMBUSH_BANNER_PATTERN = ITEMS.register("ambush_banner_pattern", () -> new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Properties()));
    public static final RegistryObject<Item> AMBER_SHARD = ITEMS.register("amber_shard", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AROMA_ARMOR_TRIM_SMITHING_TABLE = ITEMS.register("aroma_armor_trim_smithing_table", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.AROMA));
    public static final RegistryObject<Item> DRAGONFLY = ITEMS.register("dragonfly", () -> new Item(new Item.Properties().food(ModFoods.DRAGONFLY)));
}
