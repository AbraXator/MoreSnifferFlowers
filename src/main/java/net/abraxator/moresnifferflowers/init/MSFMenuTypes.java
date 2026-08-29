package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.client.gui.menu.RebrewingStandMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface MSFMenuTypes {
    DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(BuiltInRegistries.MENU, MoreSnifferFlowers.MOD_ID);

    DeferredHolder<MenuType<?>, MenuType<RebrewingStandMenu>> REBREWING_STAND = MENU_TYPES.register("rebrewing_stand",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new RebrewingStandMenu(windowId, inv)));
}
