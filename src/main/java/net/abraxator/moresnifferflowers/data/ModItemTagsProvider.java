package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagProvider {
    public ModItemTagsProvider(DataOutput p_275343_, CompletableFuture<RegistryWrapper.WrapperLookup> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void configure(RegistryWrapper.WrapperLookup pProvider) {
        this.getOrCreateTagBuilder(ItemTags.FLOWERS).add(item(ModBlocks.DAWNBERRY_VINE), item(ModBlocks.AMBUSH), item(ModBlocks.CAULORFLOWER));
        this.getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(ModItems.DAWNBERRY_VINE_SEEDS.get());
        this.getOrCreateTagBuilder(Tags.Items.SEEDS).add(ModItems.DAWNBERRY_VINE_SEEDS.get());
        this.getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS).add(ModItems.AMBER_SHARD.get());
        this.getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES).add(ModItems.AROMA_ARMOR_TRIM_SMITHING_TABLE.get());
        this.getOrCreateTagBuilder(ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).add(ModItems.AMBER_SHARD.get(), ModBlocks.AMBER.get().asItem());
    }

    private Item item(DeferredBlock<Block> object){
        return object.get().asItem();
    }
}
