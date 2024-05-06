package net.abraxator.moresnifferflowers.data.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;

import java.util.Optional;

public class UsedBonmeelTrigger extends SimpleCriterionTrigger<UsedBonmeelTrigger.Instance> {
    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public static record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<UsedBonmeelTrigger.Instance> CODEC = RecordCodecBuilder.create(instanceInstance -> instanceInstance
                .group(ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player")
                        .forGetter(UsedBonmeelTrigger.Instance::player))
                .apply(instanceInstance, UsedBonmeelTrigger.Instance::new));
    }
}
