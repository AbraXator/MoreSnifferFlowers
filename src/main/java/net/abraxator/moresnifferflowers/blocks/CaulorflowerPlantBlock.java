package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;

public class CaulorflowerPlantBlock extends GrowingPlantBodyBlock implements Colorable{
    public CaulorflowerPlantBlock(Properties pProperties) {
        super(pProperties, Direction.UP, Shapes.block(), false);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return ((GrowingPlantHeadBlock) ModBlocks.CAULORFLOWER.get());
    }
}
