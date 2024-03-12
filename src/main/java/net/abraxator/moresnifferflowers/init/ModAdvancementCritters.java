package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedBonmeelTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class ModAdvancementCritters {
    public static final PlayerTrigger EARN_SNIFFER_ADVANCEMENT = new PlayerTrigger();
    public static final PlayerTrigger USED_DYESPRIA = new PlayerTrigger();
    public static final PlayerTrigger USED_BONMEEL = new PlayerTrigger();

    public static Criterion<?> getSnifferAdvancement() {
        return EARN_SNIFFER_ADVANCEMENT.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
    }

    public static Criterion<?> usedDyespria() {
        return USED_DYESPRIA.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
    }

    public static Criterion<?> usedBonmeel() {
        return USED_BONMEEL.createCriterion(new PlayerTrigger.TriggerInstance(Optional.empty()));
    }

    public static void init() {
        CriteriaTriggers.register(MoreSnifferFlowers.sLoc("earn_sniffer_advancement"), EARN_SNIFFER_ADVANCEMENT);
        CriteriaTriggers.register(MoreSnifferFlowers.sLoc("used_dyespria"), USED_DYESPRIA);
        CriteriaTriggers.register(MoreSnifferFlowers.sLoc("used_bonmeel"), USED_BONMEEL);
    }
}
