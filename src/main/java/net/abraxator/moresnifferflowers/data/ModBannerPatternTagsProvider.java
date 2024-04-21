package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.TagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBannerPatternTagsProvider extends TagProvider<BannerPattern> {
    protected ModBannerPatternTagsProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> provider) {
        super(output, RegistryKeys.BANNER_PATTERN, provider);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup pProvider) {
        getOrCreateTagBuilder(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN);
    }
}
