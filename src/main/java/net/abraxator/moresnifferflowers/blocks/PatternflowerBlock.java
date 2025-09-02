package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.components.BlockPattern;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.abraxator.moresnifferflowers.init.ModStateProperties.*;

public class PatternflowerBlock extends CaulorflowerBlock implements BonemealableBlock, ModCropBlock {

    public PatternflowerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(FLIPPED, true)
                .setValue(getAgeProperty(), 0)
                .setValue(ModStateProperties.BLOCK_PATTERN, BlockPattern.EMPTY)
                .setValue(EMPTY, true)
                .setValue(SHEARED, false));

    }

    @Override
    public boolean canBeColored(BlockState blockState, Dye dye) {
        return false;
    }

    @Override
    public boolean isCorrupted() {
        return true;
    }

    @Override
    public void popResource(Level level, BlockPos pos, BlockState state) {
        popResource(level, pos, BlockPattern.fromState(state).getItem().getDefaultInstance());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHEARED, FLIPPED, getAgeProperty(), ModStateProperties.BLOCK_PATTERN, EMPTY);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction pFacing, BlockState pFacingState, LevelAccessor level, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if(canSurvive(state, level, pCurrentPos)) {
            return state.setValue(FLIPPED, pCurrentPos.getY() % 2 == 0).setValue(EMPTY, BlockPattern.isEmpty(state));
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return BlockPattern.fromState(state).isBanner() ? random.nextFloat() < 0.2 : random.nextFloat() < 0.50;
    }

    @Override
    public boolean harvestable(BlockState blockState) {
        return isMaxAge(blockState) && !BlockPattern.isEmpty(blockState);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(newState.is(this)) {
            return;
        }

        var stateBelow = level.getBlockState(pos.below());
        if(!stateBelow.is(this) && !stateBelow.is(Blocks.AIR)) {
            popResource(level, pos, new ItemStack(ModItems.PATTERNFLOWER_SEEDS.get()));
        }

        if(!BlockPattern.isEmpty(state) && isMaxAge(state)) {
            popResource(level, pos, BlockPattern.fromState(state).getItem().getDefaultInstance());
        }
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE_2;
    }
}
