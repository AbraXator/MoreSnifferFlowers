package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class MoreSnifferFlowerBlock extends Block implements ModCropBlock {
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
    public static final IntegerProperty STALK_TYPE = IntegerProperty.create("stalk_type", 1, 3);
    public static final IntegerProperty LEAVES_TYPE = IntegerProperty.create("leaves_type", 1, 3);

    public MoreSnifferFlowerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(STALK_TYPE, 1).setValue(LEAVES_TYPE, 1));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(STALK_TYPE, LEAVES_TYPE);
    }
    @Override
    public BlockState getPlant(BlockGetter level, BlockPos pos) {
        return null;
    }
}
