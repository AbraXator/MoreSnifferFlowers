package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.components.PreviewMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockEntity extends ModBlockEntity {
    public BlockPos center;
    public boolean isPlaced; //True once the whole placing logic runs (to prevent updateShape from breaking it early)
    public PreviewMode previewMode = PreviewMode.PLACED;

    public MultiBlockEntity(BlockEntityType<?> pType, BlockPos pos, BlockState state) {
        super(pType, pos, state);
        this.center = this.getBlockPos();
        this.isPlaced = false;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("center", NbtUtils.writeBlockPos(this.center));
        tag.putBoolean("placed", this.isPlaced);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.center = NbtUtils.readBlockPos(tag, "center").orElseGet(this::getBlockPos);
        this.isPlaced = tag.getBoolean("placed");
    }

    public BlockPos getCenter() {
        return this.center;
    }

    public void setCenter(BlockPos pos) {
        this.center = pos;
    }

    public void setPlaced() {
        this.isPlaced = true;
    }

    public boolean canRender(){
        return center.equals(this.getBlockPos()) || !previewMode.equals(PreviewMode.PLACED);
    }

    public static void setPlaced(LevelReader level, BlockPos blockPos) {
        if(level.getBlockEntity(blockPos) instanceof MultiBlockEntity entity) entity.setPlaced();
    }

    public boolean isCenter(){
        return this.center.equals(getBlockPos());
    }


}
