package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin extends ThrowableItemProjectile {
    public ThrownPotionMixin(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "isLingering", at = @At("TAIL"), cancellable = true)
    private void isLingering(CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue(getItem().is(ModItems.REBREWED_LINGERING_POTION.get()) || getItem().is(Items.LINGERING_POTION));
    }
}
