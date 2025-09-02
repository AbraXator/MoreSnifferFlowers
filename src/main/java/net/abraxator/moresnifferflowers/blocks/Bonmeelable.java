package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface Bonmeelable {
    void performBonmeel(BlockPos blockPos, BlockState blockState, Level level, @Nullable Player player);

    boolean canBonmeel(BlockPos blockPos, BlockState blockState, Level level);
}

