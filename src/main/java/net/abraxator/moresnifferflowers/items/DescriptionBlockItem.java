package net.abraxator.moresnifferflowers.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class DescriptionBlockItem extends ItemNameBlockItem {
    final Component[] components;
    public DescriptionBlockItem(Block block, Properties properties, Component... tooltipComponents) {
        super(block, properties);
        components = tooltipComponents;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.addAll(List.of(components));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
