package net.abraxator.moresnifferflowers.data.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EarnSnifferAdvancementTrigger extends SimpleCriterionTrigger<EarnSnifferAdvancementTrigger.Instance> {
    public Codec<EarnSnifferAdvancementTrigger.Instance> codec() {
        return EarnSnifferAdvancementTrigger.Instance.CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static record Instance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<EarnSnifferAdvancementTrigger.Instance> CODEC = RecordCodecBuilder.create(instanceInstance -> instanceInstance
                .group(ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player")
                        .forGetter(Instance::player))
                .apply(instanceInstance, Instance::new));

        public @NotNull Optional<ContextAwarePredicate> player() {
            return this.player;
        }
    }
}
