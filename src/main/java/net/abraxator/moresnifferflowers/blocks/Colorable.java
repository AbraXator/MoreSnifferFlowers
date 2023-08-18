package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.OgingoBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.Tags;

public interface Colorable {
    default void color(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        OgingoBlockEntity entity = pLevel.getBlockEntity(pPos) instanceof OgingoBlockEntity ? ((OgingoBlockEntity) pLevel.getBlockEntity(pPos)) : null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;

        if(entity == null) return;
        if(pPlayer.getItemInHand(pHand).getItem() instanceof DyeItem dyeItem) {
            if(entity.hasColor()) {
                int k = entity.color;
                float f = (k >> 16 & 255) / 255;
                float f1 = (k >> 8 & 255) / 255;
                float f2 = (k & 255) / 255;
                i += (Math.max(f, Math.max(f1, f2))) * 255;
                aint[0] += f * 255;
                aint[1] += f1 * 255;
                aint[2] += f2 * 255;
                ++j;
            }

            float[] afloat = dyeItem.getDyeColor().getTextureDiffuseColors();
            int i2 = (int) (afloat[0] * 255);
            int l = (int) (afloat[1] * 255);
            int i1 = (int) (afloat[2] * 255);
            i += Math.max(i2, Math.max(l, i1));
            aint[0] += i2;
            aint[1] += l;
            aint[2] += i1;
            ++j;

            int j1 = aint[0] / j;
            int k1 = aint[1] / j;
            int l1 = aint[2] / j;
            float f3 = i / j;
            float f4 = Math.max(j1, Math.max(k1, l1));
            j1 = (int) (j1 * f3 / f4);
            k1 = (int) (k1 * f3 / f4);
            l1 = (int) (l1 * f3 / f4);
            int j2 = (j1 << 8) + k1;
            j2 = (j2 << 8) + l1;
            entity.color = j2;
            entity.hasColor = true;
        }
    }

    default void clearColor(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
    }
}
