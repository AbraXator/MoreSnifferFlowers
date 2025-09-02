package net.abraxator.moresnifferflowers.blocks.corrupted;

import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CorruptedWartBlock extends BushBlock {
    public static final MapCodec<CorruptedWartBlock> CODEC = simpleCodec(CorruptedWartBlock::new);

    public CorruptedWartBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    private static final VoxelShape SHAPE = Block.box(4, 0,  4, 12, 5, 12);

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity pEntity) {
        explode(pos, level);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        return level.getBlockState(blockPos.below()).is(ModBlocks.CORRUPTED_GRASS_BLOCK.get());
    }

    @Override
    public BlockState updateShape(BlockState stateOriginal, Direction dir, BlockState stateNew, LevelAccessor level, BlockPos pCurrentPos, BlockPos pNewPos) {

        if (!canSurvive(stateOriginal, level, pCurrentPos)) {
            boolean drop = !level.getBlockState(pCurrentPos.below()).is(ModBlocks.CURED_GRASS_BLOCK.get());
            level.destroyBlock(pCurrentPos, drop);
        }
        return stateOriginal;
    }

    public void explode(BlockPos pos, Level level){
        level.destroyBlock(pos, true);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.CORRUPTED_GRASS_BLOCK.get());
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, 0, vec3.z);
    }


}
