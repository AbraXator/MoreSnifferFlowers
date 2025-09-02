package net.abraxator.moresnifferflowers.client.gui.screen.cookbook;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.nutrition.Nutrition;
import net.abraxator.moresnifferflowers.nutrition.NutritionEntry;
import net.abraxator.moresnifferflowers.nutrition.NutritionLoader;
import net.abraxator.moresnifferflowers.nutrition.NutritionType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class CookbookScreen extends Screen {
    private static final ResourceLocation TEXTURE = MoreSnifferFlowers.loc("textures/gui/cookbook.png");
    public static final ResourceLocation RENDERABLES = MoreSnifferFlowers.loc("textures/gui/cookbook_renderables.png");
    public static final ResourceLocation GUIDE_0 = MoreSnifferFlowers.loc("textures/gui/cookbook_guide1.png");
    public static final ResourceLocation GUIDE_1 = MoreSnifferFlowers.loc("textures/gui/cookbook_guide2.png");

    private final int ROWS = 8;
    private final int COLUMNS = 5;
    private final int PAGE_SIZE = ROWS * COLUMNS;
    private final int SCROLLBAR_HEIGHT = 142;
    private final int SCROLLER_HEIGHT = 15;
    private final List<String> mods;
    private final Set<Item> unlocked;
    public Page page = Page.CONTENTS;
    public int guide_page = 0;
    public NutritionType type;
    private List<Nutrition> nutritions = new ArrayList<>();
    private float scrollOffs;
    private int startIndex;
    private boolean isScrolling;
    public int yOffset = 0;
    public int yTotal = 0;
    public static int MAX_Y = 140;


    public CookbookScreen(Set<Item> unlocked) {
        super(Component.empty());
        this.mods = new ArrayList<>(NutritionLoader.modNutritions.keySet());
        this.unlocked = unlocked;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        int x = (this.width - 272) / 2;
        int y = (this.height - 180) / 2;

        if(page == Page.ITEMS) {
            this.renderItems(guiGraphics, mouseX, mouseY, x, y);
        }

        if (page == Page.GUIDE){
            this.renderGuide(guiGraphics, mouseX, mouseY, x, y);
        }
        this.renderContents(guiGraphics, mouseX, mouseY, x, y);

        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        int x = (this.width - 272) / 2;
        int y = (this.height - 180) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, 272, 180, 512, 256);
    }

    public void renderNutritionInfo(GuiGraphics guiGraphics, Nutrition nutrition) {
        int x = (this.width - 272) / 2;
        int y = (this.height - 180) / 2;
        int xPos = x + 150;
        int yPos = y + 20;
        ItemStack item = nutrition.getItem().getDefaultInstance();
        String string = item.getDisplayName().getString();
        int nameLength = string.length();

        guiGraphics.drawWordWrap(font, FormattedText.of(string, Style.EMPTY.withBold(true).withUnderlined(true)), xPos, yPos, 108, ChatFormatting.DARK_GRAY.getColor());
        if (nameLength > 16) yPos += 9;
        for (NutritionEntry nutritionEntry : nutrition.getNutritionEntries()) {
            yPos += 10;
            MutableComponent nutritionName = Component.translatable("gui.moresnifferflowers.cookbook." + nutritionEntry.nutrition().name).withStyle(ChatFormatting.BOLD);
            guiGraphics.drawString(font, nutritionName.append(" : ").append(String.valueOf(nutritionEntry.weight())), xPos, yPos, nutritionEntry.nutrition().color);
        }
    }

    public void renderEffectInfo(GuiGraphics guiGraphics, MobEffect effect, boolean isPositive) {
        int x = (this.width - 272) / 2;
        int y = (this.height - 180) / 2;
        int xPos = x + 150;
        int yPos = y + 20;
        String string = effect.getDisplayName().getString();
        int nameLength = string.length();
        int color = isPositive ? 0x67911c : 0x8d2a22;

        guiGraphics.drawWordWrap(font, FormattedText.of(string, Style.EMPTY.withBold(true).withUnderlined(true)), xPos, yPos, 100, color);

        yPos += 15;
        MutableComponent effectDescription = Component.translatable(effect.getDescriptionId() + ".description").withStyle(ChatFormatting.DARK_GRAY);
        guiGraphics.drawWordWrap(font, effectDescription, xPos, yPos, 108, ChatFormatting.DARK_GRAY.getColor());
    }

    private void renderGuide(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        ResourceLocation texture = guide_page == 0 ? GUIDE_0 : GUIDE_1;
        guiGraphics.blit(texture, x  + 10, y, 0, 0, 256, 176);
    }


    private void renderItems(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        int xPos = 18;
        int yPos = 16;
        int yStarting = yPos;
        guiGraphics.blit(RENDERABLES, x + 17, y + 15, 25, 0, 111, 144);

        for (int i = startIndex * COLUMNS + 1; i < startIndex * COLUMNS  + 1 + PAGE_SIZE && i < this.nutritions.size() + 1; i++) {
            Nutrition nutrition = nutritions.get(i - 1);
            boolean unlocked = this.unlocked.contains(nutrition.getItem());
            
            nutrition = unlocked ? nutrition : Nutrition.EMPTY;
            if (yPos >= 16) addRenderableWidget(new ItemWidget(x + xPos, y + yPos, Component.empty(), nutrition, this));

            if (i % COLUMNS != 0) {
                xPos += 18;
            } else {
                yPos += 18;
                xPos = 18;
            }
        }

        if (xPos > 18) yPos += 18;
        xPos = 18;
        yPos += 4;
      //  yPos += startIndex * 18;

        if (canRender(yPos)) addRenderableWidget(new EffectWidget(x + xPos, y + yPos, Component.empty(), type, this, false));
        yPos += 25;
        if (canRender(yPos))  addRenderableWidget(new EffectWidget(x + xPos, y + yPos, Component.empty(), type, this, true));

        boolean scrollable = yTotal > MAX_Y;
        int scrollAmount = (int) ((SCROLLBAR_HEIGHT - SCROLLER_HEIGHT) * this.scrollOffs);
        guiGraphics.blit(RENDERABLES, x + 115, y + 16 + scrollAmount, scrollable ? 0 : 12, 0, 12, 15);

        this.yTotal = yPos - yStarting + startIndex * 18 + 10;
    }

    public boolean canRender(int yPos){
        return yPos >= 16 && yPos <= MAX_Y + 10;
    }

    private void renderContents(GuiGraphics guiGraphics, int mouseX, int mouseY, int x, int y) {
        for (int i = 0; i < NutritionType.values().length; i++) {
            NutritionType type = NutritionType.byId(i);
            this.addRenderableWidget(new TypeWidget(x + 271, y + 24*i + 13 + i*2, type, 24, 24, Component.literal(type.name), this));
        }
        int i = 5; // the guide tab
        this.addRenderableWidget(new TypeWidget(x + 271, y + 24*i + 13 + i*2, null, 24, 24, Component.literal("Guide"), this));
    }
    
    public void pageToItems(NutritionType type) {
        List<Nutrition> list = new ArrayList<>(NutritionLoader.typeNutritions.get(type).stream().toList());
        list.sort(Comparator.comparing( nutrition -> {
            float weight = 0;
            for (NutritionEntry entry : nutrition.getNutritionEntries()){
                    if (entry.nutrition().equals(type)){
                        weight += entry.weight();
                    } else {
                        weight += 0.001f* entry.weight();
                    }
            }
            return -weight; // .reversed doesnt work for some reason???
        }));
        this.nutritions = list;
        this.turnPage(Page.ITEMS);
    }
    
    public void turnPage(Page page) {
        startIndex = 0;
        yTotal = 0;
        guide_page = 0;
        this.page = page;
        this.clearWidgets();
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int x = (this.width - 272) / 2;
        int y = (this.height - 180) / 2;

        if (page == Page.GUIDE){
            if (guide_page == 0) {
                guide_page = 1;
            } else if (guide_page == 1) {
                guide_page = 0;
            }
        }

        this.isScrolling = isMouseOver(mouseX, mouseY, x + 115, y + 15, 15, MAX_Y) && this.page == Page.ITEMS;
        
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(this.page == Page.ITEMS && this.isScrolling) {
            mouseScrolled(mouseX, mouseY, 0, - dragY / 40);
            return true;
        }
        
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int totalItems = this.nutritions.size();
        int maxRows = Math.max(1, (int) Math.ceil((double) totalItems / COLUMNS));
        float value = (yTotal - MAX_Y) / 18f;
        int scrollableRows = Math.max(0, Mth.ceil(value));

        if (scrollableRows > 0) {
            float f = (float) scrollY / (float) scrollableRows;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            
            this.startIndex = (int) Math.max((this.scrollOffs * scrollableRows), 0);

            if (startIndex > scrollableRows) startIndex = scrollableRows;
            clearWidgets();
        }

        return true;
    }

    private int getTotalRowCount() {
        return Mth.positiveCeilDiv(this.nutritions.size(), 5);
    }

    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {
        return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);
    }
    
    public enum Page {
        CONTENTS,
        ITEMS,
        GUIDE
    }
}
