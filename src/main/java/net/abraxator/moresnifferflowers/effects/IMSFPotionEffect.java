package net.abraxator.moresnifferflowers.effects;

import net.abraxator.moresnifferflowers.client.gui.slot.HardenedMouthSlot;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface IMSFPotionEffect {
    default void onEffectEnd(LivingEntity player, MobEffectInstance instance) {
    }

}
