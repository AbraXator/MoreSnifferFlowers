package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.blocks.DyespriaPlantBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.*;

public class ModStateProperties {
    public static final IntegerProperty AGE_3 = IntegerProperty.create("age", 0, 3);
    public static final IntegerProperty AGE_8 = IntegerProperty.create("age", 0, 8);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty FLIPPED = BooleanProperty.create("flipped");
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
    public static final BooleanProperty SHEARED = BooleanProperty.create("sheared");
}
