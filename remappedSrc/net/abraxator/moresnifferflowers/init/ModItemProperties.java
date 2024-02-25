package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class ModItemProperties {
    public static void register() {
        ModelPredicateProviderRegistry.register(ModItems.DYESPRIA.get(), MoreSnifferFlowers.loc("color"), (pStack, pLevel, pEntity, pSeed) -> {
            if(!DyespriaItem.getDye(pStack).isEmpty()) {
                return 1.0F;
            } else {
                return 0.0F;
            }
        });
    }
}
