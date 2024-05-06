package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModTags {
    public static class ModItemTags {
        public static final TagKey<Item> AROMA_TRIM_TEMPLATE_INGREDIENT = create(Registries.ITEM ,"aroma_trim_template_ingredient");
        public static final TagKey<Item> CROP_SMITHING_TEMPLATES = create(Registries.ITEM ,"crop_smithing_templates");
        public static final TagKey<Item> CROPRESSABLE_CROPS = create(Registries.ITEM ,"cropressable_crops");
        public static final TagKey<Item> CROPRESSOR_PIECES = create(Registries.ITEM ,"cropressor_pieces");
        public static final TagKey<Item> REBREWING_STAND_INGREDIENTS = create(Registries.ITEM ,"rebrewing_stand_ingredients");
        public static final TagKey<Item> CROPRESSED_CROPS = create(Registries.ITEM ,"cropressed_crops");
        public static final TagKey<Item> REBREWED_POTIONS = create(Registries.ITEM ,"rebrewed_potions");

    }

    public static class ModBlockTags {
        public static final TagKey<Block> CROPS_FERTIABLE_BY_FBM = create(Registries.BLOCK, "crops_fertiable_by_fbm");
        public static final TagKey<Block> GIANT_CROPS = create(Registries.BLOCK, "giant_crops");
    }

    public static class ModBannerPatternTags {
        public static final TagKey<BannerPattern> AMBUSH_BANNER_PATTERN = create(Registries.BANNER_PATTERN, "pattern_item/ambush");

    }

    private static <T extends Object> TagKey<T> create(ResourceKey<Registry<T>> registry, String name){
        return TagKey.create(registry, MoreSnifferFlowers.loc(name));
    }
}