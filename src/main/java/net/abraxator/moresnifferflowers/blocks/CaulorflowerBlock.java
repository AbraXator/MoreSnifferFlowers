package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.CaulorflowerBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.Nullable;

public class CaulorflowerBlock extends GrowingPlantHeadBlock implements ModEntityBlock {
    public CaulorflowerBlock(Properties pProperties) {
        super(pProperties, Direction.UP, Shapes.block(), false, 0.1D);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        BlockPos blockpos = pPos.relative(this.growthDirection);
        if(pLevel.getBlockEntity(blockpos) instanceof CaulorflowerBlockEntity entity && pLevel.getBlockEntity(pPos) instanceof CaulorflowerBlockEntity entity1) {
            entity.color = entity1.color;
        }
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        super.performBonemeal(pLevel, pRandom, pPos, pState);
        BlockPos blockpos = pPos.relative(this.growthDirection);
        if(pLevel.getBlockEntity(blockpos) instanceof CaulorflowerBlockEntity entity && pLevel.getBlockEntity(pPos) instanceof CaulorflowerBlockEntity entity1) {
            entity.color = entity1.color;
        }
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        return NetherVines.getBlocksToGrowWhenBonemealed(pRandom);
    }

    @Override
    protected boolean canGrowInto(BlockState pState) {
        return pState.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return ModBlocks.CAULORFLOWER_PLANT.get();
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        dropResources();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CaulorflowerBlockEntity(pPos, pState);
    }
}
