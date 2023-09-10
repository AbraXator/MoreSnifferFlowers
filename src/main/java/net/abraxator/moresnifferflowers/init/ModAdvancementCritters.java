package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModAdvancementCritters {
    public static final UsedDyespriaTrigger USED_DYESPRIA = CriteriaTriggers.register(new UsedDyespriaTrigger());
    public static final EarnSnifferAdvancementTrigger EARN_SNIFFER_ADVANCEMENT = CriteriaTriggers.register(new EarnSnifferAdvancementTrigger());

    public static void init() {}
}
