package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class ModItemTags {
        public static final TagKey<Item> AROMA_TRIM_TEMPLATE_INGREDIENT = createItemTag("aroma_trim_template_ingredient");
        public static final TagKey<Item> CROP_SMITHING_TEMPLATES = createItemTag("crop_smithing_templates");
        public static final TagKey<Item> CROPRESSABLE_CROPS = createItemTag("cropressable_crops");
        public static final TagKey<Item> CROPRESSOR_PIECES = createItemTag("cropressor_pieces");

    }

    public static class ModBlockTags {
        public static final TagKey<Block> CROPS_FERTIABLE_BY_FBM = createBlockTag( "crops_fertiable_by_fbm");
        public static final TagKey<Block> GIANT_CROPS = createBlockTag( "giant_crops");
    }

    public static class ModBannerPatternTags {
        public static final TagKey<Block> AMBUSH_BANNER_PATTERN = createBlockTag("pattern_item/ambush");

    }

    private static TagKey<Item> createItemTag(String name) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(MoreSnifferFlowers.MOD_ID, name));
    }


    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(MoreSnifferFlowers.MOD_ID, name));
    }

}
