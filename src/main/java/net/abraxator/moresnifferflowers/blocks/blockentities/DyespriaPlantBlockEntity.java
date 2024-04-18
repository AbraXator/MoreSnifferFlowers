package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.colors.Dye;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DyespriaPlantBlockEntity extends ColoredBlockEntity {
    public DyespriaPlantBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DYESPRIA_PLANT.get(), pPos, pBlockState);
    }

    @Override
    public void onAddDye(@Nullable ItemStack destinationStack, ItemStack itemStack, int amount) {
        dye = Dye.getDyeFromStack(itemStack);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        BlockState blockState = getBlockState().setValue(ModStateProperties.COLOR, dye.color());
        
        if(dye.isEmpty()) {
            blockState.setValue(ModStateProperties.COLOR, DyeColor.WHITE);
        }
        
        level.setBlockAndUpdate(getBlockPos(), blockState);
    }
}
