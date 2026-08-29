package net.abraxator.moresnifferflowers.effects;

import net.abraxator.moresnifferflowers.init.MSFEffects;
import net.abraxator.moresnifferflowers.init.MSFTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExtractedEffect extends MobEffect {
    public ExtractedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
        cures.clear();
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        List<MobEffectInstance> activeEffects = new ArrayList<>(livingEntity.getActiveEffects());
        activeEffects = activeEffects.stream().filter(mobEffectInstance -> !mobEffectInstance.getEffect().is(MSFTags.ModEffectTags.EXTRACTION_BLACKLIST)).toList();

        if (activeEffects.size() <= 1){
            livingEntity.removeEffect(MSFEffects.EXTRACTED);
        };

        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
