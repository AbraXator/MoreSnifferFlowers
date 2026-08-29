package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.effects.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface MSFEffects {
    DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MoreSnifferFlowers.MOD_ID);
    
    DeferredHolder<MobEffect, MobEffect> EXTRACTED = EFFECTS.register("extracted", () -> new ExtractedEffect(MobEffectCategory.NEUTRAL, 14058905));

    //Sour
    DeferredHolder<MobEffect, MobEffect> SLIPPERY = EFFECTS.register("slippery", () -> new SlipperyEffect(MobEffectCategory.HARMFUL, 0xffcd00));
    DeferredHolder<MobEffect, MobEffect> UNTOUCHABLE = EFFECTS.register("untouchable", () -> new UntouchableEffect(MobEffectCategory.BENEFICIAL, 0xfffa5e));
    //Salty
    DeferredHolder<MobEffect, MobEffect> SALTY = EFFECTS.register("salty", () -> new SaltyEffect(MobEffectCategory.HARMFUL, 0x0cab1ac));
    DeferredHolder<MobEffect, MobEffect> COMBO_MEAL = EFFECTS.register("combo_meal", () -> new ComboMealEffect(MobEffectCategory.BENEFICIAL, 0xf2e9da));
    //Spicy
    DeferredHolder<MobEffect, MobEffect> PANTS_ON_FIRE = EFFECTS.register("pants_on_fire", () -> new PantsOnFireEffect(MobEffectCategory.HARMFUL, 0x3e4d94));
    DeferredHolder<MobEffect, MobEffect> HARDENED_MOUTH = EFFECTS.register("hardened_mouth", () -> new HardenedMouthEffect(MobEffectCategory.BENEFICIAL, 0xc56922));
    //Sweet
    DeferredHolder<MobEffect, MobEffect> STICKY = EFFECTS.register("sticky", () -> new StickyEffect(MobEffectCategory.HARMFUL, 0xa3679c));
    DeferredHolder<MobEffect, MobEffect> GLUING_TOUCH = EFFECTS.register("gluing_touch", () -> new SimpleEffect(MobEffectCategory.BENEFICIAL, 0xe084b8));
    DeferredHolder<MobEffect, MobEffect> GLUED = EFFECTS.register("glued", () -> new GluedEffect(MobEffectCategory.HARMFUL, 0x863c93)); // caused by the 2 above

    // Neutral
    DeferredHolder<MobEffect, MobEffect> BLAND = EFFECTS.register("bland", () -> new SimpleEffect(MobEffectCategory.HARMFUL, 0x7a8484));
    DeferredHolder<MobEffect, MobEffect> WELL_BALANCED = EFFECTS.register("well_balanced", () -> new SimpleEffect(MobEffectCategory.BENEFICIAL, 0xc5a73c)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, MoreSnifferFlowers.loc("well_balanced_damage"), 0.2F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, MoreSnifferFlowers.loc("well_balanced_speed"), 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.LUCK, MoreSnifferFlowers.loc("well_balanced_luck"), 0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, MoreSnifferFlowers.loc("well_balanced_speed"), 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.MAX_HEALTH, MoreSnifferFlowers.loc("well_balanced_health"), 0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ARMOR, MoreSnifferFlowers.loc("well_balanced_armor"), 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ARMOR_TOUGHNESS, MoreSnifferFlowers.loc("well_balanced_toughness"), 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_KNOCKBACK, MoreSnifferFlowers.loc("well_balanced_knockback"), 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,MoreSnifferFlowers.loc("well_balanced_knockback_res"), 0.1F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );

}
