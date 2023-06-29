package net.abraxator.ceruleanvines.data;

import net.abraxator.ceruleanvines.CeruleanVines;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CeruleanVines.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDatagen{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        //generator.addProvider(event.includeClient(), new BlockStateDatagen(generator.getPackOutput(), CeruleanVines.MOD_ID, existingFileHelper));
    }
}
