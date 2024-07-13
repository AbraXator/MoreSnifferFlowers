package net.abraxator.moresnifferflowers.blocks.vivicus;

import net.abraxator.moresnifferflowers.blocks.ColorableVivicusBlock;
import net.abraxator.moresnifferflowers.blocks.ModCropBlock;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class VivicusSproutingBlock extends VivicusLeavesBlock implements ModCropBlock, ColorableVivicusBlock {
    public VivicusSproutingBlock(Properties p_54422_) {
        super(p_54422_);
        defaultBlockState().setValue(ModStateProperties.COLOR, DyeColor.WHITE).setValue(ModStateProperties.VIVICUS_TYPE, BoblingEntity.Type.CORRUPTED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ModStateProperties.AGE_2);
        pBuilder.add(ModStateProperties.COLOR);
        pBuilder.add(ModStateProperties.VIVICUS_TYPE);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_2;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState) {
        return super.isRandomlyTicking(pState);
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if(pRandom.nextDouble() <= 0.3 && !isMaxAge(pState)) {
            pLevel.setBlock(pPos, pState.setValue(getAgeProperty(), (pState.getValue(getAgeProperty()) + 1)), 2);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return !isMaxAge(pState);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        makeGrowOnBonemeal(pLevel, pPos, pState);
    }

    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return null;
    }
}
