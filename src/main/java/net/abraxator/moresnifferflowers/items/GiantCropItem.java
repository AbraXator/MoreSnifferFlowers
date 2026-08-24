package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.nikdo53.tinymultiblocklib.blockentities.IMultiBlockEntity;
import net.nikdo53.tinymultiblocklib.block.IMultiBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.abraxator.moresnifferflowers.blocks.GiantCropBlock.WATERLOGGED;

public class GiantCropItem extends BlockItem {
    public GiantCropItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        var level = context.getLevel();
        var clickPos = context.getClickedPos().relative(context.getClickedFace(), 1);
        var aabb = AABB.ofSize(clickPos.getCenter(), 2, 2, 2);
        IMultiBlock multiBlock = (IMultiBlock) state.getBlock();

        multiBlock.getFullBlockShape(level, clickPos, state).getGlobalPositions().forEach(pos -> {

            boolean isWaterLogged = context.getLevel().getFluidState(pos).getType() == Fluids.WATER;

            level.setBlockAndUpdate(pos, this.getBlock().defaultBlockState()
                    .setValue(ModStateProperties.CENTER, pos.equals(clickPos))
                    .setValue(WATERLOGGED, isWaterLogged));

            if (level.getBlockEntity(pos) instanceof IMultiBlockEntity entity) {
                entity.setCenter(clickPos);
            }
        });

        return true;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        var pos = context.getClickedPos();
        var level = context.getLevel();
        IMultiBlock multiBlock = (IMultiBlock) state.getBlock();
        var aabb = AABB.ofSize(context.getClickedPos().relative(context.getClickedFace(), 1).getCenter(), 2, 2, 2);

        return multiBlock.getFullBlockShape(level, pos.relative(context.getClickedFace()), state).getGlobalPositions().stream().allMatch(blockPos -> level.getBlockState(blockPos).canBeReplaced());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.literal("CREATIVE ONLY").withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));
    }
}