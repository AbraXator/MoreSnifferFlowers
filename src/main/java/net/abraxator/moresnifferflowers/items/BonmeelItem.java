package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import oshi.util.tuples.Pair;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class BonmeelItem extends Item {
    public final Map<Block, Pair<Block, Pair<IntegerProperty, Integer>>> MAP = Map.of(
            Blocks.CARROTS, new Pair<>(ModBlocks.GIANT_CARROT.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
            Blocks.POTATOES, new Pair<>(ModBlocks.GIANT_POTATO.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
            Blocks.NETHER_WART, new Pair<>(ModBlocks.GIANT_NETHERWART.get(), new Pair<>(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE)),
            Blocks.BEETROOTS, new Pair<>(ModBlocks.GIANT_POTATO.get(), new Pair<>(BeetrootBlock.AGE, BeetrootBlock.MAX_AGE)),
            Blocks.WHEAT, new Pair<>(ModBlocks.GIANT_POTATO.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE))
    );

    public BonmeelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Block crop = clickedState.getBlock();
        Block giantVersion = MAP.get(crop).getA();
        BoundingBox boundingBox = new BoundingBox(
                clickedPos.getX() - 1,
                clickedPos.getY() - 0,
                clickedPos.getZ() - 1,
                clickedPos.getX() + 1,
                clickedPos.getY() + 2,
                clickedPos.getZ() + 1);
        Supplier<Stream<BlockPos>> stream = () -> BlockPos.betweenClosedStream(boundingBox);

        boolean flag = stream.get().allMatch(pos -> {
            BlockState blockState = level.getBlockState(pos);
            int cropY = clickedPos.getY();
            var PROPERTY = MAP.get(crop).getB().getA();
            int MAX_AGE = MAP.get(crop).getB().getB();

            return pos.getY() == cropY ? blockState.is(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM) && blockState.getValue(PROPERTY) == MAX_AGE : blockState.is(Blocks.AIR);
        });

        if (pContext.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (player instanceof ServerPlayer && flag) {
            stream.get().forEach(pos -> {
                level.destroyBlock(pos, false);
                level.setBlockAndUpdate(pos, giantVersion.defaultBlockState().setValue(GiantCropBlock.IS_CENTER, pos.equals(clickedPos)));
            });
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
