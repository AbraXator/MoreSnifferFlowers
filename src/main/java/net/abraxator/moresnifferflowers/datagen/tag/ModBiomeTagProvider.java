package net.abraxator.moresnifferflowers.datagen.tag;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends TagsProvider<Biome> {
    public ModBiomeTagProvider(PackOutput p_275432_, CompletableFuture<HolderLookup.Provider> p_275222_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275432_, Registries.BIOME, p_275222_, MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(MSFTags.ModBiomeTags.HAS_SWAMP_SNIFFER_TEMPLE).add(Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
    }
}
