package net.abraxator.moresnifferflowers.recipes;

import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RebrewedTippedArrowRecipe extends CustomRecipe {
    public RebrewedTippedArrowRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput pInput, Level pLevel) {
        if (pInput.width() == 3 && pInput.height() == 3) {
            for (int i = 0; i < pInput.width(); i++) {
                for (int j = 0; j < pInput.height(); j++) {
                    ItemStack itemstack = pInput.getItem(i + j * pInput.width());
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
    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries) {
        ItemStack itemstack = pInput.getItem(1 + pInput.width());
        if (!itemstack.is(ModItems.REBREWED_LINGERING_POTION)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);
            itemstack1.set(DataComponents.POTION_CONTENTS, itemstack.get(DataComponents.POTION_CONTENTS));
            return itemstack1;
        }
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 2 && pHeight >= 2;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.REBREWED_TIPPED_ARROW.get();
    }
}
