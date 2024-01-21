package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
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

public class AmbushBlockEntity extends GrowingCropBlockEntity {
    public AmbushBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AMBUSH.get(), pPos, pBlockState, 0.001f);
    }

    @Override
    public boolean canGrow(float growProgress, boolean hasGrown) {
        return this.getBlockState().getValue(AmbushBlock.AGE).equals(7) && super.canGrow(growProgress, hasGrown);   
    }
}