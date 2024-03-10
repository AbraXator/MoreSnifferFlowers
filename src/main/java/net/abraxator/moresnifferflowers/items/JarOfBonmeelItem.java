package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.*;
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
            return placeLogic(blockPosList, level, giantVersion, clickedPos, player);
        }

        return InteractionResult.PASS;
    }

    private InteractionResult placeLogic(Iterable<BlockPos> blockPosList, Level level, Block giantVersion, BlockPos clickedPos, Player player) {
        blockPosList.forEach(pos -> {
            pos = pos.immutable();
            level.destroyBlock(pos, false);
            level.setBlockAndUpdate(pos, giantVersion.defaultBlockState().setValue(GiantCropBlock.MODEL_POSITION, evaulateModelPos(pos, clickedPos)));

            for(int j = 0; j <= 3; j++) {
                ((ServerLevel) level).sendParticles(new DustParticleOptions(Vec3.fromRGB24(11162034).toVector3f(), 1.0F), clickedPos.getX() + level.random.nextDouble(), clickedPos.above().getY() + level.random.nextDouble(), clickedPos.getZ() + level.random.nextDouble()   , 1, 0, 0, 0, 0.3D);
            }

            if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity) {
                entity.pos1 = clickedPos.mutable().move(1, 2, 1);
                entity.pos2 = clickedPos.mutable().move(-1, 0, -1);
            }
        });

        player.getMainHandItem().shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private GiantCropBlock.ModelPos evaulateModelPos(BlockPos pos, BlockPos posToCompare) {
        var value = GiantCropBlock.ModelPos.NONE;

        if(pos.equals(posToCompare.mutable().move(1, 1, 0))) {
            value = GiantCropBlock.ModelPos.X;
        }
        if(pos.equals(posToCompare.mutable().move(-1, 1, 0))) {
            value = GiantCropBlock.ModelPos.IX;
        }
        if(pos.equals(posToCompare.mutable().move(0, 1, 1))) {
            value = GiantCropBlock.ModelPos.Z;
        }
        if(pos.equals(posToCompare.mutable().move(0, 1, -1))) {
            value = GiantCropBlock.ModelPos.IZ;
        }
        if(pos.equals(posToCompare.mutable().move(0, 2, 0))) {
            value = GiantCropBlock.ModelPos.Y;
        }
        if(pos.equals(posToCompare.mutable().move(0, 0, 0))) {
            value = GiantCropBlock.ModelPos.IY;
        }

        return value;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatableWithFallback("tooltip.jar_of_bonmeel.usage", "Can be applied to a 3x3 grid of the following crops: carrot, potato, wheat, beetroot and nether wart").withStyle(ChatFormatting.GOLD));
    }
}