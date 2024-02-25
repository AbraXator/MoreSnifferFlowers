package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class ModItemTags {
        public static final TagKey<Item> AROMA_TRIM_TEMPLATE_INGREDIENT = create(RegistryKeys.ITEM ,"aroma_trim_template_ingredient");
    }

    public static class ModBlockTags {
        public static final TagKey<Block> CROPS_FERTIABLE_BY_FBM = create(RegistryKeys.BLOCK, "crops_fertiable_by_fbm");
        public static final TagKey<Block> GIANT_CROPS = create(RegistryKeys.BLOCK, "giant_crops");
    }

    public static class ModBannerPatternTags {
        public static final TagKey<BannerPattern> AMBUSH_BANNER_PATTERN = create(RegistryKeys.BANNER_PATTERN, "pattern_item/ambush");

    }

    private static <T extends Object> TagKey<T> create(RegistryKey<Registry<T>> registry, String name){
        return TagKey.of(registry, MoreSnifferFlowers.loc(name));
    }
}
