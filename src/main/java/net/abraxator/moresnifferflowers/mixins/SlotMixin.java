package net.abraxator.moresnifferflowers.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Shadow public abstract ItemStack getItem();

    @Shadow @Final public Container container;

    @WrapMethod(method = "mayPickup" )
    public boolean mayPickup(Player player, Operation<Boolean> original) {
        if (this.getItem().is(MSFItems.BURNED_SLOT.get())){
            return false;
        }
        return original.call(player);
    }

    @WrapMethod(method = "isHighlightable")
    public boolean isHighlightable(Operation<Boolean> original) {
        if (this.getItem().is(MSFItems.BURNED_SLOT.get())){
            return false;
        }
        return original.call();
    }


}
