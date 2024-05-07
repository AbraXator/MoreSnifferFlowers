package net.abraxator.moresnifferflowers.blockentities;

import net.abraxator.moresnifferflowers.colors.Dye;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DyespriaPlantBlockEntity extends ColoredBlockEntity {
    public DyespriaPlantBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DYESPRIA_PLANT.get(), pPos, pBlockState);
    }
}
