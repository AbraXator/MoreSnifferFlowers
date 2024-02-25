package net.abraxator.moresnifferflowers.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final ItemGroup MORE_SNIFFER_FLOWERS = FabricItemGroup.builder()
            .displayName(Text.translatable("moresnifferflowers.creative_tab"))
            .icon(() -> new ItemStack(ModItems.DAWNBERRY))
            .entries((parameters, output) -> Registries.ITEM.iterator().forEachRemaining(output::add))
            .build();
}
