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
    public boolean hasColor;
    public int color = -1;

    public CaulorflowerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CAULORFLOWER.get(), pPos, pBlockState);
    }

    public void setColor(Player player, ItemStack painter, BlockPos blockPos, BlockState blockState) {
        FlowerPainter.getColor(painter).ifPresentOrElse(integer -> {
            color = integer;
        }, () -> {
            color = -1;
        });
        level.sendBlockUpdated(blockPos, blockState, blockState, 3);
    }

    public boolean hasColor() {
        return hasColor;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("hasColor", hasColor);
        pTag.putInt("color", color);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        hasColor = pTag.getBoolean("hasColor");
        color = pTag.getInt("color");
    }
}
