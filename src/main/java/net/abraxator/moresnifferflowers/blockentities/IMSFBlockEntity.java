package net.abraxator.moresnifferflowers.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface IMSFBlockEntity {
    default void serverTick(ServerLevel level, BlockPos pos, BlockState state) {};

    default void tick(Level level, BlockPos pos, BlockState state) {};

    default void clientTick(Level level, BlockPos pos, BlockState state) {};

    default Level level() {
        Level level = self().getLevel();
        if (level == null)
            throw new IllegalStateException("BlockEntity " + self() + " is not in a world");
        return level;
    }

    private BlockEntity self(){
        return (BlockEntity) this;
    }
}
