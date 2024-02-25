package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final Item DAWNBERRY_VINE_SEEDS = Registry.register(Registries.ITEM, "dawnberry_vine_seeds", new AliasedBlockItem(ModBlocks.DAWNBERRY_VINE, new Item.Settings()));
    public static final Item DAWNBERRY = Registry.register(Registries.ITEM,"dawnberry", new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.6F).snack().build())));
    public static final Item AMBUSH_SEEDS = Registry.register(Registries.ITEM,"ambush_seeds", new AliasedBlockItem(ModBlocks.AMBUSH, new Item.Settings()));
    public static final Item AMBUSH_BANNER_PATTERN = Registry.register(Registries.ITEM,"ambush_banner_pattern", new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Settings().maxCount(1)));
    public static final Item AMBER_SHARD = Registry.register(Registries.ITEM,"amber_shard", new Item(new Item.Settings()));
    public static final Item AROMA_ARMOR_TRIM_SMITHING_TABLE = Registry.register(Registries.ITEM,"aroma_armor_trim_smithing_table", SmithingTemplateItem.of(ModTrimPatterns.AROMA));
    public static final Item DRAGONFLY = Registry.register(Registries.ITEM,"dragonfly", new Item(new Item.Settings().food(ModFoods.DRAGONFLY)));
    public static final Item DYESPRIA = Registry.register(Registries.ITEM,"dyespria", new DyespriaItem(new Item.Settings().maxCount(1)));
    public static final Item BONMEELIA_SEEDS = Registry.register(Registries.ITEM,"bonmeelia_seeds", new AliasedBlockItem(ModBlocks.BONMEELIA, new Item.Settings()));
    public static final Item JAR_OF_BONMEEL = Registry.register(Registries.ITEM,"jar_of_bonmeel", new JarOfBonmeelItem(new Item.Settings()));
}
