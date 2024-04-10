package net.abraxator.moresnifferflowers.client.gui.screen;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.client.gui.menu.RebrewingStandMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RebrewingStandScreen extends AbstractContainerScreen<RebrewingStandMenu> {
    public static final ResourceLocation TEXTURE = MoreSnifferFlowers.loc("textures/gui/container/rebrewing_stand.png");
    
    public RebrewingStandScreen(RebrewingStandMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        this.renderBackground(p_283479_);
        super.render(p_283479_, p_283661_, p_281248_, p_281886_);
        this.renderTooltip(p_283479_, p_281248_, p_281248_);
    }

    @Override
    protected void renderBg(GuiGraphics p_283065_, float p_97788_, int p_97789_, int p_97790_) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        
        p_283065_.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }
}
