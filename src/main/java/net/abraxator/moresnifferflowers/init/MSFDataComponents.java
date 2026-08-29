package net.abraxator.moresnifferflowers.init;

import com.mojang.serialization.Codec;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.components.DyespriaMode;
import net.abraxator.moresnifferflowers.components.PatternspriaMode;
import net.abraxator.moresnifferflowers.components.RootedSoup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public interface MSFDataComponents {
    DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = 
            DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, MoreSnifferFlowers.MOD_ID);

    Supplier<DataComponentType<Dye>> DYE = DATA_COMPONENTS.register("dye", () -> DataComponentType.<Dye>builder().persistent(Dye.CODEC).cacheEncoding().build());

    Supplier<DataComponentType<DyespriaMode>> DYESPRIA_MODE =
            DATA_COMPONENTS.register("dyespria_mode", () -> DataComponentType.<DyespriaMode>builder()
                    .persistent(DyespriaMode.CODEC)
                    .networkSynchronized(DyespriaMode.STREAM_CODEC)
                    .cacheEncoding()
                    .build());

    Supplier<DataComponentType<PatternspriaMode>> PATTERNSPRIA_MODE =
            DATA_COMPONENTS.register("patternspria_mode", () -> DataComponentType.<PatternspriaMode>builder()
                    .persistent(PatternspriaMode.CODEC)
                    .networkSynchronized(PatternspriaMode.STREAM_CODEC)
                    .cacheEncoding()
                    .build());

    Supplier<DataComponentType<RootedSoup>> ROOTED_SOUP =
            DATA_COMPONENTS.register("rooted_soup", () -> DataComponentType.<RootedSoup>builder()
                    .persistent(RootedSoup.CODEC)
                    .networkSynchronized(RootedSoup.STREAM_CODEC)
                    .build());

    Supplier<DataComponentType<List<RootedSoup.RootedEffect>>> ROOTED_EFFECTS =
            DATA_COMPONENTS.register("rooted_effects", () -> DataComponentType.<List<RootedSoup.RootedEffect>>builder()
                    .persistent(RootedSoup.RootedEffect.LIST_CODEC)
                    .networkSynchronized(RootedSoup.RootedEffect.LIST_STREAM_CODEC)
                    .build());

    Supplier<DataComponentType<List<ItemStack>>> ROOTED_INGREDIENTS =
            DATA_COMPONENTS.register("rooted_ingredients", () -> DataComponentType.<List<ItemStack>>builder()
                    .persistent(ItemStack.CODEC.listOf())
                    .networkSynchronized(ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()))
                    .build());

    //Integer Land
    Supplier<DataComponentType<Integer>> USES = DATA_COMPONENTS.register("uses", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> COLOR = DATA_COMPONENTS.register("color", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> COLOR_ID = DATA_COMPONENTS.register("color_id", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> PATTERN_ID = DATA_COMPONENTS.register("pattern_id", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> AMOUNT = DATA_COMPONENTS.register("amount", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());


}
