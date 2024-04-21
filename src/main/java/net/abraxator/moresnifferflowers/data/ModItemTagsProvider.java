package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.ItemTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;


import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }
    @Override
    protected void configure(RegistryWrapper.WrapperLookup pProvider) {
        getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS) .add(ModItems.AMBER_SHARD);
        getOrCreateTagBuilder(ItemTags.TRIM_TEMPLATES).add(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE);
        this.getOrCreateTagBuilder(ModTags.ModItemTags.AROMA_TRIM_TEMPLATE_INGREDIENT).add(ModItems.AMBER_SHARD, ModBlocks.AMBER.asItem());
    }


}
