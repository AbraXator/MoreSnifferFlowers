package net.abraxator.moresnifferflowers.compat.jei.rebrewing;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class RebrewingCategory implements IRecipeCategory<JeiRebrewingRecipe> {
    public static final RecipeType<JeiRebrewingRecipe> REBREWING = RecipeType.create(MoreSnifferFlowers.MOD_ID, "rebrewing", JeiRebrewingRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public RebrewingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(MoreSnifferFlowers.loc("textures/gui/container/rebrewing_jei.png"), 0, 0, 176, 84);
        this.icon = guiHelper.createDrawableItemStack(ModItems.REBREWING_STAND.get().getDefaultInstance());
        this.localizedName = Component.translatableWithFallback("gui.moresnifferflowers.rebrewing_category", "Rebrewing");
    }

    @Override
    public RecipeType<JeiRebrewingRecipe> getRecipeType() {
        return REBREWING;
    }

    @Override
    public Component getTitle() {
        return this.localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, JeiRebrewingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 40).addItemStack(ModItems.CROPRESSED_NETHERWART.get().getDefaultInstance());
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 36).addItemStack(recipe.extractedPotion());
        builder.addSlot(RecipeIngredientRole.INPUT, 90, 36).addItemStack(recipe.ingredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 133, 36).addItemStack(recipe.rebrewedPotion());
    }

    @Override
    public void draw(JeiRebrewingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        String text = "4";
        int width = minecraft.font.width(text);
        int x = getWidth() - 140 - width;
        int y = 16;
        guiGraphics.drawString(minecraft.font, text, x, y, 0xa42429, false);
    }
}
