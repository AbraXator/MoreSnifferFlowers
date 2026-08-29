package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public interface MSFBannerPatterns {
    ResourceKey<BannerPattern> AMBUSH = create("ambush");
    ResourceKey<BannerPattern> EVIL = create("evil");

    private static ResourceKey<BannerPattern> create(String name) {
        return ResourceKey.create(Registries.BANNER_PATTERN, MoreSnifferFlowers.loc(name));
    }
    
    static void bootstrap(BootstrapContext<BannerPattern> context) {
        context.register(AMBUSH, new BannerPattern(MoreSnifferFlowers.loc("ambush"), "block.minecraft.banner.moresnifferflowers.ambush"));
        context.register(EVIL, new BannerPattern(MoreSnifferFlowers.loc("evil"), "block.minecraft.banner.moresnifferflowers.evil"));
    }
}
