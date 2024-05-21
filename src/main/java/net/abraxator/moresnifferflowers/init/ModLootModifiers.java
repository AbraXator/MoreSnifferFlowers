package net.abraxator.moresnifferflowers.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.lootmodifers.AddItemsModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(
            NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MoreSnifferFlowers.MOD_ID);


    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddItemsModifier>> ADD_ITEM =
            LOOT_MODIFIERS.register("add_items", () -> AddItemsModifier.CODEC);
}
