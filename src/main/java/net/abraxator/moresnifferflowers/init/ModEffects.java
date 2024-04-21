package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.effects.ExtractedEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final StatusEffect EXTRACTED = registerStatusEffect("extracted",
            new ExtractedEffect(StatusEffectCategory.NEUTRAL, 14058905));

    private static StatusEffect registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(MoreSnifferFlowers.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        MoreSnifferFlowers.LOGGER.info("Registering Effects for" + MoreSnifferFlowers.MOD_ID);

    }
}
