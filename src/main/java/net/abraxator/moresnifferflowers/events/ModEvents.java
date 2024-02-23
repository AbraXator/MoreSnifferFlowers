package net.abraxator.moresnifferflowers.events;


import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.BOBLING.get(), Bobling.createAttributes().build());
    }
}
