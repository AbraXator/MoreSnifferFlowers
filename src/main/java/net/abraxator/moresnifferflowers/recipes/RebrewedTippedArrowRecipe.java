package net.abraxator.moresnifferflowers.recipes;

import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RebrewedTippedArrowRecipe extends CustomRecipe {
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
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        ItemStack itemstack = pContainer.getItem(1 + pContainer.getWidth());
        if (!itemstack.is(ModItems.REBREWED_LINGERING_POTION)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemstack1 = new ItemStack(Items.TIPPED_ARROW, 8);   
            itemstack1.setTag(itemstack.getOrCreateTag());
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
