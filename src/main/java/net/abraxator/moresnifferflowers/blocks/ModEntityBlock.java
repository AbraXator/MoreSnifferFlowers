package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.IModBlockEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import org.jetbrains.annotations.Nullable;

public interface ModEntityBlock extends EntityBlock {
    @Nullable
     default <T extends BlockEntity> BlockEntityTicker<T> tickerHelper(Level level) {
        return (pLevel1, pos, pState1, blockEntity) -> {
            if(level.isClientSide) {
                ((IModBlockEntity) blockEntity).clientTick(level);
            } else {
                ((IModBlockEntity) blockEntity).tick(level);
            }
        };
    }
}
