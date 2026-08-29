package net.abraxator.moresnifferflowers.effects;

import net.abraxator.moresnifferflowers.capability.GluedCapability;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GluedEffect extends MobEffect {
    public GluedEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        double y = Math.min(entity.getDeltaMovement().y, 0);
        entity.setDeltaMovement(new Vec3(0, y ,0));
        entity.setJumping(false);
        entity.hasImpulse = false;

        if (entity.level().getGameTime() % 41 == 0) {
            GluedCapability.setAndSync(entity, true, false);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}
