package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.effects.GluedEffect;
import net.abraxator.moresnifferflowers.init.MSFEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

    @Shadow private Level level;

    @Inject(method = "clearFire", at = @At("TAIL"))
    public void clearGlued(CallbackInfo ci){
        Entity entity = (Entity)(Object)this;

        if(entity instanceof LivingEntity livingEntity && livingEntity.hasEffect(MSFEffects.GLUED) && !level.isClientSide()){
            livingEntity.removeEffect(MSFEffects.GLUED);
            GluedEffect.setAndSync(livingEntity, false, true);
        }
    }
}
