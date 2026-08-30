package net.abraxator.moresnifferflowers.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ExtractedBottleItem extends PotionItem {
    public ExtractedBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
        components.add(Component.translatableWithFallback("tooltip.extracted_bottle.obtain", "Obtainable using Bottle o' Extraction").withStyle(ChatFormatting.GOLD));
    }

}
