package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.data.advancement.ModAdvancementGenerator;
import net.abraxator.moresnifferflowers.data.loot.ModLootModifierProvider;
import net.abraxator.moresnifferflowers.data.loot.ModLoottableProvider;
import net.abraxator.moresnifferflowers.data.recipe.ModRecipesProvider;
import net.abraxator.moresnifferflowers.data.tag.ModBannerPatternTagsProvider;
import net.abraxator.moresnifferflowers.data.tag.ModBlockTagsProvider;
import net.abraxator.moresnifferflowers.data.tag.ModItemTagsProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        var generator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var registries = event.getLookupProvider();
        var packOutput = generator.getPackOutput();
        var future = event.getLookupProvider();
        
        //BLOCKMODELS
        generator.addProvider(event.includeClient(), new ModBlockModelGenerator(packOutput, existingFileHelper));
        
        //SOUNDS
        generator.addProvider(event.includeClient(), new ModSoundProvider(packOutput, existingFileHelper));
        
        //DATAPACK REGISTRIES
        generator.addProvider(event.includeServer(), new RegistryDataGenerator(packOutput, future));
        
        //DATA MAPS
        generator.addProvider(event.includeServer(), new ModDataMapsProvider(packOutput, future));
        
        //LOOT
        generator.addProvider(event.includeServer(), new ModLoottableProvider(packOutput, future));
        generator.addProvider(event.includeClient(), new ModLootModifierProvider(packOutput, future));

        //TAGS
        var blockTagsProvider = generator.addProvider(event.includeServer(), new ModBlockTagsProvider(packOutput, registries, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, future, blockTagsProvider.contentsGetter(), existingFileHelper));

        //ADVANCEMENTS
        generator.addProvider(event.includeServer(), new AdvancementProvider(packOutput, registries, existingFileHelper, List.of(new ModAdvancementGenerator())));

        //RECIPES
        generator.addProvider(event.includeServer(), new ModRecipesProvider(packOutput, future));
        
        //LANG
        //generator.addProvider(event.includeClient(), new ModLangProvider(packOutput));
    }
}
