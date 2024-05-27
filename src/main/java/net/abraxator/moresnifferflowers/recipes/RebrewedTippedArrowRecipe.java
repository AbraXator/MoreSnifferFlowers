package net.abraxator.moresnifferflowers.recipes;

import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.TippedArrowRecipe;
import net.minecraft.world.level.Level;

public class RebrewedTippedArrowRecipe extends TippedArrowRecipe {
    public RebrewedTippedArrowRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        if (pInv.getWidth() == 3 && pInv.getHeight() == 3) {
            for (int i = 0; i < pInv.getWidth(); i++) {
                for (int j = 0; j < pInv.getHeight(); j++) {
                    ItemStack itemstack = pInv.getItem(i + j * pInv.getWidth());
                    if (itemstack.isEmpty()) {
                        return false;
                    }

                    if (i == 1 && j == 1) {
                        if (!itemstack.is(ModItems.REBREWED_LINGERING_POTION)) {
                            return false;
                        }
                    } else if (!itemstack.is(Items.ARROW)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack assemble(CraftingContainer pCraftingContainer, HolderLookup.Provider pRegistries) {
        ItemStack itemstack = pCraftingContainer.getItem(1 + pCraftingContainer.getWidth());
        if (!itemstack.is(ModItems.REBREWED_LINGERING_POTION)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
            itemstack1.set(DataComponents.POTION_CONTENTS, itemstack.get(DataComponents.POTION_CONTENTS));
            return itemstack1;
        }
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.REBREWED_TIPPED_ARROW.get();
    }
}
