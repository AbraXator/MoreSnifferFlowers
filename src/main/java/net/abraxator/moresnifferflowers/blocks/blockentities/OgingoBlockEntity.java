package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OgingoBlockEntity extends BlockEntity {
    public boolean hasColor;
    public int color;

    public OgingoBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, boolean hasColor) {
        super(pType, pPos, pBlockState);
        this.hasColor = hasColor;
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
