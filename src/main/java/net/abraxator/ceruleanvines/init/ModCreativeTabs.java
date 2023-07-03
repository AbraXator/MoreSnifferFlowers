package net.abraxator.ceruleanvines.init;

import net.abraxator.ceruleanvines.MoreSnifferFlowers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<CreativeModeTab> moresnifferflowers_TAB = TABS.register("moresnifferflowers_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("moresnifferflowers.creative_tab"))
            .icon(() -> new ItemStack(ModItems.DAWNBERRY.get()))
            .displayItems((parameters, output) -> ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get())))
            .build());
}
