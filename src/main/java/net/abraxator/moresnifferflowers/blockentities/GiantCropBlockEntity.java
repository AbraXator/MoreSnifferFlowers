package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GiantCropBlockEntity extends ModBlockEntity {
    public BlockPos pos1;
    public BlockPos pos2;
    public boolean canGrow = false;
    public double growProgress = 0;
    public int state = 0; //0 NONE; 1 ANIMATION; 2 SACK;

    public GiantCropBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GIANT_CROP.get(), pPos, pBlockState);
        this.pos1 = this.getBlockPos();
        this.pos2 = this.getBlockPos();
    }

    @Override
    public void tick(Level level) {
        if(canGrow) {
            growProgress += 0.25;
            this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            if(growProgress >= 1) {
                canGrow = false;
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        var tag = new CompoundTag();
        tag.putDouble("growProgress", growProgress);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putBoolean("canGrow", canGrow);
        pTag.putDouble("growProgress", growProgress);
        pTag.put("pos1", NbtUtils.writeBlockPos(this.pos1));
        pTag.put("pos2", NbtUtils.writeBlockPos(this.pos2));
        pTag.putInt("state", this.state);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.canGrow = pTag.getBoolean("canGrow");
        this.growProgress = pTag.getDouble("growProgress");
        this.pos1 = NbtUtils.readBlockPos(pTag, "pos1").orElseGet(this::getBlockPos);
        this.pos2 = NbtUtils.readBlockPos(pTag, "pos2").orElseGet(this::getBlockPos);
        this.state = pTag.getInt("state");
    }
}
