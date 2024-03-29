package net.abraxator.moresnifferflowers.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin extends net.minecraftforge.common.capabilities.CapabilityProvider<ItemStack> implements net.minecraftforge.common.extensions.IForgeItemStack {
    @Shadow public abstract CompoundTag getOrCreateTag();

    protected ItemStackMixin(Class<ItemStack> baseClass) {
        super(baseClass);
    }

    @Inject(method = "getHoverName", at=@At("TAIL"), cancellable=true)
    public void moresnifferflowers$getHoverName(CallbackInfoReturnable<Component> callbackInfo) {
        if(getOrCreateTag().contains("isMoreSnifferFlowers")) {
            callbackInfo.setReturnValue(Component.translatable("hover_name.moresnifferflowers.extracted_bottle"));
        }
    }
}
