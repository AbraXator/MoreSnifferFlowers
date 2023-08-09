package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModTags {
    public class ModBannerPatternTags {
        public static final TagKey<BannerPattern> AMBUSH_BANNER_PATTERN = create("pattern_item/ambush");

        private static TagKey<BannerPattern> create(String name) {
            return TagKey.create(Registries.BANNER_PATTERN, MoreSnifferFlowers.loc(name));
        }
    }
}
