package net.abraxator.ceruleanvines.init;

import com.mojang.serialization.Codec;
import net.abraxator.ceruleanvines.MoreSnifferFlowers;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(
            ForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(), MoreSnifferFlowers.MOD_ID);

    //public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SNIFFER_LOOTTABLE = LOOT_MODIFIERS.register("sniffer", SnifferLootModifier.CODEC);
}
