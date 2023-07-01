package net.abraxator.ceruleanvines.data;

import net.abraxator.ceruleanvines.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.FLOWERS).add(ModBlocks.DAWNBERRY_VINE.get());
        this.tag(BlockTags.SWORD_EFFICIENT).add(ModBlocks.DAWNBERRY_VINE.get());
        this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(ModBlocks.DAWNBERRY_VINE.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.DAWNBERRY_VINE.get());
    }
}
