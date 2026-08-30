package net.abraxator.moresnifferflowers.init;

import com.mojang.serialization.Codec;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.components.Corruptable;
import net.abraxator.moresnifferflowers.nutrition.Nutrition;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;

public interface MSFDataMaps {
    Set<DataMapType<?, ?>> DATA_MAPS = new HashSet<>();

    DataMapType<Block, Corruptable> CORRUPTABLE = register("corruptable", Registries.BLOCK, Corruptable.CODEC);
    DataMapType<Item, Nutrition> NUTRITION = register("nutrition", Registries.ITEM, Nutrition.CODEC);

    static<R, T> DataMapType<R, T> register(String id, ResourceKey<Registry<R>> registry, Codec<T> codec, UnaryOperator<DataMapType.Builder<T, R>> builderConsumer) {
        DataMapType<R, T> type = builderConsumer.apply(DataMapType.builder(MoreSnifferFlowers.loc(id), registry, codec).synced(codec, true)).build();
        DATA_MAPS.add(type);
        return type;
    }

    static<R, T> DataMapType<R, T> register(String id, ResourceKey<Registry<R>> registry, Codec<T> codec) {
        return register(id, registry, codec, UnaryOperator.identity());
    }

    static void init(){}
}
