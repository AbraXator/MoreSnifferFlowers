package net.abraxator.moresnifferflowers.datagen;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.datagen.advancement.MSFAdvancementGenerator;
import net.abraxator.moresnifferflowers.datagen.datamaps.MSFDataMapsProvider;
import net.abraxator.moresnifferflowers.datagen.loot.MSFLootGenerator;
import net.abraxator.moresnifferflowers.datagen.model.MSFBlockModelProvider;
import net.abraxator.moresnifferflowers.datagen.model.MSFBlockStateGenerator;
import net.abraxator.moresnifferflowers.datagen.model.MSFItemModelProvider;
import net.abraxator.moresnifferflowers.datagen.recipe.MSFRecipesProvider;
import net.abraxator.moresnifferflowers.datagen.tag.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID)
public class MSFDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        var generator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();
        var packOutput = generator.getPackOutput();
        var datapackProvider = new RegistryDataGenerator(packOutput, event.getLookupProvider());
        var registryProvider = datapackProvider.getRegistryProvider();
        
        //BLOCKMODELS
        generator.addProvider(event.includeClient(), new MSFBlockModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MSFBlockStateGenerator(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new MSFItemModelProvider(packOutput, existingFileHelper));

        //SOUNDS
        generator.addProvider(event.includeClient(), new MSFSoundProvider(packOutput, existingFileHelper));
        
        //DATAPACK REGISTRIES
        generator.addProvider(event.includeServer(), new RegistryDataGenerator(packOutput, lookupProvider));
        
        //DATA MAPS
        generator.addProvider(event.includeServer(), new MSFDataMapsProvider(packOutput, lookupProvider));
        
        //LOOT
        generator.addProvider(event.includeClient(), MSFLootGenerator.create(packOutput, lookupProvider));

        //TAGS
        ModBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPaintingTagsProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModBiomeTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, registryProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEffectTagsProvider(packOutput, lookupProvider, existingFileHelper));


        //ADVANCEMENTS
        generator.addProvider(event.includeServer(), new AdvancementProvider(packOutput, lookupProvider, existingFileHelper, List.of(new MSFAdvancementGenerator())));

        //RECIPES
        generator.addProvider(event.includeServer(), new MSFRecipesProvider(packOutput, lookupProvider));
        
        //LANG
        //generator.addProvider(event.includeClient(), new ModLangProvider(packOutput));
    }
}
