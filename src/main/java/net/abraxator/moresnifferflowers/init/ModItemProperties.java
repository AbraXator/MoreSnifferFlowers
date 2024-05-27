package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.components.Dye;
import net.minecraft.client.renderer.item.ItemProperties;

public class ModItemProperties {
    public static void register() {
        ItemProperties.register(ModItems.DYESPRIA.get(), MoreSnifferFlowers.loc("color"), (pStack, pLevel, pEntity, pSeed) -> {
            if(!Dye.getDyeFromStack(pStack).isEmpty()) {
                return 1.0F;
            } else {
                return 0.0F;
            }
        });
    }
}
