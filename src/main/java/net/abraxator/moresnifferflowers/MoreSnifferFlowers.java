package net.abraxator.moresnifferflowers;

import com.mojang.logging.LogUtils;
import net.abraxator.moresnifferflowers.data.ModRecipesProvider;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.init.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class MoreSnifferFlowers implements ModInitializer {
    public static final String MOD_ID = "moresnifferflowers";
    public static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public void onInitialize() {
        ModItemGroups.registerItemGroups();
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModBlocks.registerBlockProperties();
        ModItemProperties.registerModItemPropeties();
        ModParticles.registerParticles();
        ModEffects.registerEffects();
        ModSounds.registerSounds();
        ModBlockEntities.registerBlockEntities();
        ModMenuTypes.registerScreenHandlers();
        ModRecipesProvider.registerModRecipes();
        ModRecipeTypes.registerRecipeTypes();

    }
//    public MoreSnifferFlowers(IEventBus modEventBus, Dist dist) {
//       if(dist.isClient()) modEventBus.addListener(ClientEvents::clientSetup);
//        modEventBus.addListener(this::commonSetup);
//
//        ModItems.ITEMS.register(modEventBus);
//        ModBlocks.BLOCKS.register(modEventBus);
//        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
//        ModEntityTypes.ENTITIES.register(modEventBus);
//        ModItemGroups.TABS.register(modEventBus);
//        ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
//        ModBannerPatterns.BANNER_PATTERNS.register(modEventBus);
//        ModParticles.PARTICLES.register(modEventBus);
//        ModAdvancementCritters.TRIGGERS.register(modEventBus);
//        ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
//        ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
//
//    }
//
//    private void commonSetup(final FMLCommonSetupEvent event) {
//        event.enqueueWork(() -> {
//            ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY_VINE_SEEDS.get(), 0.3F);
//            ComposterBlock.COMPOSTABLES.put(ModItems.DAWNBERRY.get(), 0.3F);
//            ComposterBlock.COMPOSTABLES.put(ModBlocks.DAWNBERRY_VINE.get(), 0.85F);
//        });
//    }
        public static Identifier id(String id) {
            return new Identifier(MOD_ID, id);
        }
    }
