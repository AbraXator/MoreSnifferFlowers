package net.abraxator.cerulean_vines.init;

import net.abraxator.cerulean_vines.CeruleanVines;
import net.abraxator.cerulean_vines.items.CeruleanVinePatchItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, CeruleanVines.MOD_ID);

    public static final RegistryObject<Item> CERULEAN_VINE_PATCH = ITEMS.register("cerulean_vine_patch", () -> new CeruleanVinePatchItem(new Item.Properties()));
}
