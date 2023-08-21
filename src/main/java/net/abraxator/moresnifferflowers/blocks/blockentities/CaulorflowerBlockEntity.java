package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.items.FlowerPainter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CaulorflowerBlockEntity extends BlockEntity {
    public ItemStack dye;

    public CaulorflowerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CAULORFLOWER.get(), pPos, pBlockState);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("dye", dye != null ? dye.serializeNBT() : ItemStack.EMPTY.serializeNBT());
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        dye = ItemStack.of(pTag.getCompound("dye"));
    }
}
