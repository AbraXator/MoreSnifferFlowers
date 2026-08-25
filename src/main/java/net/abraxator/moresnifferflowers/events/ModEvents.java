package net.abraxator.moresnifferflowers.events;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.abraxator.moresnifferflowers.init.config.ModClientConfig;
import net.abraxator.moresnifferflowers.init.config.ModServerConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.BOBLING.get(), BoblingEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntityTypes.BOBLING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event){
        if (ModServerConfig.SERVER_CONFIG.isLoaded()) {
            List<ResourceLocation> locations = new ArrayList<>();

            locations.add(MoreSnifferFlowers.ofLoc(ModServerConfig.REBREWING_AMPLIFIER.get()));
            locations.add(MoreSnifferFlowers.ofLoc(ModServerConfig.REBREWING_LENGTH.get()));
            locations.add(MoreSnifferFlowers.ofLoc(ModServerConfig.REBREWING_SPLASH.get()));
            locations.add(MoreSnifferFlowers.ofLoc(ModServerConfig.REBREWING_LINGERING.get()));

            for (ResourceLocation location : locations) {
                if (!ForgeRegistries.ITEMS.containsKey(location)) {
                    MoreSnifferFlowers.LOGGER.error("Error in Rebrewing Server Config, couldn't find item: " + location);
                }

            }
        }

        if (ModClientConfig.CLIENT_CONFIG.isLoaded()){
            int hardenedMouthX = ModClientConfig.HARDENED_MOUTH_X.get();
            if (hardenedMouthX > -5 && hardenedMouthX < 132){
                MoreSnifferFlowers.LOGGER.error("Error in Hardened Mouth Client Config, the following X value would overlap vanilla slots " + hardenedMouthX + " ... Resetting to default value");

                ModClientConfig.HARDENED_MOUTH_X.set(ModClientConfig.HARDENED_MOUTH_X.getDefault());
                ModClientConfig.HARDENED_MOUTH_X.save();
            }
        }
    }

    /*@SubscribeEvent
    public static void onRegisterDataMapTypes(RegisterDataMapTypesEven event) {
        event.register(ModDataMaps.CORRUPTIBLE);
    }*/
}
