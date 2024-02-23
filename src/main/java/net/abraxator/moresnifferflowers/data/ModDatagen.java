package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        var generator = event.getGenerator();
        var existingFileHelper = event.getExistingFileHelper();
        var registries = event.getLookupProvider();
        var packOutput = generator.getPackOutput();
        var future = event.getLookupProvider();
        TagsProvider<Block> blockTagsProvider = new ModBlockTagsProvider(packOutput, registries, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, future, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModLoottableProvider(packOutput));
        generator.addProvider(event.includeClient(), new ModLootModifierProvider(packOutput));
        generator.addProvider(event.includeServer(), new ModBannerPatternTagsProvider(packOutput, future, existingFileHelper));
        generator.addProvider(event.includeServer(), new RegistryDataGenerator(packOutput, future));
        generator.addProvider(event.includeServer(), new ModRecipesProvider(packOutput, future));
        //generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, future, existingFileHelper));
    }
}
