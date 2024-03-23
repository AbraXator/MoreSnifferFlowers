package net.abraxator.moresnifferflowers;

import com.mojang.logging.LogUtils;
import net.abraxator.moresnifferflowers.compat.quark.OtherModEvents;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.networking.ModPacketHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MoreSnifferFlowers.MOD_ID)
public class MoreSnifferFlowers {
    public static final String MOD_ID = "moresnifferflowers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreSnifferFlowers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModEntityTypes.ENTITIES.register(modEventBus);
        ModMobEffects.EFFECTS.register(modEventBus);
        ModCreativeTabs.TABS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        ModBannerPatterns.BANNER_PATTERNS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        if(ModList.get().isLoaded("quark")) {
            MinecraftForge.EVENT_BUS.addListener(OtherModEvents::onSimpleHarvest);
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ModItemProperties.register();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModAdvancementCritters.init();

        event.enqueueWork(() -> {
            ModPacketHandler.register();
            ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY_VINE_SEEDS.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.DAWNBERRY_VINE.get(), 0.85F);
        });
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
