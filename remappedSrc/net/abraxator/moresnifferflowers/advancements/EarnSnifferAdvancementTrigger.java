package net.abraxator.moresnifferflowers.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.dynamic.Codecs;

public class EarnSnifferAdvancementTrigger extends AbstractCriterion<EarnSnifferAdvancementTrigger.Instance> {
    public Codec<EarnSnifferAdvancementTrigger.Instance> getConditionsCodec() {
        return EarnSnifferAdvancementTrigger.Instance.CODEC;
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, instance -> true);
    }

    public static record Instance(Optional<LootContextPredicate> player) implements SimpleInstance {
        public static final Codec<EarnSnifferAdvancementTrigger.Instance> CODEC = RecordCodecBuilder.create(instanceInstance -> instanceInstance
                .group(Codecs.createStrictOptionalFieldCodec(EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC, "player")
                        .forGetter(Instance::player))
                .apply(instanceInstance, Instance::new));

        public @NotNull Optional<LootContextPredicate> player() {
            return this.player;
        }
    }
}
