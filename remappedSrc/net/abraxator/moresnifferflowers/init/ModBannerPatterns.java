package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBannerPatterns {
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS =
            DeferredRegister.create(RegistryKeys.BANNER_PATTERN, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<BannerPattern, BannerPattern> AMBUSH = BANNER_PATTERNS.register("ambush", () -> new BannerPattern("msfa"));
}
