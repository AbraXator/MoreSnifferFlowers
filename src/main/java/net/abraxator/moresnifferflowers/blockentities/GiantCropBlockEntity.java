package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GiantCropBlockEntity extends MultiBlockEntity {
    public boolean canGrow = false;
    public double growProgress = 0;
    public int state = 0; //0 NONE; 1 ANIMATION; 2 SACK;
    public float staticGameTime;

    public GiantCropBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GIANT_CROP.get(), pos, state);
    }

    @Override
    public void tick(Level level) {
        if(canGrow) {
            if(staticGameTime==0){
                staticGameTime = level.getGameTime();
               // System.out.println("staticgametime="+staticGameTime);
            }
            growProgress += 0.10;
            this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            if(growProgress >= 1) {
                canGrow = false;
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("canGrow", canGrow);
        tag.putDouble("growProgress", growProgress);
        tag.putFloat("staticGameTime", staticGameTime);
        tag.putInt("state", this.state);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.canGrow = tag.getBoolean("canGrow");
        this.growProgress = tag.getDouble("growProgress");
        this.staticGameTime = tag.getFloat("staticGameTime");
        this.state = tag.getInt("state");
    }
}
