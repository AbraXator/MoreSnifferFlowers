package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBannerPatterns {
    public static final ResourceKey<BannerPattern> AMBUSH = register("ambush");
    
    public static void bootstrap(BootstrapContext<BannerPattern> context) {
        context.register(AMBUSH, new BannerPattern(MoreSnifferFlowers.loc("ambush"), "banner.moresnifferflowers.ambush"));
    }

    private static ResourceKey<BannerPattern> register(String name) {
        return ResourceKey.create(Registries.BANNER_PATTERN, MoreSnifferFlowers.loc(name));
    }
}
