package net.abraxator.moresnifferflowers.init;

import com.mojang.serialization.Codec;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.components.DyespriaMode;
import net.abraxator.moresnifferflowers.components.PatternspriaMode;
import net.abraxator.moresnifferflowers.components.RootedSoup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public interface MSFDataComponents {
    DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = 
            DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, MoreSnifferFlowers.MOD_ID);

    Supplier<DataComponentType<Dye>> DYE = DATA_COMPONENTS.register("dye", () -> DataComponentType.<Dye>builder().persistent(Dye.CODEC).cacheEncoding().build());

    Supplier<DataComponentType<DyespriaMode>> DYESPRIA_MODE =
            register("dyespria_mode", DyespriaMode.CODEC, DyespriaMode.STREAM_CODEC);

    Supplier<DataComponentType<PatternspriaMode>> PATTERNSPRIA_MODE =
            register("patternspria_mode", PatternspriaMode.CODEC, PatternspriaMode.STREAM_CODEC);

    Supplier<DataComponentType<RootedSoup>> ROOTED_SOUP =
            register("rooted_soup",RootedSoup.CODEC, RootedSoup.STREAM_CODEC);

    Supplier<DataComponentType<List<RootedSoup.RootedEffect>>> ROOTED_EFFECTS =
            register("rooted_effects", RootedSoup.RootedEffect.LIST_CODEC, RootedSoup.RootedEffect.LIST_STREAM_CODEC);

    Supplier<DataComponentType<List<ItemStack>>> ROOTED_INGREDIENTS =
            register("rooted_ingredients", ItemStack.CODEC.listOf(), ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()));

    //Integer Land
    Supplier<DataComponentType<Integer>> USES = DATA_COMPONENTS.register("uses", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> COLOR = DATA_COMPONENTS.register("color", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> COLOR_ID = DATA_COMPONENTS.register("color_id", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> PATTERN_ID = DATA_COMPONENTS.register("pattern_id", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    Supplier<DataComponentType<Integer>> AMOUNT = DATA_COMPONENTS.register("amount", () -> DataComponentType.<Integer>builder().persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());



    static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, @Nullable Codec<T> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return DATA_COMPONENTS.register(name, () -> {
            DataComponentType.Builder<T> builder = DataComponentType.builder();
            if (codec != null) builder.persistent(codec);
            if (streamCodec != null) builder.networkSynchronized(streamCodec);
            return builder.build();
        });

    }
}
