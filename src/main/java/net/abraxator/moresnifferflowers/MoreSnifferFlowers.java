package net.abraxator.moresnifferflowers;

import com.mojang.logging.LogUtils;
import net.abraxator.moresnifferflowers.client.ClientEvents;
import net.abraxator.moresnifferflowers.init.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.slf4j.Logger;

@Mod(MoreSnifferFlowers.MOD_ID)
public class MoreSnifferFlowers {
    public static final String MOD_ID = "moresnifferflowers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreSnifferFlowers(IEventBus modEventBus, Dist dist) {
        if(dist.isClient()) modEventBus.addListener(ClientEvents::clientSetup);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener((RegisterEvent e) -> ModAdvancementCritters.init());
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModEntityTypes.ENTITIES.register(modEventBus);
        ModCreativeTabs.TABS.register(modEventBus);
        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        ModBannerPatterns.BANNER_PATTERNS.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        ModMobEffects.EFFECTS.register(modEventBus);
        ModDataComponents.DATA_COMPONENTS.register(modEventBus);
    }
    
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY_VINE_SEEDS.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModItems.AMBUSH_SEEDS.get(), 0.3F);
            ComposterBlock.COMPOSTABLES.put(ModBlocks.CAULORFLOWER.get().asItem(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModItems.DYESPRIA_SEEDS.get(), 0.4F);
            ComposterBlock.COMPOSTABLES.put(ModItems.BONMEELIA_SEEDS.get(), 0.5F);
            ComposterBlock.COMPOSTABLES.put(ModItems.CROPRESSED_BEETROOT.get(), 1.0F);
            ComposterBlock.COMPOSTABLES.put(ModItems.CROPRESSED_NETHERWART.get(), 1.0F);
            ComposterBlock.COMPOSTABLES.put(ModItems.CROPRESSED_WHEAT.get(), 1.0F);
            ComposterBlock.COMPOSTABLES.put(ModItems.CROPRESSED_POTATO.get(), 1.0F);
            ComposterBlock.COMPOSTABLES.put(ModItems.CROPRESSED_CARROT.get(), 1.0F);
        });
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String sLoc(String path) {
        return loc(path).toString();
    }
}
