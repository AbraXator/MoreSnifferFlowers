package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.colors.Dye;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.DyeItem;

public class ModItemProperties {
    public static void register() {
        ItemProperties.register(ModItems.DYESPRIA.get(), MoreSnifferFlowers.loc("color"), (pStack, pLevel, pEntity, pSeed) -> {
            if(!Dye.getDyeFromDyeStack(pStack).isEmpty()) {
                return 1.0F;
            } else {
                return 0.0F;
            }
        });
    }
}
