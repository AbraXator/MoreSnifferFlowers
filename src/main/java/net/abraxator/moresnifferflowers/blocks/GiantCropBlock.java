package net.abraxator.moresnifferflowers.blocks;

import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.blocks.blockentities.GiantCropBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class GiantCropBlock extends PlantBlock implements BlockEntityProvider {
    public static final BooleanProperty IS_CENTER = BooleanProperty.of("center");
    public static final MapCodec<GiantCropBlock> CODEC = createCodec(GiantCropBlock::new);

    public GiantCropBlock(Settings pProperties) {
        super(pProperties);
        setDefaultState(getDefaultState().with(IS_CENTER, false));
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    public VoxelShape getSidesShape(BlockState pState, BlockView pReader, BlockPos pPos) {
        return super.getSidesShape(pState, pReader, pPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> pBuilder) {
        super.appendProperties(pBuilder);
        pBuilder.add(IS_CENTER);
    }

    public static boolean isCenter(BlockState blockState) {
        return blockState.get(IS_CENTER);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pPos, BlockState pState) {
        return new GiantCropBlockEntity(pPos, pState);
    }
}
