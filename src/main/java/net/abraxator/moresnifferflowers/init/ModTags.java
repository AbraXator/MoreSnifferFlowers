package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {
    public static class ModItemTags {
        public static final TagKey<Item> AROMA_TRIM_TEMPLATE_INGREDIENT = create(Registries.ITEM ,"aroma_trim_template_ingredient");
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
