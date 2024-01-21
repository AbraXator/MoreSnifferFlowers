package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class GiantCropBlockEntity extends ModBlockEntity {
    public UUID uuid;
    
    public GiantCropBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GIANT_CROP.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        uuid = pTag.getUUID("uuid");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putUUID("uuid", uuid);
    }
}
