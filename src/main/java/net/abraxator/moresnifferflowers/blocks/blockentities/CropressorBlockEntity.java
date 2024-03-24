package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.BaseCropressorBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Predicate;

public class CropressorBlockEntity extends ModBlockEntity {
    public NonNullList<ItemStack> inv = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
    public int progress = 0;
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
            progress += 1;
            CropressingRecipe cropressorRecipe = recipe.get();
            inv.set(0, cropressorRecipe.result());
            if(progress == 1) {
                craft(cropressorRecipe);
            }
        }
    }

    private void craft(CropressingRecipe cropressingRecipe) {
        BlockPos blockPos = worldPosition.relative(getBlockState().getValue(BaseCropressorBlock.FACING).getOpposite());
        Vec3 vec3 = blockPos.getCenter().add(0, 0.5, 0);
        ItemEntity entity = new ItemEntity(level, vec3.x, vec3.y, vec3.z, cropressingRecipe.result());
        progress = 0;
        inv = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
    }

    public boolean canInteract() {
        return 0 >= progress;
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
