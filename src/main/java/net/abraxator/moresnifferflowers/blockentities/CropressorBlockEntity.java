package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.blocks.cropressor.CropressorBlockBase;
import net.abraxator.moresnifferflowers.client.particle.CarrotParticle;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModRecipeTypes;
import net.abraxator.moresnifferflowers.init.ModSoundEvents;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class CropressorBlockEntity extends ModBlockEntity {
    public ItemStack content = ItemStack.EMPTY;
    public ItemStack result = ItemStack.EMPTY;
    public int progress = 0;
    public final int MAX_PROGRESS = 100;
    private static final int INV_SIZE = 16;
    private final RecipeManager.CachedCheck<Container, CropressingRecipe> quickCheck = RecipeManager.createCheck(ModRecipeTypes.CROPRESSING.get());
    private boolean sound = true;

    public CropressorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CROPRESSOR.get(), pPos, pBlockState);
    }

    @Override
    public void tick(Level level) {
        var container = new SimpleContainer(content);
        var cropressingRecipeOptional = quickCheck.getRecipeFor(container, level);
        
        if(content.getCount() >= INV_SIZE && cropressingRecipeOptional.isPresent()) {
            result = cropressingRecipeOptional.get().value().result();
            progress++;

            if(sound) {
                level.playSound(null, worldPosition, ModSoundEvents.CROPRESSOR_BELT.get(), SoundSource.BLOCKS, 1.0F, (float) (1.0F + (level.getRandom().nextFloat() * 0.2)));
                sound = false;
            }
            
            if(progress % 10 == 0) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
            }

            if(progress >= MAX_PROGRESS) {
                Vec3 blockPos = getBlockPos().relative(getBlockState().getValue(CropressorBlockBase.FACING).getOpposite()).getCenter();
                ItemEntity entity = new ItemEntity(level, blockPos.x, blockPos.y + 0.5, blockPos.z, result);
                level.playSound(null, worldPosition, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.4F, 1.0F);
                level.addFreshEntity(entity);
                content = ItemStack.EMPTY;
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, getBlockPos(), GameEvent.Context.of(getBlockState()));
                setChanged();
                progress = 0;
            }
        }
    }

    public boolean canInteract() {
        return 0 >= progress || content.getCount() >= INV_SIZE;
    }

    public ItemStack addItem(ItemStack pStack, Level level) {
        if(content.getCount() >= INV_SIZE) {
            return pStack;
        } else if(!content.is(pStack.getItem()) && !content.isEmpty()) {
            var ret = content.copy();
            var toInsert = Math.min(pStack.getCount(), INV_SIZE);
            content = new ItemStack(pStack.getItem(), content.getCount() + toInsert);
            pStack.shrink(toInsert);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
            
            return ret;
        } else {
            var freeSpace = INV_SIZE - content.getCount();
            var toInsert = Math.min(pStack.getCount(), freeSpace);
            content = new ItemStack(pStack.getItem(), content.getCount() + toInsert);
            pStack.shrink(toInsert);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
            
            return pStack;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        CompoundTag tagContent = new CompoundTag();
        content.save(tagContent);
        CompoundTag tagResult = new CompoundTag();
        content.save(tagResult);
        pTag.put("content", tagContent);
        pTag.putInt("progress", progress);
        pTag.put("result", tagResult);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        content = ItemStack.of(pTag.getCompound("content"));
        progress = pTag.getInt("progress");
        result = ItemStack.of(pTag.getCompound("result"));
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.put("result", result.save(compoundtag));
        compoundtag.putInt("progress", progress);
        return compoundtag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
