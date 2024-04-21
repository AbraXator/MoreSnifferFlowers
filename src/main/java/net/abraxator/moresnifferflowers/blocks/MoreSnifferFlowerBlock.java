package net.abraxator.moresnifferflowers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class MoreSnifferFlowerBlock extends Block implements ModCropBlock {
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.of("color", DyeColor.class);
    public static final IntProperty STALK_TYPE = IntProperty.of("stalk_type", 1, 3);
    public static final IntProperty LEAVES_TYPE = IntProperty.of("leaves_type", 1, 3);

    public MoreSnifferFlowerBlock(Settings pProperties) {
        super(pProperties);
        this.setDefaultState(this.getDefaultState().with(STALK_TYPE, 1).with(LEAVES_TYPE, 1));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> pBuilder) {
        super.appendProperties(pBuilder);
        pBuilder.add(STALK_TYPE, LEAVES_TYPE);
    }

}
