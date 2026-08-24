package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.capability.CorruptionCapability;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.nikdo53.tinymultiblocklib.block.IMultiBlock;
import net.nikdo53.tinymultiblocklib.blockentities.IMultiBlockEntity;

public class DebugFlowerItem extends Item {
    public DebugFlowerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        if (blockState.is(ModBlocks.CORRUPTED_GRASS_BLOCK.get())){
            CorruptionCapability.printDebug(level.getChunkAt(pos));
        }

        if (IMultiBlock.isMultiblock(blockState) && level.getBlockEntity(pos) instanceof IMultiBlockEntity entity) {
            System.out.println("entity.getCenter() = " + entity.getCenter());
            System.out.println("entity.isPlaced() = " + entity.isPlaced());
        }

/*        new GhostModelRenderer(pos, 60, SimpleModels.simpleCube().bakeRoot(), new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/amber_block")))
                .enableFadeOut(60)
                .addToRenderList();*/

/*
        new GhostBlockRenderer(pos, 20*5, Blocks.DIAMOND_BLOCK.defaultBlockState())
                .setARGB(1, 0.5f, 0.5f, 0.5f)
                .addToRenderList();
*/

        return super.useOn(context);
    }
}
