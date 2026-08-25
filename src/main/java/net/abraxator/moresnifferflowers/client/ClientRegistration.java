package net.abraxator.moresnifferflowers.client;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.ModCauldronBlockEntity;
import net.abraxator.moresnifferflowers.client.gui.screen.ClientDyespriaTooltip;
import net.abraxator.moresnifferflowers.client.gui.screen.DyespriaTooltip;
import net.abraxator.moresnifferflowers.client.gui.screen.GluedOverlay;
import net.abraxator.moresnifferflowers.client.gui.screen.RebrewingStandScreen;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.client.model.block.*;
import net.abraxator.moresnifferflowers.client.model.entity.*;
import net.abraxator.moresnifferflowers.client.particle.*;
import net.abraxator.moresnifferflowers.client.renderer.block.*;
import net.abraxator.moresnifferflowers.client.renderer.custom.BlockPatternRenderer;
import net.abraxator.moresnifferflowers.client.renderer.entity.*;
import net.abraxator.moresnifferflowers.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistration {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        Sheets.addWoodType(ModWoodTypes.CORRUPTED);
        Sheets.addWoodType(ModWoodTypes.VIVICUS);
        ModItemProperties.register();
        MenuScreens.register(ModMenuTypes.REBREWING_STAND.get(), RebrewingStandScreen::new);
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
    public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.BOBLING.get(), BoblingRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DRAGONFLY.get(), DragonflyRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CORRUPTED_SLIME_BALL.get(), CorruptedProjectileRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.MOD_CORRUPTED_BOAT.get(), context -> new ModBoatRenderer(context, false));
        event.registerEntityRenderer(ModEntityTypes.MOD_CORRUPTED_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));
        event.registerEntityRenderer(ModEntityTypes.MOD_VIVICUS_BOAT.get(), context -> new ModBoatRenderer(context, false));
        event.registerEntityRenderer(ModEntityTypes.MOD_VIVICUS_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));
        event.registerEntityRenderer(ModEntityTypes.JAR_OF_ACID.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SALT_BUBBLE.get(), SaltBubbleRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SALT_PROJECTILE.get(), SaltProjectileRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.GLUING_GUM_ENTITY.get(), GluingGumRenderer::new);

    }

    @SubscribeEvent
    public static void onRegisterBlockEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.XBUSH.get(), AmbushBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GIANT_CROP.get(), GiantCropBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CROPRESSOR.get(), CropressorBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DYESPRIA_PLANT.get(), DyespriaPlantBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.VIVICUS_SIGN.get(), VivicusSignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.VIVICUS_HANGING_SIGN.get(), VivicusHangingSignRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BONDRIPIA.get(), BondripiaBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BEROOT_CAULDRON.get(), BerootCauldronRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SALTEMONE.get(), SaltemoneBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MOD_CAULDRON.get(), ModCauldronRenderer::new);

    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.FLY.get(), FlyParticle.Provider::new);
        event.registerSpriteSet(ModParticles.CARROT.get(), CarrotParticle.Provider::new);
        event.registerSpriteSet(ModParticles.AMBUSH.get(), AmbushParticle.Provider::new);
        event.registerSpriteSet(ModParticles.GARBUSH.get(), AmbushParticle.Provider::new);
        event.registerSpriteSet(ModParticles.GIANT_CROP.get(), GiantCropParticle.Provider::new);

        event.registerSpriteSet(ModParticles.BONDRIPIA_DRIP.get(), BondripiaParticle.BondripiaDripProvider::new);
        event.registerSpriteSet(ModParticles.BONDRIPIA_FALL.get(), BondripiaParticle.BondripiaFallProvider::new);
        event.registerSpriteSet(ModParticles.BONDRIPIA_LAND.get(), BondripiaParticle.BondripiaLandProvider::new);

        event.registerSpriteSet(ModParticles.ACIDRIPIA_DRIP.get(), BondripiaParticle.AcidripiaDripProvider::new);
        event.registerSpriteSet(ModParticles.ACIDRIPIA_FALL.get(), BondripiaParticle.AcidripiaFallProvider::new);
        event.registerSpriteSet(ModParticles.ACIDRIPIA_LAND.get(), BondripiaParticle.AcidripiaLandProvider::new);

        event.registerSpriteSet(ModParticles.TORCHFLAME.get(), TorchflameParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BUBBLE.get(), ModBubbleParticle.Provider::new);

    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll("glued", new GluedOverlay());
    }



    @SubscribeEvent
    public static void onRegisterTooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(DyespriaTooltip.class, ClientDyespriaTooltip::new);
    }


    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if(event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo iModFileInfo = ModList.get().getModFileById(MoreSnifferFlowers.MOD_ID);
            if(iModFileInfo == null) {
                MoreSnifferFlowers.LOGGER.error("Could not find More Sniffer Flowers mod file info; built-in resource packs will be missing!");
            }

            IModFile modFile = iModFileInfo.getFile();
            event.addRepositorySource(pOnLoad -> {

                Pack customStyleGUI = Pack.readMetaAndCreate(
                        MoreSnifferFlowers.loc("more_sniffer_flowers_boring").toString(),
                        Component.literal("Boring More Sniffer Flowers"),
                        false,
                        pId -> new PathPackResources(pId, modFile.findResource("resourcepacks/more_sniffer_flowers_boring"), true),
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        PackSource.BUILT_IN);
                if(customStyleGUI != null) {
                    pOnLoad.accept(customStyleGUI);
                }
            });
        }
    }

    public static boolean isBoringLoaded() {
        return Minecraft.getInstance().getResourceManager().listPacks().anyMatch(packResources -> packResources.packId().equals("more_sniffer_flowers_boring"));
    }
}
