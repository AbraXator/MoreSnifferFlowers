package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.colors.Colorable;
import net.abraxator.moresnifferflowers.colors.Dye;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ColoredBlockEntity extends ModBlockEntity implements Colorable {
    public Dye dye = new Dye(DyeColor.WHITE, 0);
    
    public ColoredBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    public Map<DyeColor, Integer> colorValues() {
        return null;
    }

    @Override
    public void onAddDye(@Nullable ItemStack destinationStack, ItemStack dye, int amount) {

    }
}
