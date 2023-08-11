package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTags {
    public class ModItemTags {
        public static final TagKey<Item> AROMA_TRIM_TEMPLATE_INGREDIENT = create("aroma_trim_template_ingredient");

        private static TagKey<Item> create(String name) {
            return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), MoreSnifferFlowers.loc(name));
        }
    }

    public class ModBannerPatternTags {
        public static final TagKey<BannerPattern> AMBUSH_BANNER_PATTERN = create("pattern_item/ambush");

        private static TagKey<BannerPattern> create(String name) {
            return TagKey.create(Registries.BANNER_PATTERN, MoreSnifferFlowers.loc(name));
        }
    }
}
