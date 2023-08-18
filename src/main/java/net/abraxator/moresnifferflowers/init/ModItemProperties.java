package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.FlowerPainter;
import net.minecraft.client.renderer.item.ItemProperties;

public class ModItemProperties {
    public static void register() {
        ItemProperties.register(ModItems.FLOWER_PAINTER.get(), MoreSnifferFlowers.loc("color"), (pStack, pLevel, pEntity, pSeed) -> {
            if(FlowerPainter.getColor(pStack).isPresent()) return 1;
            else return 0;
        });
    }
}
