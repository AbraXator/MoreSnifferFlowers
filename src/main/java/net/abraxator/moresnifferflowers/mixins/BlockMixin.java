package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.blocks.ColorableVivicusBlock;
import net.abraxator.moresnifferflowers.init.MSFDataComponents;
import net.abraxator.moresnifferflowers.init.MSFStateProperties;
import net.abraxator.moresnifferflowers.init.MSFTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin  {
    @Inject(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void injectVivicusNBT(BlockState state, ServerLevel level, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfoReturnable<List<ItemStack>> cir) {
        Block block = state.getBlock();
        if (block instanceof ColorableVivicusBlock colorable){
            List<ItemStack> list = cir.getReturnValue();
            int colorId = state.getValue(MSFStateProperties.COLOR).getId();
            int color = colorable.colorValues().get(DyeColor.byId(colorId));

            for (ItemStack stack : list) {
                if (stack.is(MSFTags.ItemTags.COLORABLE)) {
                    stack.set(MSFDataComponents.COLOR, color);
                    stack.set(MSFDataComponents.COLOR_ID, colorId);
                }
            }

            cir.setReturnValue(list);

        }
    }
}
