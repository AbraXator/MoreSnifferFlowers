package net.abraxator.ceruleanvines.client;

import net.abraxator.ceruleanvines.MoreSnifferFlowers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void entityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        //event.registerEntityRenderer(ModEntities.CERULEAN_VINE_PATCH.get(), pContext -> new ThrownItemRenderer<>(pContext, 2.0F, false));
    }

    @SubscribeEvent
    public static void registerGUIOverlays(RegisterGuiOverlaysEvent event){
        //event.registerBelow(new ResourceLocation("frostbite"),"ceruleanly_vined_overlay", new CeruleanlyVinedOverlay());
    }
}
