package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;

public class GiantCropBlockEntity extends ModBlockEntity {
    public BlockPos pos1;
    public BlockPos pos2;

    public GiantCropBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GIANT_CROP.get(), pPos, pBlockState);
        this.pos1 = this.getPos();
        this.pos2 = this.getPos();
    }

    @Override
    protected void writeNbt(NbtCompound pTag) {
        super.writeNbt(pTag);
        pTag.put("pos1", NbtHelper.fromBlockPos(this.pos1));
        pTag.put("pos2", NbtHelper.fromBlockPos(this.pos2));
    }

    @Override
    public void readNbt(NbtCompound pTag) {
        super.readNbt(pTag);
        this.pos1 = NbtHelper.toBlockPos(pTag.getCompound("pos1"));
        this.pos2 = NbtHelper.toBlockPos(pTag.getCompound("pos2"));
    }
}
