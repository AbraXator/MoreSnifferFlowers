package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;

import java.util.Iterator;
import java.util.List;
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
            BoundingBox boundingBox = new BoundingBox(
                    clickedPos.getX() - 1,
                    clickedPos.getY() - 0,
                    clickedPos.getZ() - 1,
                    clickedPos.getX() + 1,
                    clickedPos.getY() + 2,
                    clickedPos.getZ() + 1);
            List<BlockPos> list = BlockPos.betweenClosedStream(boundingBox).toList();
            boolean flag = list.stream().allMatch(pos -> {
                BlockState blockState = level.getBlockState(pos);
                int cropY = clickedPos.getY();
                return pos.getY() == cropY ? blockState.is(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM) : blockState.is(Blocks.AIR);
            });

            if (pContext.getHand() != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }

            if (player instanceof ServerPlayer && flag) {
                for (BlockPos blockPos : list) {
                    level.destroyBlock(blockPos, false);
                    level.setBlockAndUpdate(blockPos, ModBlocks.GIANT_CARROT.get().defaultBlockState().setValue(GiantCropBlock.IS_CENTER, blockPos == clickedPos));
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }

            return InteractionResult.PASS;
    }
}
