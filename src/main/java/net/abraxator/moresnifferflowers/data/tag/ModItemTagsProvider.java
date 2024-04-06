package net.abraxator.moresnifferflowers.data.tag;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.FLOWERS).add(item(ModBlocks.DAWNBERRY_VINE), item(ModBlocks.AMBUSH), item(ModBlocks.CAULORFLOWER));
        this.tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(ModItems.DAWNBERRY_VINE_SEEDS.get());
        this.tag(Tags.Items.SEEDS).add(ModItems.DAWNBERRY_VINE_SEEDS.get());
        this.tag(ItemTags.TRIM_MATERIALS).add(ModItems.AMBER_SHARD.get());
        this.tag(ItemTags.TRIM_MATERIALS).add(ModItems.CROPRESSED_NETHERWART.get());
        this.tag(ItemTags.TRIM_MATERIALS).add(ModItems.CROPRESSED_POTATO.get());
        this.tag(ItemTags.TRIM_MATERIALS).add(ModItems.CROPRESSED_CARROT.get());
        this.tag(ItemTags.TRIM_MATERIALS).add(ModItems.CROPRESSED_BEETROOT.get());
        this.tag(ItemTags.TRIM_MATERIALS).add(ModItems.CROPRESSED_WHEAT.get());
        this.tag(ItemTags.TRIM_TEMPLATES).add(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        this.tag(ItemTags.TRIM_TEMPLATES).add(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        this.tag(ItemTags.TRIM_TEMPLATES).add(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
        this.tag(ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).add(ModItems.AMBER_SHARD.get(), ModBlocks.AMBER.get().asItem());
        this.tag(ModTags.ModItemTags.CROPRESSABLE_CROPS).add(Items.POTATO, Items.CARROT, Items.BEETROOT, Items.NETHER_WART, Items.WHEAT);
    }

    private Item item(RegistryObject<Block> object){
        return object.get().asItem();
    }
}
