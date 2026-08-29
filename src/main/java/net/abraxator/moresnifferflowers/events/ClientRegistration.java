package net.abraxator.moresnifferflowers.events;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.client.gui.screen.ClientDyespriaTooltip;
import net.abraxator.moresnifferflowers.client.gui.screen.DyespriaTooltip;
import net.abraxator.moresnifferflowers.client.gui.screen.GluedOverlay;
import net.abraxator.moresnifferflowers.client.gui.screen.RebrewingStandScreen;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.client.model.block.*;
import net.abraxator.moresnifferflowers.client.model.entity.*;
import net.abraxator.moresnifferflowers.client.particle.*;
import net.abraxator.moresnifferflowers.client.renderer.block.*;
import net.abraxator.moresnifferflowers.client.renderer.entity.*;
import net.abraxator.moresnifferflowers.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.locating.IModFile;

import java.util.Optional;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, value = Dist.CLIENT)
public class ClientRegistration {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        Sheets.addWoodType(MSFWood.WoodTypes.CORRUPTED);
        Sheets.addWoodType(MSFWood.WoodTypes.VIVICUS);
        MSFItems.ModelProperties.register();
    }

    @SubscribeEvent
    public static void onRegisterMenuScreenEvent(RegisterMenuScreensEvent event) {
        event.register(MSFMenuTypes.REBREWING_STAND.get(), RebrewingStandScreen::new);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //CENTER
        event.registerLayerDefinition(ModModelLayerLocations.BOBLING, BoblingModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.DRAGONFLY, DragonflyModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.CORRUPTED_PROJECTILE, CorruptedProjectileModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.CORRUPTED_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayerLocations.CORRUPTED_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayerLocations.VIVICUS_BOAT_LAYER, BoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayerLocations.VIVICUS_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        event.registerLayerDefinition(ModModelLayerLocations.SALT_BUBBLE, SaltBubbleModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GLUING_GUM, GluingGumModel::createBodyLayer);

        //BLOCK
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_CARROT, GiantCropModels::createGiantCarrotLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_POTATO, GiantCropModels::createGiantPotatoLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_NETHERWART, GiantCropModels::createNetherwartLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_BEETROOT, GiantCropModels::createBeetrootLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_WHEAT, GiantCropModels::createWheatLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_ONION, GiantCropModels::createOnionLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_TOMATO, GiantCropModels::createTomatoLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_CABBAGE, GiantCropModels::createCabbageLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_RICE, GiantCropModels::createRiceLayer);

        event.registerLayerDefinition(ModModelLayerLocations.CROPRESSOR, CropressorModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.BONDRIPIA, BondripiaModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.BEROOT_CAULDRON, BerootCauldronModel::createCauldronLayer);
        event.registerLayerDefinition(ModModelLayerLocations.BEROOT_SPOON, BerootCauldronModel::createSpoonLayer);
        event.registerLayerDefinition(ModModelLayerLocations.SALTEMONE, SaltemoneModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.SALTEMONE_TOP, SaltemoneModel::createTopLayer);
        event.registerLayerDefinition(ModModelLayerLocations.DYESPRIA, DyespriaModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayerLocations.SIMPLE_CUBE, SimpleModels::simpleCube);
        event.registerLayerDefinition(ModModelLayerLocations.INVERTED_CUBE, SimpleModels::invertedCube);


    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MSFEntityTypes.BOBLING.get(), BoblingRenderer::new);
        event.registerEntityRenderer(MSFEntityTypes.DRAGONFLY.get(), DragonflyRenderer::new);
        event.registerEntityRenderer(MSFEntityTypes.CORRUPTED_SLIME_BALL.get(), CorruptedProjectileRenderer::new);
        event.registerEntityRenderer(MSFEntityTypes.MOD_CORRUPTED_BOAT.get(), context -> new ModBoatRenderer(context, false));
        event.registerEntityRenderer(MSFEntityTypes.MOD_CORRUPTED_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));
        event.registerEntityRenderer(MSFEntityTypes.MOD_VIVICUS_BOAT.get(), context -> new ModBoatRenderer(context, false));
        event.registerEntityRenderer(MSFEntityTypes.MOD_VIVICUS_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));
        event.registerEntityRenderer(MSFEntityTypes.JAR_OF_ACID.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(MSFEntityTypes.SALT_BUBBLE.get(), SaltBubbleRenderer::new);
        event.registerEntityRenderer(MSFEntityTypes.SALT_PROJECTILE.get(), SaltProjectileRenderer::new);
        event.registerEntityRenderer(MSFEntityTypes.GLUING_GUM_ENTITY.get(), GluingGumRenderer::new);

    }

    @SubscribeEvent
    public static void blockRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MSFBlockEntities.XBUSH.get(), AmbushBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.GIANT_CROP.get(), GiantCropBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.CROPRESSOR.get(), CropressorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.DYESPRIA_PLANT.get(), DyespriaPlantBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.VIVICUS_SIGN.get(), VivicusSignRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.VIVICUS_HANGING_SIGN.get(), VivicusHangingSignRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.BONDRIPIA.get(), BondripiaBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.BEROOT_CAULDRON.get(), BerootCauldronRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.SALTEMONE.get(), SaltemoneBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(MSFBlockEntities.MOD_CAULDRON.get(), ModCauldronRenderer::new);

    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(MSFParticles.FLY.get(), FlyParticle.Provider::new);
        event.registerSpriteSet(MSFParticles.CARROT.get(), CarrotParticle.Provider::new);
        event.registerSpriteSet(MSFParticles.AMBUSH.get(), AmbushParticle.Provider::new);
        event.registerSpriteSet(MSFParticles.GARBUSH.get(), AmbushParticle.Provider::new);
        event.registerSpriteSet(MSFParticles.GIANT_CROP.get(), GiantCropParticle.Provider::new);
        event.registerSpriteSet(MSFParticles.BONDRIPIA_DRIP.get(), BondripiaParticle.BondripiaDripProvider::new);
        event.registerSpriteSet(MSFParticles.BONDRIPIA_FALL.get(), BondripiaParticle.BondripiaFallProvider::new);
        event.registerSpriteSet(MSFParticles.BONDRIPIA_LAND.get(), BondripiaParticle.BondripiaLandProvider::new);

        event.registerSpriteSet(MSFParticles.ACIDRIPIA_DRIP.get(), BondripiaParticle.AcidripiaDripProvider::new);
        event.registerSpriteSet(MSFParticles.ACIDRIPIA_FALL.get(), BondripiaParticle.AcidripiaFallProvider::new);
        event.registerSpriteSet(MSFParticles.ACIDRIPIA_LAND.get(), BondripiaParticle.AcidripiaLandProvider::new);

        event.registerSpriteSet(MSFParticles.TORCHFLAME.get(), TorchflameParticle.Provider::new);
        event.registerSpriteSet(MSFParticles.BUBBLE.get(), ModBubbleParticle.Provider::new);

    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerBelowAll(MoreSnifferFlowers.loc("glued"), new GluedOverlay());
    }



    @SubscribeEvent
    public static void onRegisterTooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(DyespriaTooltip.class, ClientDyespriaTooltip::new);
    }

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if(event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFile iModFileInfo = ModList.get().getModFileById(MoreSnifferFlowers.MOD_ID).getFile();

            event.addRepositorySource(pOnLoad -> {
                String name = "more_sniffer_flowers_boring";
                    var pack = Pack.readMetaAndCreate(
                            new PackLocationInfo(name, Component.literal("More Sniffer Flowers Boring"),  PackSource.BUILT_IN, Optional.empty()),
                            new Pack.ResourcesSupplier() {
                                @Override
                                public PackResources openPrimary(PackLocationInfo packLocationInfo) {
                                    return new PathPackResources(packLocationInfo, iModFileInfo.findResource("resourcepacks/" + name));
                                }

                                @Override
                                public PackResources openFull(PackLocationInfo packLocationInfo, Pack.Metadata metadata) {
                                    return openPrimary(packLocationInfo);
                                }
                            },
                            PackType.CLIENT_RESOURCES,
                            new PackSelectionConfig(false, Pack.Position.TOP, false));
                    if(pack != null) {
                        pOnLoad.accept(pack);
                    }
            });
        }
    }

    public static boolean isBoringLoaded() {
        return Minecraft.getInstance().getResourceManager().listPacks().anyMatch(packResources -> packResources.packId().equals("more_sniffer_flowers_boring"));
    }
}
