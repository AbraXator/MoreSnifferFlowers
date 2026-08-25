package net.abraxator.moresnifferflowers.blockentities;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public interface IModBlockEntity {
    default void tick(Level level) {};

    default void clientTick(Level level) {};

}
