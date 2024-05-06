package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.state.BlockState;

public class GiantCropBlockEntity extends ModBlockEntity {
    public BlockPos pos1;
    public BlockPos pos2;

    public GiantCropBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GIANT_CROP.get(), pPos, pBlockState);
        this.pos1 = this.getBlockPos();
        this.pos2 = this.getBlockPos();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("pos1", NbtUtils.writeBlockPos(this.pos1));
        pTag.put("pos2", NbtUtils.writeBlockPos(this.pos2));
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.pos1 = NbtUtils.readBlockPos(pTag.getCompound("pos1"));
        this.pos2 = NbtUtils.readBlockPos(pTag.getCompound("pos2"));
    }
}
