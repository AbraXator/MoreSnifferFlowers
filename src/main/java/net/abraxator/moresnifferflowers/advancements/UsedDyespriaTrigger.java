package net.abraxator.moresnifferflowers.advancements;

import com.google.gson.JsonObject;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class UsedDyespriaTrigger extends SimpleCriterionTrigger<UsedDyespriaTrigger.Instance> {
    public static final ResourceLocation ID = MoreSnifferFlowers.loc("used_dyespria");

    @Override
    protected UsedDyespriaTrigger.Instance createInstance(JsonObject p_66248_, ContextAwarePredicate p_286603_, DeserializationContext p_66250_) {
        return new Instance(p_286603_);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ContextAwarePredicate p_286466_) {
            super(ID, p_286466_);
        }
    }
}
