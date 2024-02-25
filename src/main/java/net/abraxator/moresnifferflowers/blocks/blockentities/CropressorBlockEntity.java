package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.abraxator.moresnifferflowers.recipes.CropressorRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import java.util.Optional;

public class CropressorBlockEntity extends ModBlockEntity {
    private DefaultedList<ItemStack> inv = DefaultedList.ofSize(9, ItemStack.EMPTY);
    private boolean hasFinished;

    public CropressorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CROPRESSOR.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        Inventory container = new SimpleInventory(9);
        int i = 0;
        for(ItemStack itemStack : inv) {
            if(!itemStack.isEmpty()) {
                container.setStack(i, new ItemStack(itemStack.getItem(), 1));
                i++;
            }
        }
        Optional<RecipeEntry<CropressorRecipe>> recipe = world.getRecipeManager().getFirstMatch(ModRecipeTypes.CROPRESSOR.get(), container, world);
        var list = world.getRecipeManager().listAllOfType(ModRecipeTypes.CROPRESSOR.get());
        System.out.println(list);
        if(recipe.isPresent()) {
            CropressorRecipe cropressorRecipe = recipe.get().value();
            inv = DefaultedList.copyOf(cropressorRecipe.result);
        }
    }

    public void addItem(ItemStack itemStack) {
        int i = -1;

        while(i + 1 != 9 && inv.get(i + 1).isOf(ItemStack.EMPTY.getItem())) {
            i++;
        }

        if(i > 0) {
            inv.set(i, itemStack);
            itemStack.decrement(1);
            this.markDirty();
        }
    }

    public DefaultedList<ItemStack> getInventory() {
        return inv;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    @Override
    protected void writeNbt(NbtCompound pTag) {
        super.writeNbt(pTag);
        pTag.putInt("size", inv.size());
        for(int i = 0; i < inv.size(); i++) {
            pTag.put("item_" + i, inv.get(i).serializeAttachments());
        }
        pTag.putBoolean("finished", hasFinished);
    }

    @Override
    public void readNbt(NbtCompound pTag) {
        super.readNbt(pTag);
        inv = DefaultedList.ofSize(9, ItemStack.EMPTY);
        for(int i = 0; i < pTag.getInt("size"); i++) {
            inv.set(i, ItemStack.fromNbt(pTag.getCompound("item_" + i)));
        }
        hasFinished = pTag.getBoolean("finished");
    }
}
