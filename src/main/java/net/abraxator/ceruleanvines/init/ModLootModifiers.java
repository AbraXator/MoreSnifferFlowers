package net.abraxator.ceruleanvines.init;

import com.mojang.serialization.Codec;
import net.abraxator.ceruleanvines.MoreSnifferFlowers;
import net.abraxator.ceruleanvines.lootmodifers.AddItemsModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(
            ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MoreSnifferFlowers.MOD_ID);


    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM =
            LOOT_MODIFIERS.register("add_items", AddItemsModifier.CODEC);
}
