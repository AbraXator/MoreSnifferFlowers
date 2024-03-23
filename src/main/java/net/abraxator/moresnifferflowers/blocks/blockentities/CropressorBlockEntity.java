package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Predicate;

public class CropressorBlockEntity extends ModBlockEntity {
    public NonNullList<ItemStack> inv = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
    private boolean hasFinished;
    private static final int INV_SIZE = 16;

    public CropressorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CROPRESSOR.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        Container container = new SimpleContainer(INV_SIZE);
        int i = 0;
        for(ItemStack itemStack : inv) {
            if(!itemStack.isEmpty()) {
                container.setItem(i, new ItemStack(itemStack.getItem(), 1));
                i++;
            }
        }

        Optional<CropressingRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.CROPRESSING.get(), container, level);
        var list = level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CROPRESSING.get());
        System.out.println(inv.stream().filter(Predicate.not(ItemStack::isEmpty)).count());

        if(recipe.isPresent()) {
            CropressingRecipe cropressorRecipe = recipe.get();
            Vec3 pos = worldPosition.getCenter().add(0, 0.5, 0);
            level.addFreshEntity(new ItemEntity(level, pos.x, pos.y, pos.z, cropressorRecipe.result()));
            inv = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
        }
    }

    public NonNullList<ItemStack> getInventory() {
        return inv;
    }

    public int addItem(ItemStack pStack) {
        for(int i = 0; i < this.inv.size(); ++i) {
            if (this.inv.get(i).isEmpty()) {
                inv.set(i, pStack);
                return i;
            }
        }

        return -1;
    }

    public void setItems(NonNullList<ItemStack> pItems) {
        this.inv = pItems;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        ContainerHelper.saveAllItems(pTag, inv);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inv = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, inv);
    }
}
