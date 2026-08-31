package net.abraxator.moresnifferflowers.datagen.tag;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFBannerPatterns;
import net.abraxator.moresnifferflowers.init.MSFTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBannerPatternTagsProvider extends TagsProvider<BannerPattern> {
    public ModBannerPatternTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
        super(output, Registries.BANNER_PATTERN, provider, MoreSnifferFlowers.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MSFTags.BannerTags.AMBUSH_BANNER_PATTERN).add(MSFBannerPatterns.AMBUSH);
        tag(MSFTags.BannerTags.EVIL_BANNER_PATTERN).add(MSFBannerPatterns.EVIL);
    }
}
