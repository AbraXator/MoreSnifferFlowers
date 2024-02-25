package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAdvancementCritters {
    public static final DeferredRegister<Criterion<?>> TRIGGERS = DeferredRegister.create(RegistryKeys.CRITERION, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<Criterion<?>, UsedDyespriaTrigger> USED_DYESPRIA = TRIGGERS.register("used_dyespria", UsedDyespriaTrigger::new);
    public static final DeferredHolder<Criterion<?>, EarnSnifferAdvancementTrigger> EARN_SNIFFER_ADVANCEMENT = TRIGGERS.register("earn_sniffer_advancement", EarnSnifferAdvancementTrigger::new);

    public static void init() {}
}
