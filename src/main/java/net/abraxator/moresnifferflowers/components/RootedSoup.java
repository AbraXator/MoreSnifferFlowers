package net.abraxator.moresnifferflowers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record RootedSoup(int food, float saturation, int maxUses) {
    public static final Codec<RootedSoup> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("food").forGetter(RootedSoup::food),
                    Codec.FLOAT.fieldOf("saturation").forGetter(RootedSoup::saturation),
                    Codec.INT.fieldOf("maxUses").forGetter(RootedSoup::maxUses)
            ).apply(instance, RootedSoup::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, RootedSoup> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, RootedSoup::food,
            ByteBufCodecs.FLOAT, RootedSoup::saturation,
            ByteBufCodecs.INT, RootedSoup::maxUses,
            RootedSoup::new
    );

    public static final Codec<List<ItemStack>> ITEM_LIST_CODEC =
            ItemStack.CODEC.listOf();


    public record RootedEffect(int id, boolean isPositive, int length, int amplifier){
        public static final Codec<RootedEffect> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.INT.fieldOf("id").forGetter(RootedEffect::id),
                        Codec.BOOL.fieldOf("isPositive").forGetter(RootedEffect::isPositive),
                        Codec.INT.fieldOf("length").forGetter(RootedEffect::length),
                        Codec.INT.fieldOf("amplifier").forGetter(RootedEffect::amplifier)
                ).apply(instance, RootedEffect::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, RootedEffect> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, RootedEffect::id,
                ByteBufCodecs.BOOL, RootedEffect::isPositive,
                ByteBufCodecs.INT, RootedEffect::length,
                ByteBufCodecs.INT, RootedEffect::amplifier,
                RootedEffect::new
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, List<RootedEffect>> LIST_STREAM_CODEC = STREAM_CODEC.apply(ByteBufCodecs.list());


        public static final Codec<List<RootedEffect>> LIST_CODEC =
                CODEC.listOf()
                        .flatXmap(
                                list -> !list.isEmpty() && list.size() <= 5
                                        ? DataResult.success(list)
                                        : DataResult.error(() -> "Effect list must have between 1 and 5 entries"),
                                DataResult::success
                        );
    }

}
