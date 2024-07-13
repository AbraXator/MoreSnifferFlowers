package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class VivicusAntidoteItem extends Item {
    public VivicusAntidoteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        
        if(blockState.is(ModBlocks.VIVICUS_SAPLING)) {
            
        }
    }
}
