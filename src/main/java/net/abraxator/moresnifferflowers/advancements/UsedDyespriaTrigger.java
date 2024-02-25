package net.abraxator.moresnifferflowers.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.dynamic.Codecs;

public class UsedDyespriaTrigger extends AbstractCriterion<UsedDyespriaTrigger.Instance> {
    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, instance -> true);
    }

    @Override
    public Codec<Instance> getConditionsCodec() {
        return Instance.CODEC;
    }

    public static record Instance(Optional<LootContextPredicate> player) implements Conditions {
        public static final Codec<UsedDyespriaTrigger.Instance> CODEC = RecordCodecBuilder.create(instanceInstance -> instanceInstance
                .group(Codecs.createStrictOptionalFieldCodec(EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC, "player")
                        .forGetter(UsedDyespriaTrigger.Instance::player))
                .apply(instanceInstance, UsedDyespriaTrigger.Instance::new));
    }
}
