package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SmithingTemplateItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = 
            DeferredRegister.createItems(MoreSnifferFlowers.MOD_ID);

    public static final DeferredItem<Item> DAWNBERRY_VINE_SEEDS = ITEMS.register("dawnberry_vine_seeds", () -> new ItemNameBlockItem(ModBlocks.DAWNBERRY_VINE.get(), new Item.Properties()));
    public static final DeferredItem<Item> DAWNBERRY = ITEMS.register("dawnberry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).fast().build())));
    public static final DeferredItem<Item> AMBUSH_SEEDS = ITEMS.register("ambush_seeds", () -> new ItemNameBlockItem(ModBlocks.AMBUSH.get(), new Item.Properties()));
    public static final DeferredItem<Item> AMBUSH_BANNER_PATTERN = ITEMS.register("ambush_banner_pattern", () -> new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> AMBER_SHARD = ITEMS.register("amber_shard", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> AROMA_ARMOR_TRIM_SMITHING_TABLE = ITEMS.register("aroma_armor_trim_smithing_table", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.AROMA));
    public static final DeferredItem<Item> DRAGONFLY = ITEMS.register("dragonfly", () -> new Item(new Item.Properties().food(ModFoods.DRAGONFLY)));
    public static final DeferredItem<Item> DYESPRIA = ITEMS.register("dyespria", () -> new DyespriaItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BONMEELIA_SEEDS = ITEMS.register("bonmeelia_seeds", () -> new ItemNameBlockItem(ModBlocks.BONMEELIA.get(), new Item.Properties()));
    public static final DeferredItem<Item> JAR_OF_BONMEEL = ITEMS.register("jar_of_bonmeel", () -> new JarOfBonmeelItem(new Item.Properties()));
}
