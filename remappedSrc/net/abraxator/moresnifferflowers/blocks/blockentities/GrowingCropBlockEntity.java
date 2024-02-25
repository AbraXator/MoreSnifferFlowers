package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.pattern.ThreadIdPatternConverter;
import org.jetbrains.annotations.Nullable;

public abstract class GrowingCropBlockEntity extends ModBlockEntity {
    public float growProgress;
    public boolean hasGrown;
    public final float growRate;
    
    public GrowingCropBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, float growRate) {
        super(pType, pPos, pBlockState);
        this.growRate = growRate;
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    public void tick() {
        if(canGrow(this.growProgress, this.hasGrown)) {
            this.growProgress += growRate;
            this.world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
            if(this.growProgress >= 1) {
                this.onGrow(getPos(), getCachedState(), getWorld());
            }
        }
    }

    public boolean canGrow(float growProgress, boolean hasGrown) {
        return !(growProgress >= 1) && !hasGrown;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound tag = super.toInitialChunkDataNbt();
        writeNbt(tag);
        return tag;
    }

    public void onGrow(BlockPos blockPos, BlockState state, World level) {
        this.hasGrown = true;;
        this.world.updateListeners(blockPos, state, state, Block.NOTIFY_LISTENERS);
    }

    public void reset(BlockPos blockPos, BlockState state, World level) {
        this.growProgress = 0;
        this.hasGrown = false;
        //level.setBlock(blockPos, state.setValue(AmbushBlock.AGE, 7), 3);
    }
    
    @Override
    public void readNbt(NbtCompound pTag) {
        super.readNbt(pTag);
        this.hasGrown = pTag.getBoolean("hasGrown");
        this.growProgress = pTag.getFloat("progress");
    }

    @Override
    protected void writeNbt(NbtCompound pTag) {
        super.writeNbt(pTag);
        pTag.putBoolean("hasGrown", this.hasGrown);
        pTag.putFloat("progress", this.growProgress);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
