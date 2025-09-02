package net.abraxator.moresnifferflowers.blocks.vivicus;

import com.google.common.collect.Maps;
import net.abraxator.moresnifferflowers.blocks.ColorableVivicusBlock;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class VivicusLeavesBlock extends LeavesBlock implements ColorableVivicusBlock {
    public VivicusLeavesBlock(Properties p_54422_) {
        super(p_54422_);
        defaultBlockState().setValue(ModStateProperties.COLOR, DyeColor.WHITE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ModStateProperties.COLOR);
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

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return stateForPlacementHelper(super.getStateForPlacement(context), context);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
        return cloneItemStackHelper(state, super.getCloneItemStack(level, pos, state));
    }
}
