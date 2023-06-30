package net.abraxator.ceruleanvines.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.abraxator.ceruleanvines.CeruleanVines;
import net.abraxator.ceruleanvines.init.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CeruleanlyVinedOverlay implements IGuiOverlay {
    public static final ResourceLocation CERULEAN_VINED_EFFECTS = new ResourceLocation(CeruleanVines.MOD_ID, "textures/misc/cerulean_vine_outline.png");

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        MobEffectInstance effectInstance = Minecraft.getInstance().player.getEffect(ModMobEffects.CERULEANLY_VINED.get());
        if(effectInstance != null) {
            //float i = Mth.lerp(effectInstance.getDuration() / 60, 0.0F, 1.0F);
            this.renderTextureOverlay(guiGraphics, CERULEAN_VINED_EFFECTS, 1.0F);
        }
    }

    public void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation location, float alpha) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        guiGraphics.blit(location, 0, 0, -90, 0.0F, 0.0F, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight(), Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
