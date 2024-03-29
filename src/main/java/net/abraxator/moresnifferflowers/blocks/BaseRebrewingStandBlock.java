package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class BaseRebrewingStandBlock extends Block {
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2};
    
    public BaseRebrewingStandBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if(pState.is(ModBlocks.REBREWING_STAND_BOTTOM.get())) super.playerWillDestroy(pLevel, pPos.above(), pState, pPlayer);
        if(pState.is(ModBlocks.REBREWING_STAND_TOP.get())) super.playerWillDestroy(pLevel, pPos.below(), pState, pPlayer);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockPos = pContext.getClickedPos();
        BlockPos blockPos1 = blockPos.below();
        Level level = pContext.getLevel();

        return level.getBlockState(blockPos1).canBeReplaced(pContext) ? this.defaultBlockState() : null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(!pLevel.isClientSide) {
            BlockPos blockPos = pPos.above();
            pLevel.setBlockAndUpdate(blockPos, ModBlocks.REBREWING_STAND_TOP.get().defaultBlockState());
            pState.updateNeighbourShapes(pLevel, blockPos, 3);
        }
    }
}
