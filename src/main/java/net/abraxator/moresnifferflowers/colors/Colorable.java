package net.abraxator.moresnifferflowers.colors;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Colorable {
    Map<DyeColor, Integer> colorValues();

    default ItemStack add(@Nullable ItemStack dyespria, ItemStack dyeToInsert) {
        Dye dye = dyespria == null ? Dye.getDyeFromStack(dyeToInsert) : Dye.getDyeFromStack(dyespria);
        if(dyeToInsert.getItem() instanceof DyeItem) {
            if(!dye.isEmpty()) {
                int amountInside = dye.amount();
                int freeSpace = 64 - amountInside;
                int totalDye = Math.min(amountInside + dyeToInsert.getCount(), 64); //AMOUNT TO INSERT INTO DYESPRIA
                if (!Dye.dyeCheck(dye, dyeToInsert)) {
                    //DYESPRIA HAS DIFFERENT DYE AND YOU INSERT ANOTHER 😝
                    onAddDye(dyespria, dyeToInsert, dyeToInsert.getCount());
                    dyeToInsert.shrink(totalDye);
                    return Dye.stackFromDye(dye);
                } else if(freeSpace <= 0) {
                    //DYESPRIA HAS NO SPACE 😇
                    return dyeToInsert;
                }

                //DYESPRIA HAS DYE AND YOU INSERT SAME DYE 🥳
                onAddDye(dyespria, dyeToInsert, totalDye);
                dyeToInsert.shrink(totalDye);
                return dyeToInsert;
            } else {
                //DYESPRIA HAS NO DYE 🥸
                onAddDye(dyespria, dyeToInsert, dyeToInsert.getCount());
                return ItemStack.EMPTY;
            }
        }
        return dyeToInsert;
    }
    
    void onAddDye(@Nullable ItemStack destinationStack, ItemStack dye, int amount);
}
