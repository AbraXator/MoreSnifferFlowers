package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.BigCropBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.stream.Stream;

public class FecundBoneMeal extends Item {
    public FecundBoneMeal(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(clickedPos);
        AABB boundingBox = new AABB(clickedPos.mutable().north().west().immutable(), clickedPos.mutable().south().east().immutable());
        Stream<BlockPos> iterator = BlockPos.betweenClosedStream(boundingBox);

        if (pContext.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (player instanceof ServerPlayer) {
            for (BlockPos pos : iterator.toList())
                if (level.getBlockState(pos).is(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM)) {
                    level.setBlock(pos, ModBlocks.BIG_CROP.get().defaultBlockState().setValue(BigCropBlock.IS_CENTER, pos.equals(clickedPos)), 1);
                } else {
                    return InteractionResult.PASS;
                }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
