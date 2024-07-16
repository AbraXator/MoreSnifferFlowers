package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(), MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MORESNIFFERFLOWERS_TAB = TABS.register("moresnifferflowers_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("moresnifferflowers.creative_tab"))
            .icon(() -> new ItemStack(ModItems.CREATIVE_TAB_ICON.get()))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.DAWNBERRY_VINE_SEEDS.get());
                output.accept(ModItems.DAWNBERRY.get());
                output.accept(ModItems.AMBUSH_SEEDS.get());
                output.accept(ModBlocks.AMBER.get());
                output.accept(ModItems.AMBER_SHARD.get());
                output.accept(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.AMBUSH_BANNER_PATTERN.get());
                output.accept(ModItems.DRAGONFLY.get());
                output.accept(ModItems.DYESPRIA_SEEDS.get());
                output.accept(ModItems.DYESPRIA.get());
                output.accept(ModBlocks.CAULORFLOWER.get());
                output.accept(ModItems.BONMEELIA_SEEDS.get());
                output.accept(ModItems.JAR_OF_BONMEEL.get());
                output.accept(ModItems.BELT_PIECE.get());
                output.accept(ModItems.ENGINE_PIECE.get());
                output.accept(ModItems.TUBE_PIECE.get());
                output.accept(ModItems.SCRAP_PIECE.get());
                output.accept(ModItems.PRESS_PIECE.get());
                output.accept(ModItems.CROPRESSOR.get());
                output.accept(ModItems.CROPRESSED_CARROT.get());
                output.accept(ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.CROPRESSED_POTATO.get());
                output.accept(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.CROPRESSED_WHEAT.get());
                output.accept(ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.CROPRESSED_BEETROOT.get());
                output.accept(ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.CROPRESSED_NETHERWART.get());
                output.accept(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE.get());
                output.accept(ModItems.EXTRACTION_BOTTLE.get());
                output.accept(ModItems.REBREWING_STAND.get());
                output.accept(ModItems.BROKEN_REBREWING_STAND.get());
            })
            .backgroundTexture(MoreSnifferFlowers.loc("textures/gui/container/tab_items.png"))
            .withTabsImage(MoreSnifferFlowers.loc("textures/gui/container/tabs.png"))
            .build());
}
//    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
//            BuiltInRegistries.CREATIVE_MODE_TAB.key(), MoreSnifferFlowers.MOD_ID);
//
//    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> moresnifferflowers_TAB = TABS.register("moresnifferflowers_tab", () -> CreativeModeTab.builder()
//            .title(Component.translatable("moresnifferflowers.creative_tab"))
//            .icon(() -> new ItemStack(ModItems.DAWNBERRY.get()))
//            .displayItems((parameters, output) -> ModItems.ITEMS.getEntries().forEach(itemRegistryObject -> output.accept(itemRegistryObject.get())))
//            .build());
//}
