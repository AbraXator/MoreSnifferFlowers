package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.data.advancement.ModAdvancementProvider;
import net.abraxator.moresnifferflowers.data.loot.ModLoottableProvider;
import net.abraxator.moresnifferflowers.data.recipe.ModRecipesProvider;
import net.abraxator.moresnifferflowers.data.tag.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        var generator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();
        var packOutput = generator.getPackOutput();
        var datapackProvider = new RegistryDataGenerator(packOutput, event.getLookupProvider());
        var registryProvider = datapackProvider.getRegistryProvider();
        
        //BLOCKMODELS
        generator.addProvider(event.includeClient(), new ModBlockStateGenerator(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        
        //SOUNDS
        generator.addProvider(event.includeClient(), new ModSoundProvider(packOutput, existingFileHelper));
        
        //DATAPACK REGISTRIES
        generator.addProvider(event.includeServer(), new RegistryDataGenerator(packOutput, lookupProvider));

        //LOOT
        generator.addProvider(event.includeClient(), new ModLoottableProvider(packOutput));

        //TAGS
        ModBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPaintingTagsProvider(packOutput, lookupProvider, existingFileHelper));
        //generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, future, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, registryProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEffectTagsProvider(packOutput, lookupProvider, existingFileHelper));


        //ADVANCEMENTS
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, lookupProvider, existingFileHelper));

        //RECIPES
        generator.addProvider(event.includeServer(), new ModRecipesProvider(packOutput));
        
        //LANG
        //generator.addProvider(event.includeClient(), new ModLangProvider(packOutput));
    }
}
