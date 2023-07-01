package net.abraxator.ceruleanvines.data;

import net.abraxator.ceruleanvines.CeruleanVines;
import net.abraxator.ceruleanvines.init.ModBlocks;
import net.abraxator.ceruleanvines.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, CeruleanVines.MOD_ID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.FLOWERS).add(item(ModBlocks.DAWNBERRY_VINE));
        this.tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(ModItems.DAWNBERRY_VINE_SEEDS.get());
        this.tag(Tags.Items.SEEDS).add(ModItems.DAWNBERRY_VINE_SEEDS.get());
    }

    private Item item(RegistryObject<Block> object){
        return object.get().asItem();
    }
}
