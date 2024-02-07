package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.GiantCropBlockEntity;
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

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JarOfBonmeelItem extends Item {
    public final Map<Block, Pair<Block, Pair<IntegerProperty, Integer>>> MAP = Map.of(
            Blocks.CARROTS, new Pair<>(ModBlocks.GIANT_CARROT.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
            Blocks.POTATOES, new Pair<>(ModBlocks.GIANT_POTATO.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE)),
            Blocks.NETHER_WART, new Pair<>(ModBlocks.GIANT_NETHERWART.get(), new Pair<>(NetherWartBlock.AGE, NetherWartBlock.MAX_AGE)),
            Blocks.BEETROOTS, new Pair<>(ModBlocks.GIANT_BEETROOT.get(), new Pair<>(BeetrootBlock.AGE, BeetrootBlock.MAX_AGE)),
            Blocks.WHEAT, new Pair<>(ModBlocks.GIANT_WHEAT.get(), new Pair<>(CropBlock.AGE, CropBlock.MAX_AGE))
    );

    public JarOfBonmeelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos clickedPos = pContext.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        if(!clickedState.is(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM)) return InteractionResult.PASS;
        Block crop = clickedState.getBlock();
        Block giantVersion = MAP.get(crop).getA();
        Iterable<BlockPos> blockPosList = BlockPos.betweenClosed(
                clickedPos.getX() - 1,
                clickedPos.getY() - 0,
                clickedPos.getZ() - 1,
                clickedPos.getX() + 1,
                clickedPos.getY() + 2,
                clickedPos.getZ() + 1
        );
        boolean flag = StreamSupport.stream(blockPosList.spliterator(), false).allMatch(pos -> {
            BlockState blockState = level.getBlockState(pos);
            int cropY = clickedPos.getY();
            var PROPERTY = MAP.get(crop).getB().getA();
            int MAX_AGE = MAP.get(crop).getB().getB();

            return pos.getY() == cropY ? blockState.is(ModTags.ModBlockTags.CROPS_FERTIABLE_BY_FBM) && blockState.getValue(PROPERTY) == MAX_AGE : blockState.is(Blocks.AIR);
        });

        if (pContext.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if (flag && !level.isClientSide()) {
            return placeLogic(blockPosList, level, giantVersion, clickedPos);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult placeLogic(Iterable<BlockPos> blockPosList, Level level, Block giantVersion, BlockPos clickedPos) {
        int i = 0;
        List<BlockPos> list = new ArrayList<>();
        blockPosList.forEach(list::add);

        blockPosList.forEach(pos -> {
            pos = pos.immutable();
            level.destroyBlock(pos, false);
            level.setBlockAndUpdate(pos, giantVersion.defaultBlockState().setValue(GiantCropBlock.IS_CENTER, pos.equals(clickedPos)));
            if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity) {
                entity.pos1 = clickedPos.mutable().move(1, 2, 1);
                entity.pos2 = clickedPos.mutable().move(-1, 0, -1);
            }
        });

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
