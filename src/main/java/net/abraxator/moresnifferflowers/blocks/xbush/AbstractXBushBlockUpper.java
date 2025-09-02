package net.abraxator.moresnifferflowers.blocks.xbush;

import net.abraxator.moresnifferflowers.blockentities.XbushBlockEntity;
import net.abraxator.moresnifferflowers.blocks.ModEntityBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractXBushBlockUpper extends AbstractXBushBlockBase implements ModEntityBlock {
    public static final int AGE_TO_GROW_UP = 4;

    public AbstractXBushBlockUpper(Properties properties) {
        super(properties);
    }

    @Override
    public void onCorrupt(Level level, BlockPos pos, BlockState oldState, Block corruptedBlock) {
        if (isLower(level.getBlockState(pos.below()))) {
            getCorruptedBlock(getLowerBlock(), level.getRandom()).ifPresent(block ->
                    level.setBlockAndUpdate(pos.below(), block.withPropertiesOf(level.getBlockState(pos.below()))));
        }
        level.setBlockAndUpdate(pos, corruptedBlock.withPropertiesOf(oldState));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        ENTITY_POS = pos;
        return new XbushBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(level);
    }
}
