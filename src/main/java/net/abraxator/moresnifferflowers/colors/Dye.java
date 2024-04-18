package net.abraxator.moresnifferflowers.colors;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;

public record Dye(DyeColor color, int amount) {
    public boolean isEmpty() {
        return Dye.this.amount <= 0;
    }
    
    public static Dye getDyeFromStack(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        int colorId = tag.getInt("color");
        int amount = tag.getInt("amount");

        return new Dye(DyeColor.byId(colorId), amount);
    }
    
    public static ItemStack stackFromDye(Dye dye) {
        return new ItemStack(DyeItem.byColor(dye.color), dye.amount);
    }    
    
    public static boolean dyeCheck(Dye dye, ItemStack dyeToInsert) {
        DyeItem dyeToInsertItem = ((DyeItem) dyeToInsert.getItem());

        return dye.color.equals(dyeToInsertItem.getDyeColor());
    }
    
    public static int colorForDye(Colorable colorable, DyeColor dyeColor) {
        return colorable.colorValues().getOrDefault(dyeColor, -1);
    }

    public static void setDyeToStack(ItemStack stack, ItemStack dyeToInset, int amount) {
        var dyeColor = dyeToInset.getItem() instanceof DyeItem ? ((DyeItem) dyeToInset.getItem()).getDyeColor() : DyeColor.WHITE;
        
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("color", dyeColor.getId());
        tag.putInt("amount", amount);
        stack.setTag(tag);
    }
    
    public static void setDyeColorToStack(ItemStack stack, DyeColor color, int amount) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("color", color.getId());
        tag.putInt("amount", amount);
        stack.setTag(tag);
    }
}