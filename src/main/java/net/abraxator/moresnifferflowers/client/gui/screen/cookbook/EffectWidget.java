package net.abraxator.moresnifferflowers.client.gui.screen.cookbook;

import net.abraxator.moresnifferflowers.capability.NutritionCapability;
import net.abraxator.moresnifferflowers.init.ModDataAttachments;
import net.abraxator.moresnifferflowers.nutrition.NutritionType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;

public class EffectWidget extends AbstractWidget {
    private final NutritionType nutrition;
    private final CookbookScreen screen;
    private final boolean isPositive;

    public EffectWidget(int x, int y, Component message, NutritionType nutrition, CookbookScreen screen, boolean isPositive) {
        super(x, y, 20 , 20, message);
        this.nutrition = nutrition;
        this.screen = screen;
        this.isPositive = isPositive;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        MobEffectTextureManager mobeffecttexturemanager = Minecraft.getInstance().getMobEffectTextures();
        int id = NutritionCapability.idFromNutrition(nutrition, isPositive);

        NutritionCapability capability = Minecraft.getInstance().player.getData(ModDataAttachments.NUTRITION);
        boolean isUnlocked = capability.unlockedEffects.contains(id);
        int size = isUnlocked ? 18 : 21;

        Holder<MobEffect> effect = NutritionCapability.effectFromId(id);
        if (isUnlocked) {
            guiGraphics.blit(getX(), getY(), 0, size, size, mobeffecttexturemanager.get(effect));
        } else {
            guiGraphics.blit(CookbookScreen.RENDERABLES, getX(), getY(), 0, 32, size, size);
        }

        guiGraphics.blit(CookbookScreen.RENDERABLES, getX() - 10, getY() - 2, 0, 160, 118, 22);

        String text = isPositive ? Component.translatable("gui.moresnifferflowers.cookbook.positive").getString(): Component.translatable("gui.moresnifferflowers.cookbook.negative").getString();
        int color = isPositive ? 0x67911c : 0x8d2a22;

        guiGraphics.drawWordWrap(screen.getMinecraft().font, FormattedText.of(text, Style.EMPTY.withBold(true).withUnderlined(true)), getX() + 35, getY() + 4, 100, color);

        if (isHovered && isUnlocked) {
            screen.renderEffectInfo(guiGraphics, effect.value(), isPositive);
        }

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
