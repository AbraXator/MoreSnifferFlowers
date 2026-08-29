package net.abraxator.moresnifferflowers.mixins.client;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.HardenedMouthCapability;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.init.config.ModClientConfig;
import net.abraxator.moresnifferflowers.init.MSFEffects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends EffectRenderingInventoryScreen<InventoryMenu> implements RecipeUpdateListener {
    @Unique
    private static final ResourceLocation TEXTURE_LOCATION = MoreSnifferFlowers.loc("textures/gui/container/hardened_mouth.png");

    public InventoryScreenMixin(InventoryMenu menu, Inventory playerInventory, Component title, int moreSnifferFlowers$mouthSlotX, int moreSnifferFlowers$mouthSlotY) {
        super(menu, playerInventory, title);
    }

    @Inject(method = "renderBg", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V"))
    public void render(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
        Player player = this.minecraft.player;
        if (player.hasEffect(MSFEffects.HARDENED_MOUTH)){

            int x = this.leftPos + ModClientConfig.HARDENED_MOUTH_X.get();
            int y = this.topPos + ModClientConfig.HARDENED_MOUTH_Y.get();
            guiGraphics.blit(TEXTURE_LOCATION, x, y, 0, 0, 24, 60);

            HardenedMouthCapability hardenedMouthCapability = player.getData(MSFDataAttachments.HARDENED_MOUTH);

            float maxCooldown = (float) hardenedMouthCapability.getMaxCooldown(player);
            float cooldown = (float) hardenedMouthCapability.getCooldown();
            int height = Math.round(14F - (14F * (cooldown / maxCooldown)));

            guiGraphics.blit(TEXTURE_LOCATION, x + 5, y + 23 + 14 - height , 32, 14 - height, 14, height);

        }
    }
}
