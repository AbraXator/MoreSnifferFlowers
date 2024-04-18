package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.data.advancement.ModAdvancementProvider;
import net.abraxator.moresnifferflowers.data.loot.ModLootModifierProvider;
import net.abraxator.moresnifferflowers.data.loot.ModLoottableProvider;
import net.abraxator.moresnifferflowers.data.recipe.ModRecipesProvider;
import net.abraxator.moresnifferflowers.data.tag.ModBannerPatternTagsProvider;
import net.abraxator.moresnifferflowers.data.tag.ModBlockTagsProvider;
import net.abraxator.moresnifferflowers.data.tag.ModItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> completableFuture = event.getLookupProvider();

        //BLOCKMODELS
        generator.addProvider(event.includeClient(), new ModBlockModelGenerator(packOutput, existingFileHelper));
        
        //DATAPACK REGISTRIES
        generator.addProvider(event.includeServer(), new RegistryDataGenerator(packOutput, completableFuture));

        //LOOT
        generator.addProvider(event.includeServer(), new ModLoottableProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModLootModifierProvider(packOutput));

        //TAGS
        TagsProvider<Block> blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(packOutput, completableFuture, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, completableFuture, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, completableFuture, existingFileHelper));

        //ADVANCEMENTS
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, completableFuture, existingFileHelper));

        //RECIPES
        generator.addProvider(event.includeServer(), new ModRecipesProvider(packOutput));
    }
}
