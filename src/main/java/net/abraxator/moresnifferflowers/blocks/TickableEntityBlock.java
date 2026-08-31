package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.IMSFBlockEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.nikdo53.tinymultiblocklib.block.BaseMultiblock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TickableEntityBlock extends EntityBlock {
    @Nullable
    default <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return canTick(level,state) ? createTickerHelper() : null;
    }

    private static <T extends BlockEntity> @NotNull BlockEntityTicker<T> createTickerHelper() {
        return (lvl, pos, s, blockEntity) -> {
            if (blockEntity instanceof IMSFBlockEntity imsfBlockEntity) {
                imsfBlockEntity.tick(lvl, pos, s);
                if (lvl instanceof ServerLevel serverLevel) {
                    imsfBlockEntity.serverTick(serverLevel, pos, s);
                } else {
                    imsfBlockEntity.clientTick(lvl, pos, s);
                }
            }
        };
    }

    default boolean canTick(Level level, BlockState state) {
        if (state.hasProperty(BaseMultiblock.CENTER)) {
            return state.getValue(BaseMultiblock.CENTER);
        }
        return true;
    }
}
