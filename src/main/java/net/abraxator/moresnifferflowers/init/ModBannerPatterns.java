package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBannerPatterns {
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS =
            DeferredRegister.create(Registries.BANNER_PATTERN, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<BannerPattern, BannerPattern> AMBUSH = BANNER_PATTERNS.register("ambush", () -> new BannerPattern(MoreSnifferFlowers.loc("msfa"), "msfa"));
}
