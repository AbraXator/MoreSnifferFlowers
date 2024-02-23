package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAdvancementCritters {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, UsedDyespriaTrigger> USED_DYESPRIA = TRIGGERS.register("used_dyespria", UsedDyespriaTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, EarnSnifferAdvancementTrigger> EARN_SNIFFER_ADVANCEMENT = TRIGGERS.register("earn_sniffer_advancement", EarnSnifferAdvancementTrigger::new);

    public static void init() {}
}
