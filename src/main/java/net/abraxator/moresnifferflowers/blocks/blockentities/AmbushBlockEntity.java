package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AmbushBlockEntity extends BlockEntity {
    public float growProgress;
    public boolean hasAmber;

    public AmbushBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AMBUSH.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AmbushBlockEntity entity) {
        if(canGrow(pState, entity.growProgress, entity.hasAmber)) {
            entity.growProgress += 0.001;
            entity.level.sendBlockUpdated(pPos, pState, pState, Block.UPDATE_CLIENTS);
            if(entity.growProgress >= 1) {
                entity.onGrow(pPos, pState, pLevel);
            }
        }
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.hasAmber = pTag.getBoolean("hasAmber");
        this.growProgress = pTag.getFloat("progress");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("hasAmber", this.hasAmber);
        pTag.putFloat("progress", this.growProgress);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    public void onGrow(BlockPos blockPos, BlockState state, Level level) {
        this.hasAmber = true;
        //level.setBlock(blockPos, ModBlocks.AMBER.get().defaultBlockState(), 3);
        this.level.sendBlockUpdated(blockPos, state, state, Block.UPDATE_CLIENTS);
    }

    public void reset(BlockPos blockPos, BlockState state, Level level) {
        this.growProgress = 0;
        this.hasAmber = false;
        //level.setBlock(blockPos, state.setValue(AmbushBlock.AGE, 7), 3);
    }

    public static boolean canGrow(BlockState state, float growProgress, boolean hasAmber) {
        return state.getValue(AmbushBlock.AGE).equals(7) && !(growProgress >= 1) && !hasAmber;
    }
}