package net.abraxator.moresnifferflowers.blocks.vivicus;

import com.google.common.collect.Maps;
import net.abraxator.moresnifferflowers.blocks.ColorableVivicusBlock;
import net.abraxator.moresnifferflowers.blocks.ModCropBlock;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class VivicusSproutingBlock extends Block implements ModCropBlock, ColorableVivicusBlock {
    public VivicusSproutingBlock(Properties p_54422_) {
        super(p_54422_);
        this.registerDefaultState(defaultBlockState().setValue(ModStateProperties.VIVICUS_CURED, false).setValue(ModStateProperties.COLOR, DyeColor.WHITE));
    }
    private static final VoxelShape SHAPE0 = Block.box(3, 6,  3, 13, 16, 13);
    private static final VoxelShape SHAPE1 = Block.box(3, 2,  3, 13, 16, 13);
    private static final VoxelShape SHAPE2 = Block.box(3, 0,  3, 13, 16, 13);


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ModStateProperties.AGE_3);
        builder.add(ModStateProperties.VIVICUS_CURED);
        builder.add(ModStateProperties.COLOR);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_3;
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state) || !isMaxAge(state);
    }

    public void grow(BlockState state, Level level, BlockPos pos) {
        makeGrowOnBonemeal(level, pos, state);
        
        if(isMaxAge(level.getBlockState(pos))) {
            BoblingEntity boblingEntity = new BoblingEntity(level, state.getValue(ModStateProperties.VIVICUS_CURED));
            boblingEntity.setPos(pos.getCenter());
            level.addFreshEntity(boblingEntity);
            level.removeBlock(pos, false);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        net.neoforged.neoforge.common.util.TriState soilDecision = level.getBlockState(pos.above()).canSustainPlant(level, pos.above(), Direction.DOWN, state);
        if (!soilDecision.isDefault()) return soilDecision.isTrue();
        return level.getBlockState(pos.above()).is(ModBlocks.VIVICUS_LEAVES.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction pFacing, BlockState pFacingState, LevelAccessor level, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return pFacing == Direction.UP && !state.canSurvive(level, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, pFacing, pFacingState, level, pCurrentPos, pFacingPos);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(random.nextDouble() <= 0.5D) {
            grow(state, level, pos);
        }
        
        super.randomTick(state, level, pos, random);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return !isMaxAge(state);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        grow(state, level, pos);
    }

    @Override
    public Map<DyeColor, Integer> colorValues() {
        return Util.make(Maps.newLinkedHashMap(), dyeColorHexFormatMap -> {
            dyeColorHexFormatMap.put(DyeColor.WHITE, 0xFFf2fcfc);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_GRAY, 0xFFd2cad8);
            dyeColorHexFormatMap.put(DyeColor.GRAY, 0xFFa4a9be);
            dyeColorHexFormatMap.put(DyeColor.BLACK, 0xFF585560);
            dyeColorHexFormatMap.put(DyeColor.BROWN, 0xFFe8b5bb);
            dyeColorHexFormatMap.put(DyeColor.RED, 0xFFff9ab7);
            dyeColorHexFormatMap.put(DyeColor.ORANGE, 0xFFffa586);
            dyeColorHexFormatMap.put(DyeColor.YELLOW, 0xFFffd2bf);
            dyeColorHexFormatMap.put(DyeColor.LIME, 0xFFddff97);
            dyeColorHexFormatMap.put(DyeColor.GREEN, 0xFFa2ffb2);
            dyeColorHexFormatMap.put(DyeColor.CYAN, 0xFF9bffda);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_BLUE, 0xFFc7fff2);
            dyeColorHexFormatMap.put(DyeColor.BLUE, 0xFFa7cdff);
            dyeColorHexFormatMap.put(DyeColor.PURPLE, 0xFFb4a5fb);
            dyeColorHexFormatMap.put(DyeColor.MAGENTA, 0xFFe9adff);
            dyeColorHexFormatMap.put(DyeColor.PINK, 0xFFfbe0ff);
        });
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return switch (state.getValue(ModStateProperties.AGE_3)) {
            case 1 -> SHAPE1.move(vec3.x, vec3.y, vec3.z);
            case 2 -> SHAPE2.move(vec3.x, vec3.y, vec3.z);
            default -> SHAPE0.move(vec3.x, vec3.y, vec3.z);
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return stateForPlacementHelper(super.getStateForPlacement(context), context);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return cloneItemStackHelper(state, super.getCloneItemStack(level, pos, state));
    }
}
