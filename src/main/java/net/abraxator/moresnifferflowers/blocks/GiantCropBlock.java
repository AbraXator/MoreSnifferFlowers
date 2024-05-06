package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GiantCropBlock extends Block implements EntityBlock {
    public static final EnumProperty<ModelPos> MODEL_POSITION = EnumProperty.create("model_pos", ModelPos.class);

    public GiantCropBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(MODEL_POSITION, ModelPos.NONE));
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).is(Blocks.FARMLAND);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return super.getBlockSupportShape(pState, pReader, pPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(MODEL_POSITION);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GiantCropBlockEntity(pPos, pState);
    }

    public static enum ModelPos implements StringRepresentable {
        X("x", -0.5006, 0.5006, 0.5006),
        IX("ix", 1.5003, 0.5003, 0.5003),
        Z("z", 0.4991, 0.4991, -0.4991),
        IZ("iz", 0.4994, 0.4994, 1.4994),
        Y("y", 0.4997, -0.4997, 0.4997),
        IY("iy", 0.5, 1.5, 0.5),
        NONE("none");

        public final String name;
        public final double x;
        public final double y;
        public final double z;

        ModelPos(String name, double x, double y, double z) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        ModelPos(String name) {
            this(name, 0, 0, 0);
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
