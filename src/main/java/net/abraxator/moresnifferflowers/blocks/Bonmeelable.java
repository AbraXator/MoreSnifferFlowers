package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface Bonmeelable {
    void performBonmeel(BlockPos blockPos, BlockState blockState, Level level, @Nullable Player player);

    boolean canBonmeel(BlockPos blockPos, BlockState blockState, Level level, @Nullable Player player);
}

