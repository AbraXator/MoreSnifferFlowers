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
import net.minecraft.world.World;

import java.awt.*;
import java.util.Optional;

public class CropressorBlockEntity extends ModBlockEntity {
    public ItemStack content = ItemStack.EMPTY;
    public ItemStack result = ItemStack.EMPTY;
    public int progress = 0;
    public final int MAX_PROGRESS = 100;
    private static final int INV_SIZE = 16;
 //   private final RecipeManager.CachedCheck<Container, CropressingRecipe> quickCheck = RecipeManager.createCheck(ModRecipeTypes.CROPRESSING.get());

    public CropressorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CROPRESSOR_BLOCK_ENTITY, pPos, pBlockState);
    }

//    @Override
//    public void tick(World world) {
//        if(content.getCount() >= INV_SIZE) {
//            progress++;
//            var container = new SimpleContainer(content);
//            var cropressingRecipe = quickCheck.getRecipeFor(container, level).get();
//            result = cropressingRecipe.assemble(container, level.registryAccess());
//            if(progress % 10 == 0) {
//                world.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
//            }
//
//            if(progress >= MAX_PROGRESS) {
//                Vec3 blockPos = getBlockPos().relative(getBlockState().getValue(BaseCropressorBlock.FACING).getOpposite()).getCenter();
//                ItemEntity entity = new ItemEntity(level, blockPos.x, blockPos.y + 0.5, blockPos.z, result);
//                level.addFreshEntity(entity);
//                content = ItemStack.EMPTY;
//                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
//                level.gameEvent(GameEvent.BLOCK_CHANGE, getBlockPos(), GameEvent.Context.of(getBlockState()));
//                setChanged();
//                progress = 0;
//            }
//        }
//    }
//
//    public boolean canInteract() {
//        return 0 >= progress || content.getCount() >= INV_SIZE;
//    }
//
//    public void addItem(ItemStack pStack, Level level) {
//        if(content.getCount() >= INV_SIZE || (!content.is(pStack.getItem()) && !content.isEmpty())) return;
//        var freeSpace = INV_SIZE - content.getCount();
//        var toInsert = Math.min(pStack.getCount(), freeSpace);
//        content = new ItemStack(pStack.getItem(), content.getCount() + toInsert);
//        pStack.shrink(toInsert);
//        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
//    }
//
//    @Override
//    protected void saveAdditional(CompoundTag pTag) {
//        super.saveAdditional(pTag);
//        pTag.put("content", content.serializeNBT());
//        pTag.putInt("progress", progress);
//        pTag.put("result", result.serializeNBT());
//    }
//
//    @Override
//    public void load(CompoundTag pTag) {
//        super.load(pTag);
//        content = ItemStack.of(pTag.getCompound("content"));
//        progress = pTag.getInt("progress");
//        result = ItemStack.of(pTag.getCompound("result"));
//    }
//
//    public ClientboundBlockEntityDataPacket getUpdatePacket() {
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//
//    public CompoundTag getUpdateTag() {
//        CompoundTag compoundtag = new CompoundTag();
//        compoundtag.put("result", result.serializeNBT());
//        compoundtag.putInt("progress", progress);
//        return compoundtag;
//    }
//
//    @Override
//    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//        super.onDataPacket(net, pkt);
//    }
}
