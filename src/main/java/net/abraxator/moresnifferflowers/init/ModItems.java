package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<Item> DAWNBERRY_VINE_SEEDS = ITEMS.register("dawnberry_vine_seeds", () -> new ItemNameBlockItem(ModBlocks.DAWNBERRY_VINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DAWNBERRY = ITEMS.register("dawnberry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).fast().build())));
    //public static final RegistryObject<Item> CERULEAN_VINE_PATCH = ITEMS.register("cerulean_vine_patch", () -> new Item(new Item.Properties()));

}
