package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blockentities.MultiBlockEntity;
import net.abraxator.moresnifferflowers.blocks.multiblock.MultiBlock;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

import java.util.List;

import static net.abraxator.moresnifferflowers.blocks.GiantCropBlock.WATERLOGGED;

public class GiantCropItem extends BlockItem {
    public GiantCropItem(Block pBlock, Properties properties) {
        super(pBlock, properties);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        var level = context.getLevel();
        var clickPos = context.getClickedPos().relative(context.getClickedFace(), 1);
        var aabb = AABB.ofSize(clickPos.getCenter(), 2, 2, 2);
        MultiBlock multiBlock = (MultiBlock) state.getBlock();

        multiBlock.fullBlockShape(clickPos, null).forEach(pos -> {

            boolean isWaterLogged = context.getLevel().getFluidState(pos).getType() == Fluids.WATER;

            level.setBlockAndUpdate(pos, this.getBlock().defaultBlockState()
                    .setValue(ModStateProperties.CENTER, pos.equals(clickPos))
                    .setValue(WATERLOGGED, isWaterLogged));

            if (level.getBlockEntity(pos) instanceof MultiBlockEntity entity) {
                entity.setCenter(clickPos);
            }
        });

        return true;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        var pos = context.getClickedPos();
        var level = context.getLevel();
        MultiBlock multiBlock = (MultiBlock) state.getBlock();
        var aabb = AABB.ofSize(context.getClickedPos().relative(context.getClickedFace(), 1).getCenter(), 2, 2, 2);
        var ret = multiBlock.fullBlockShape(pos.relative(context.getClickedFace()), null).allMatch(blockPos -> level.getBlockState(blockPos).canBeReplaced());

        return ret;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);

        components.add(Component.literal("CREATIVE ONLY").withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));
    }
}
