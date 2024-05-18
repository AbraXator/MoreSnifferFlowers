package net.abraxator.moresnifferflowers.client;

import com.google.errorprone.annotations.Var;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.CaulorflowerBlock;
import net.abraxator.moresnifferflowers.client.gui.screen.RebrewingStandScreen;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.client.model.block.CropressorModel;
import net.abraxator.moresnifferflowers.client.model.block.GiantCropModels;
import net.abraxator.moresnifferflowers.client.model.entity.BoblingModel;
import net.abraxator.moresnifferflowers.client.model.entity.DragonflyModel;
import net.abraxator.moresnifferflowers.client.particle.AmbushParticle;
import net.abraxator.moresnifferflowers.client.particle.CarrotParticle;
import net.abraxator.moresnifferflowers.client.particle.FlyParticle;
import net.abraxator.moresnifferflowers.client.particle.GiantCropParticle;
import net.abraxator.moresnifferflowers.client.renderer.block.AmbushBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.block.CropressorBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.block.DyespriaPlantBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.block.GiantCropBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.entity.BoblingRenderer;
import net.abraxator.moresnifferflowers.client.renderer.entity.DragonflyRenderer;
import net.abraxator.moresnifferflowers.colors.Dye;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.locating.IModFile;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ModItemProperties.register();
    }
    
    @SubscribeEvent
    public static void onRegisterMenuScreenEvent(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.REBREWING_STAND.get(), RebrewingStandScreen::new);
    }
    
    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //ENTITY
        event.registerLayerDefinition(ModModelLayerLocations.BOBLING, BoblingModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.DRAGONFLY, DragonflyModel::createBodyLayer);
        //BLOCK
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_CARROT, GiantCropModels::createGiantCarrotLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_POTATO, GiantCropModels::createGiantPotatoLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_NETHERWART, GiantCropModels::createNetherwartLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_BEETROOT, GiantCropModels::createBeetrootLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_WHEAT, GiantCropModels::createWheatLayer);
        event.registerLayerDefinition(ModModelLayerLocations.CROPRESSOR, CropressorModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.BOBLING.get(), BoblingRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DRAGONFLY.get(), DragonflyRenderer::new);
    }

    @SubscribeEvent
    public static void blockRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.AMBUSH.get(), AmbushBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GIANT_CROP.get(), GiantCropBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CROPRESSOR.get(), CropressorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DYESPRIA_PLANT.get(), DyespriaPlantBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.FLY.get(), FlyParticle.Provider::new);
        event.registerSpriteSet(ModParticles.CARROT.get(), CarrotParticle.Provider::new);
        event.registerSpriteSet(ModParticles.AMBUSH.get(), AmbushParticle.Provider::new);
        event.registerSpriteSet(ModParticles.GIANT_CROP.get(), GiantCropParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((pState, pLevel, pPos, pTintIndex) -> {
            if(pTintIndex == 0) {
                return BiomeColors.getAverageFoliageColor(pLevel, pPos);
            }
            if(pTintIndex == 1) {
                return Dye.colorForDye(((CaulorflowerBlock) pState.getBlock()), pState.getValue(ModStateProperties.COLOR));
            }
            return -1;
        }, ModBlocks.CAULORFLOWER.get());
    }

    @SubscribeEvent
    public static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((pStack, pTintIndex) -> {
            Dye dye = Dye.getDyeFromStack(pStack);
            if(pTintIndex != 0 || dye.isEmpty()) {
                return -1;
            } else {
                return Dye.colorForDye(((DyespriaItem) pStack.getItem()), dye.color());
            }
        }, ModItems.DYESPRIA.get());
        event.register((pStack, pTintIndex) -> {
            return pTintIndex > 0 ? -1 : PotionUtils.getColor(pStack);
        }, ModItems.EXTRACTED_BOTTLE.get(), ModItems.REBREWED_POTION.get(), ModItems.REBREWED_SPLASH_POTION.get(), ModItems.REBREWED_LINGERING_POTION.get());
    }


    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if(event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFile iModFileInfo = ModList.get().getModFileById(MoreSnifferFlowers.MOD_ID).getFile();
            event.addRepositorySource(pOnLoad -> {
                String name = "more_sniffer_flowers_rtx";
                var pack = Pack.readMetaAndCreate(
                        name,
                        Component.literal("More Sniffer Flowers RTX"),
                        false,
                        new Pack.ResourcesSupplier() {
                            @Override
                            public PackResources openPrimary(String pId) {
                                return new PathPackResources(pId, iModFileInfo.findResource("resourcepacks/" + name), false);
                            }

                            @Override
                            public PackResources openFull(String pId, Pack.Info pInfo) {
                                return openPrimary(pId);
                            }
                        },
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        PackSource.BUILT_IN);
                if(pack != null) {
                    pOnLoad.accept(pack);
                }
            });

            event.addRepositorySource(pOnLoad -> {
                String name = "more_sniffer_flowers_boring";
                    var pack = Pack.readMetaAndCreate(
                            name,
                            Component.literal("More Sniffer Flowers Boring"),
                            false,
                            new Pack.ResourcesSupplier() {
                                @Override
                                public PackResources openPrimary(String pId) {
                                    return new PathPackResources(pId, iModFileInfo.findResource("resourcepacks/" + name), false);
                                }

                                @Override
                                public PackResources openFull(String pId, Pack.Info pInfo) {
                                    return openPrimary(pId);
                                }
                            },
                            PackType.CLIENT_RESOURCES,
                            Pack.Position.TOP,
                            PackSource.BUILT_IN);
                    if(pack != null) {
                        pOnLoad.accept(pack);
                    }
            });
        }
    }
}
