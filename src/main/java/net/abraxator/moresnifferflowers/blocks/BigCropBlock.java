package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BigCropBlock extends Block {
    public static final BooleanProperty IS_CENTER = BooleanProperty.create("center");

    public BigCropBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(IS_CENTER, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(IS_CENTER);
    }
}
