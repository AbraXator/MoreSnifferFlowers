package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.init.MSFItems;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Shadow public abstract ItemStack getItem();

    @Shadow @Final public Container container;

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    public void mayPickup(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (this.getItem().is(MSFItems.BURNED_SLOT.get())){
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "isHighlightable",at = @At("HEAD"), cancellable = true)
    public void isHighlightable(CallbackInfoReturnable<Boolean> cir) {
      if (this.getItem().is(MSFItems.BURNED_SLOT.get()))
          cir.setReturnValue(false);
    }


}
