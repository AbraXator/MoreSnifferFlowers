package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GiantCropItem extends BlockItem {
    public GiantCropItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        var level = pContext.getLevel();
        BlockPos.withinManhattanStream(pContext.getClickedPos(), 1, 2, 1).forEach(pos -> {
            level.setBlockAndUpdate(pos, this.getBlock().defaultBlockState().setValue(GiantCropBlock.MODEL_POSITION, JarOfBonmeelItem.evaulateModelPos(pos, pContext.getClickedPos().above())));
            if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity) {
                entity.pos1 = pContext.getClickedPos().above().mutable().move(1, 2, 1);
                entity.pos2 = pContext.getClickedPos().above().mutable().move(-1, 0, -1);
            }
        });
        
        return true;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) { 
        var pos = pContext.getClickedPos();
        var level = pContext.getLevel();
        return BlockPos.withinManhattanStream(pos.above(), 1, 2, 1)
                .allMatch(blockPos -> level.getBlockState(blockPos).isEmpty());
    }
}
