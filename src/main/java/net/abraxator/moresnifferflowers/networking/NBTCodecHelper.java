package net.abraxator.moresnifferflowers.networking;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class NBTCodecHelper {

    public static <T> void encode(Codec<T> codec, T data, CompoundTag compoundTag, String name){
        if (data == null){
            return;
        }

        codec.encodeStart(NbtOps.INSTANCE, data)
                .resultOrPartial(MoreSnifferFlowers.LOGGER::error)
                .ifPresent(tag -> compoundTag.put(name, tag));
    }

    public static <T> T decode(Codec<T> codec, CompoundTag compoundTag, String name, Supplier<T> orElse){
        Tag tag = compoundTag.get(name);

        return codec.decode(NbtOps.INSTANCE, tag)
                .resultOrPartial(MoreSnifferFlowers.LOGGER::error)
                .map(Pair::getFirst).orElseGet(orElse);
    }

    public static  <T> @Nullable T decode(Codec<T> codec, CompoundTag compoundTag, String name){
        if (!compoundTag.contains(name)){
            return null;
        }

        return decode(codec, compoundTag, name, () -> null);
    }

}
