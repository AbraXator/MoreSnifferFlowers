package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.FlowerPainter;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.DyeItem;

import java.util.concurrent.Flow;

public class ModItemProperties {
    public static void register() {
        ItemProperties.register(ModItems.FLOWER_PAINTER.get(), MoreSnifferFlowers.loc("color"), (pStack, pLevel, pEntity, pSeed) -> {
            if(FlowerPainter.getDye(pStack).isPresent() && FlowerPainter.getDye(pStack).get().getItem() instanceof DyeItem) {
                return 1.0F;
            } else {
                return 0.0F;
            }
        });
    }
}
