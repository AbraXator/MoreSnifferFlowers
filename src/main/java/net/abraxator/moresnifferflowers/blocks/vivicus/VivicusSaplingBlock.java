package net.abraxator.moresnifferflowers.blocks.vivicus;

import net.abraxator.moresnifferflowers.blocks.ColorableVivicusBlock;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.init.ModTreeGrowers;
import net.abraxator.moresnifferflowers.worldgen.configurations.tree.vivicus.VivicusTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class VivicusSaplingBlock extends SaplingBlock implements ColorableVivicusBlock {
    public final VivicusTreeGrower vivicusTreeGrower = ModTreeGrowers.VIVICUS_TREE;
    
    public VivicusSaplingBlock(Properties p_55979_) {
        super(TreeGrower.OAK, p_55979_);
        defaultBlockState().setValue(ModStateProperties.COLOR, DyeColor.WHITE).setValue(ModStateProperties.VIVICUS_TYPE, BoblingEntity.Type.CORRUPTED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ModStateProperties.COLOR);
        pBuilder.add(ModStateProperties.VIVICUS_TYPE);
    }

    @Override
    public void advanceTree(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        if (pState.getValue(STAGE) == 0) {
            pLevel.setBlock(pPos, pState.cycle(STAGE), 4);
        } else {
            this.vivicusTreeGrower.growTree(pLevel, pLevel.getChunkSource().getGenerator(), pPos, pState, pRandom);
        }
    }
}
