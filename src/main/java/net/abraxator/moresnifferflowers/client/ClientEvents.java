package net.abraxator.moresnifferflowers.client;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.CaulorflowerBlock;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.client.model.block.GiantCropModels;
import net.abraxator.moresnifferflowers.client.model.entity.BoblingModel;
import net.abraxator.moresnifferflowers.client.particle.CarrotParticle;
import net.abraxator.moresnifferflowers.client.particle.FlyParticle;
import net.abraxator.moresnifferflowers.client.renderer.block.AmbushBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.block.GiantCropBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.entity.BoblingRenderer;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforgespi.language.IModFileInfo;
import net.neoforged.neoforgespi.locating.IModFile;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayerLocations.BOBLING, BoblingModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_CARROT, GiantCropModels::createGiantCarrotLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_POTATO, GiantCropModels::createGiantPotatoLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_NETHERWART, GiantCropModels::createNetherwartLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_BEETROOT, GiantCropModels::createBeetrootLayer);
        event.registerLayerDefinition(ModModelLayerLocations.GIANT_WHEAT, GiantCropModels::createWheatLayer);
    }

    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.BOBLING.get(), BoblingRenderer::new);
    }

    @SubscribeEvent
    public static void blockRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.AMBUSH.get(), AmbushBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.GIANT_CROP.get(), GiantCropBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.FLY.get(), FlyParticle.Provider::new);
        event.registerSpriteSet(ModParticles.CARROT.get(), CarrotParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((pState, pLevel, pPos, pTintIndex) -> {
            if(pTintIndex == 0) {
                return BiomeColors.getAverageFoliageColor(pLevel, pPos);
            }
            if(pTintIndex == 1 && pState.getValue(CaulorflowerBlock.HAS_COLOR)) {
                return DyespriaItem.colorForDye(pState.getValue(CaulorflowerBlock.COLOR));
            }
            return -1;
        }, ModBlocks.CAULORFLOWER.get());
    }

    @SubscribeEvent
    public static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((pStack, pTintIndex) -> {
            var color = DyespriaItem.getColor(pStack);
            if(pTintIndex != 0 || color.isEmpty()) {
                return -1;
            } else {
                return DyespriaItem.colorForDye(color.get());
            }
        }, ModItems.DYESPRIA.get());
    }

    /*@SubscribeEvent
    public static void onTickClientTick(TickEvent.ClientTickEvent event) {
        Player player = Minecraft.getInstance().player;
        HitResult hitResult = player.pick(player.getAttributeValue(ForgeMod.BLOCK_REACH.get()), 0.0F, false);
        if(hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            if(player.level().getBlockState(blockPos).is(ModBlocks.GIANT_CARROT.get())) {
                VoxelShape voxelShape = Block.box(0, 0, 0, 0, 0, 0);
                voxelShape.
            }
        }
    }*/



    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if(event.getPackType() == PackType.CLIENT_RESOURCES) {
            IModFileInfo iModFileInfo = ModList.get().getModFileById(MoreSnifferFlowers.MOD_ID);
            if(iModFileInfo == null) {
                MoreSnifferFlowers.LOGGER.error("Could not find More Sniffer Flowers mod file info; built-in resource packs will be missing!");
            }

            IModFile modFile = iModFileInfo.getFile();
            event.addRepositorySource(pOnLoad -> {
                Pack pack = Pack.readMetaAndCreate(
                        MoreSnifferFlowers.loc("rtx_moresnifferflowers").toString(),
                        Component.literal("RTX More Sniffer Flowers"),
                        false,
                        pId -> new PathPackResources(, modFile.findResource("resourcepacks/rtx_moresnifferflowers"), true),
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
