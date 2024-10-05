package net.abraxator.moresnifferflowers.blocks;

import com.google.common.collect.Maps;
import net.abraxator.moresnifferflowers.components.Colorable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.h;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static net.abraxator.moresnifferflowers.init.ModStateProperties.*;

public class CaulorflowerBlock extends Block implements BonemealableBlock, ModCropBlock, Colorable {
    public CaulorflowerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(FLIPPED, true)
                .setValue(AGE_1, 0)
                .setValue(COLOR, DyeColor.WHITE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING, FLIPPED, COLOR, AGE_1);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if(canSurvive(pState, pLevel, pCurrentPos)) {
            return pState.setValue(FLIPPED, pCurrentPos.getY() % 2 == 0);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = pContext.getLevel().getBlockState(pContext.getClickedPos().below());
        if(state.is(this)) {
            return state.setValue(FLIPPED, pContext.getClickedPos().getY() % 2 == 0);
        }
        return super.getStateForPlacement(pContext).setValue(FLIPPED, pContext.getClickedPos().getY() % 2 == 0).setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockPos = pPos.below();
        BlockState blockState = pLevel.getBlockState(blockPos);
        BlockPos wallPos = pPos.relative(pState.getValue(FACING).getOpposite());
        BlockState wallState = pLevel.getBlockState(wallPos);
        return pLevel.getBlockState(blockPos).is(this) || blockState.isFaceSturdy(pLevel, blockPos, Direction.UP) || wallState.isFaceSturdy(pLevel, wallPos, pState.getValue(FACING));
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        if(pRandom.nextFloat() < 0.15) {
            grow(pLevel, pPos, false);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        Optional<BlockPos> highestPos = highestPos(pLevel, pPos, true);
        return highestPos.isPresent() && (pLevel.getBlockState(highestPos.get().above()).is(Blocks.AIR) || !isMaxAge(pLevel.getBlockState(highestPos.get())));
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        grow(pLevel, pPos, true);
    }

    protected void grow(ServerLevel pLevel, BlockPos originalPos, boolean bonemeal) {
        highestPos(pLevel, originalPos, bonemeal).ifPresent(highestPos -> {
            var posBelow = highestPos.below();
            var stateBelow = pLevel.getBlockState(posBelow);
            if(isMaxAge(stateBelow)) {
                pLevel.setBlockAndUpdate(highestPos, this.defaultBlockState()
                        .setValue(FLIPPED, highestPos.getY() % 2 == 0)
                        .setValue(FACING, stateBelow.getValue(FACING))
                        .setValue(COLOR, stateBelow.getValue(COLOR)));
            } else {
                makeGrowOnBonemeal(pLevel, posBelow, stateBelow);   
            }
        });
    }

    @Override
    public void makeGrowOnBonemeal(Level level, BlockPos blockPos, BlockState blockState) {
        if(level.random.nextDouble() > 0.3D) {
            ModCropBlock.super.makeGrowOnBonemeal(level, blockPos, blockState);
            return;
        } else {
            ModCropBlock.super.makeGrowOnBonemeal(level, blockPos, blockState);
            ModCropBlock.super.makeGrowOnBonemeal(level, blockPos, blockState);
        }
    }

    private Optional<BlockPos> highestPos(BlockGetter level, BlockPos originalPos, boolean bonemeal) {
        var lowestPos = getLowestPos(level, originalPos);
        if(lowestPos.isEmpty()) { return Optional.empty(); }
        var highestPos = getLastConnectedBlock(level, lowestPos.get(), Direction.UP);
        return highestPos.filter(blockPos1 -> bonemeal || !((lowestPos.get().getY() + 5) <= blockPos1.getY())).map(BlockPos::above);
    }

    private Optional<BlockPos> getLowestPos(BlockGetter level, BlockPos originalPos) {
        var posDown = getLastConnectedBlock(level, originalPos, Direction.DOWN).map(BlockPos::above);
        return posDown.filter(blockPos -> level.getBlockState(blockPos).is(this));
    }

    public Optional<BlockPos> getLastConnectedBlock(BlockGetter pGetter, BlockPos pPos, Direction pDirection) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();

        while (pGetter.getBlockState(blockpos$mutableblockpos).is(this)){
            blockpos$mutableblockpos.move(pDirection);
        }

        return pDirection == Direction.DOWN ? Optional.of(blockpos$mutableblockpos) : (pGetter.getBlockState(blockpos$mutableblockpos).is(Blocks.AIR) ? Optional.of(blockpos$mutableblockpos.below()) : Optional.empty());
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE_1;
    }

    @Override
    public Map<DyeColor, Integer> colorValues() {
        return Util.make(Maps.newLinkedHashMap(), dyeColorHexFormatMap -> {
            dyeColorHexFormatMap.put(DyeColor.WHITE, 0xFFFFFFFF);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_GRAY, 0xFF9d979b);
            dyeColorHexFormatMap.put(DyeColor.GRAY, 0xFF474f52);
            dyeColorHexFormatMap.put(DyeColor.BLACK, 0xFF26262e);
            dyeColorHexFormatMap.put(DyeColor.BROWN, 0xFF835432);
            dyeColorHexFormatMap.put(DyeColor.RED, 0xFFd5544e);
            dyeColorHexFormatMap.put(DyeColor.ORANGE, 0xFFf89635);
            dyeColorHexFormatMap.put(DyeColor.YELLOW, 0xFFffee53);
            dyeColorHexFormatMap.put(DyeColor.LIME, 0xFF80c71f);
            dyeColorHexFormatMap.put(DyeColor.GREEN, 0xFF5e7c16);
            dyeColorHexFormatMap.put(DyeColor.CYAN, 0xFF00AACC);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_BLUE, 0xFF70d9e4);
            dyeColorHexFormatMap.put(DyeColor.BLUE, 0xFF4753ac);
            dyeColorHexFormatMap.put(DyeColor.PURPLE, 0xFFb15fc2);
            dyeColorHexFormatMap.put(DyeColor.MAGENTA, 0xFFd276b9);
            dyeColorHexFormatMap.put(DyeColor.PINK, 0xFFf8b0c4);
        });
    }

    @Override
    public void onAddDye(@Nullable ItemStack destinationStack, ItemStack dye, int amount) {
        
    }
}
