package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.abraxator.moresnifferflowers.recipes.CropressorRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class CropressorBlockEntity extends ModBlockEntity {
    private NonNullList<ItemStack> inv = NonNullList.withSize(9, ItemStack.EMPTY);
    private boolean hasFinished;

    public CropressorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CROPRESSOR.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        Container container = new SimpleContainer(9);
        int i = 0;
        for(ItemStack itemStack : inv) {
            if(!itemStack.isEmpty()) {
                container.setItem(i, new ItemStack(itemStack.getItem(), 1));
                i++;
            }
        }
        Optional<CropressorRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.CROPRESSOR.get(), container, level);
        var list = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CROPRESSOR.get());
        System.out.println(list);
        if(recipe.isPresent()) {
            CropressorRecipe cropressorRecipe = recipe.get();
            inv = NonNullList.of(cropressorRecipe.result);
        }
    }

    public void addItem(ItemStack itemStack) {
        int i = -1;

        while(i + 1 != 9 && inv.get(i + 1).is(ItemStack.EMPTY.getItem())) {
            i++;
        }

        if(i > 0) {
            inv.set(i, itemStack);
            itemStack.shrink(1);
            this.setChanged();
        }
    }

    public NonNullList<ItemStack> getInventory() {
        return inv;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("size", inv.size());
        for(int i = 0; i < inv.size(); i++) {
            pTag.put("item_" + i, inv.get(i).serializeNBT());
        }
        pTag.putBoolean("finished", hasFinished);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inv = NonNullList.withSize(9, ItemStack.EMPTY);
        for(int i = 0; i < pTag.getInt("size"); i++) {
            inv.set(i, ItemStack.of(pTag.getCompound("item_" + i)));
        }
        hasFinished = pTag.getBoolean("finished");
    }
}
