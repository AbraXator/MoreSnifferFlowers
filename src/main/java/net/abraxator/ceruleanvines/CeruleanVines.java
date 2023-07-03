package net.abraxator.ceruleanvines;

import com.mojang.logging.LogUtils;
import net.abraxator.ceruleanvines.init.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CeruleanVines.MOD_ID)
public class CeruleanVines {
    public static final String MOD_ID = "moresnifferflowers";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CeruleanVines() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModMobEffects.EFFECTS.register(modEventBus);
        ModCreativeTabs.TABS.register(modEventBus);


        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }



    private void commonSetup(final FMLCommonSetupEvent event) {
        ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY_VINE_SEEDS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(ModBlocks.DAWNBERRY_VINE.get(), 0.85F);
    }
}
