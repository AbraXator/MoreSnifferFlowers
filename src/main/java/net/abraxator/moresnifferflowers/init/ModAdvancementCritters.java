package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;

public class ModAdvancementCritters {
    public static final PlayerTrigger EARN_SNIFFER_ADVANCEMENT = new PlayerTrigger(MoreSnifferFlowers.loc("earn_sniffer_advancement"));
    public static final PlayerTrigger USED_DYESPRIA = new PlayerTrigger(MoreSnifferFlowers.loc("used_dyespria"));
    public static final PlayerTrigger USED_BONMEEL = new PlayerTrigger(MoreSnifferFlowers.loc("used_bonmeel"));
    public static final PlayerTrigger PLACED_DYESPRIA_PLANT = new PlayerTrigger(MoreSnifferFlowers.loc("placed_dyespria_plant"));

    public static PlayerTrigger.TriggerInstance getSnifferAdvancement() {
        return new PlayerTrigger.TriggerInstance(EARN_SNIFFER_ADVANCEMENT.getId(), ContextAwarePredicate.ANY);
    }

    public static PlayerTrigger.TriggerInstance usedDyespria() {
        return new PlayerTrigger.TriggerInstance(USED_DYESPRIA.getId(), ContextAwarePredicate.ANY);
    }

    public static PlayerTrigger.TriggerInstance usedBonmeel() {
        return new PlayerTrigger.TriggerInstance(USED_BONMEEL.getId(), ContextAwarePredicate.ANY);
    }

    public static PlayerTrigger.TriggerInstance placedDyespriaPlant() {
        return new PlayerTrigger.TriggerInstance(PLACED_DYESPRIA_PLANT.getId(), ContextAwarePredicate.ANY);
    }

    public static void init() {
        CriteriaTriggers.register(EARN_SNIFFER_ADVANCEMENT);
        CriteriaTriggers.register(USED_DYESPRIA);
        CriteriaTriggers.register(USED_BONMEEL);
        CriteriaTriggers.register(PLACED_DYESPRIA_PLANT);
    }
}
