package net.abraxator.moresnifferflowers.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.abraxator.moresnifferflowers.client.ClientRegistration;
import net.abraxator.moresnifferflowers.init.ModCreativeTabs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
    public CreativeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @WrapOperation(method = "selectTab", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;size()I"))
    public int skipExtraSlots(NonNullList<Slot> instance, Operation<Integer> original){
        return original.call(instance) - 2;
    }

    @ModifyVariable(method = "renderTabButton", at = @At(value = "LOAD", ordinal = 0))
    protected ResourceLocation[] renderTabButton(ResourceLocation[] vanillaLoc, @Local(argsOnly = true) CreativeModeTab creativeModeTab,
                                   @Local(ordinal = 0) boolean isSelected, @Local(ordinal = 1) boolean isTop) {
        if (ClientRegistration.isBoringLoaded()) return vanillaLoc;
        if (creativeModeTab != ModCreativeTabs.MORESNIFFERFLOWERS_TAB.get()) return vanillaLoc;

        ResourceLocation[] aresourcelocation;
        if (isTop) {
            aresourcelocation = isSelected ? ModCreativeTabs.SELECTED_TOP_TABS : ModCreativeTabs.UNSELECTED_TOP_TABS;
        } else {
            aresourcelocation = isSelected ? ModCreativeTabs.SELECTED_BOTTOM_TABS : ModCreativeTabs.UNSELECTED_BOTTOM_TABS;
        }

        return aresourcelocation;
    }



}
