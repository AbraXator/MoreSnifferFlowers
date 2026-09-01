package net.abraxator.moresnifferflowers.blocks.corrupted;

import net.abraxator.moresnifferflowers.init.config.MSFServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class CorruptedSaplingBlock extends SaplingBlock {
    protected final TreeGrower treeGrower;

    public CorruptedSaplingBlock(TreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
        this.treeGrower = treeGrower;
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        advanceTree(level, pos, state, random, false);
    }

    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random, boolean wasBoneMealed) {
        if (state.getValue(STAGE) == 0) {
            level.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            if (wasBoneMealed || !MSFServerConfig.CORRUPTED_TREE_BONE_MEAL.get())
                this.treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
        }
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.advanceTree(level, pos, state, random, true);
    }
}
