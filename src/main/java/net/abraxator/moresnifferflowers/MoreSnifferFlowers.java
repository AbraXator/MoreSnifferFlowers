package net.abraxator.moresnifferflowers;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.abraxator.moresnifferflowers.events.ClientRegistration;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.init.config.ModClientConfig;
import net.abraxator.moresnifferflowers.init.config.ModServerConfig;
import net.abraxator.moresnifferflowers.networking.ModPacketHandler;
import net.abraxator.moresnifferflowers.worldgen.structures.ModStructureTypes;
import net.abraxator.moresnifferflowers.worldgen.structures.pieces.ModPieceTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

@Mod(MoreSnifferFlowers.MOD_ID)
public class MoreSnifferFlowers {
    public static final String MOD_ID = "moresnifferflowers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MoreSnifferFlowers(IEventBus modEventBus, Dist dist, ModContainer container) {
        if(dist.isClient()) {
            modEventBus.addListener(ClientRegistration::clientSetup);
            clientConfig(container);
        }
        modEventBus.addListener(this::commonSetup);
        container.registerConfig(ModConfig.Type.SERVER, ModServerConfig.SERVER_CONFIG);
        container.registerConfig(ModConfig.Type.CLIENT, ModClientConfig.CLIENT_CONFIG);


        MSFItems.ITEMS.register(modEventBus);
        MSFBlocks.BLOCKS.register(modEventBus);
        MSFWorldGen.Features.FEATURES.register(modEventBus);
        MSFCreativeTabs.TABS.register(modEventBus);
        MSFEffects.EFFECTS.register(modEventBus);
        MSFSounds.SOUNDS.register(modEventBus);
        MSFParticles.PARTICLES.register(modEventBus);
        MSFMenuTypes.MENU_TYPES.register(modEventBus);
        MSFEntityTypes.ENTITIES.register(modEventBus);
        MSFWood.TrunkPlacerTypes.TRUNKS.register(modEventBus);
        MSFRecipes.Types.RECIPE_TYPES.register(modEventBus);
        ModPieceTypes.STRUCTURE_PIECE.register(modEventBus);
        MSFAdvancementCritters.TRIGGERS.register(modEventBus);
        MSFBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        MSFWood.TreeDecoratorTypes.DECORATORS.register(modEventBus);
        ModStructureTypes.STRUCTURE_PIECE.register(modEventBus);
        MSFDataComponents.DATA_COMPONENTS.register(modEventBus);
        MSFLoot.LootConditions.CONDITIONS.register(modEventBus);
        MSFDataAttachments.ATTACHMENT_TYPES.register(modEventBus);
        MSFRecipes.Serializer.RECIPE_SERIALIZERS.register(modEventBus);

        ModPacketHandler.register(modEventBus, 1);
    }

   @SuppressWarnings("deprecation")
    private void commonSetup(final FMLCommonSetupEvent event) {
        AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
        AxeItem.STRIPPABLES.put(MSFBlocks.CORRUPTED_LOG.get(), MSFBlocks.STRIPPED_CORRUPTED_LOG.get());
        AxeItem.STRIPPABLES.put(MSFBlocks.VIVICUS_LOG.get(), MSFBlocks.STRIPPED_VIVICUS_LOG.get());
        AxeItem.STRIPPABLES.put(MSFBlocks.CORRUPTED_WOOD.get(), MSFBlocks.STRIPPED_CORRUPTED_WOOD.get());
        AxeItem.STRIPPABLES.put(MSFBlocks.VIVICUS_WOOD.get(), MSFBlocks.STRIPPED_VIVICUS_WOOD.get());

        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(MSFBlocks.DYESPRIA_PLANT.getId(), MSFBlocks.POTTED_DYESPRIA);
        pot.addPlant(MSFBlocks.CORRUPTED_SAPLING.getId(), MSFBlocks.POTTED_CORRUPTED_SAPLING);
        pot.addPlant(MSFBlocks.VIVICUS_SAPLING.getId(), MSFBlocks.POTTED_VIVICUS_SAPLING);

        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_LOG.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_WOOD.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.STRIPPED_CORRUPTED_LOG.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.STRIPPED_CORRUPTED_WOOD.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_SLAB.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_FENCE.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_FENCE_GATE.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_DOOR.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_TRAPDOOR.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_PRESSURE_PLATE.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_BUTTON.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_LEAVES.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.CORRUPTED_SAPLING.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_LOG.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_WOOD.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.STRIPPED_VIVICUS_LOG.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.STRIPPED_VIVICUS_WOOD.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_PLANKS.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_STAIRS.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_SLAB.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_FENCE.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_FENCE_GATE.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_DOOR.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_TRAPDOOR.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_PRESSURE_PLATE.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_BUTTON.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_LEAVES.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_SAPLING.get(), 5, 20);
        fireBlock.setFlammable(MSFBlocks.VIVICUS_LEAVES_SPROUT.get(), 5, 20);

        MSFCauldronInteractions.bootstrap();
    }

    public static boolean hasFarmersDelight(){
        return ModList.get().isLoaded("farmersdelight");
    }

    public static ResourceLocation farmersDelightLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("farmersdelight", path);
    }


    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public void clientConfig(ModContainer container){
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    public static ResourceLocation vanillaLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    public static ResourceLocation ofLoc(String path) {
        return ResourceLocation.bySeparator(path, ':');
    }

    public static String sLoc(String path) {
        return loc(path).toString();
    }
}
