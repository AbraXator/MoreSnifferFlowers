package net.abraxator.ceruleanvines.data;

import net.abraxator.ceruleanvines.CeruleanVines;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = CeruleanVines.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> completableFuture = event.getLookupProvider();
        TagsProvider<Block> blockTagsProvider = new ModBlockTagsProvider(packOutput, completableFuture, CeruleanVines.MOD_ID, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput, completableFuture, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModLoottableProvider(packOutput));
    }
}
