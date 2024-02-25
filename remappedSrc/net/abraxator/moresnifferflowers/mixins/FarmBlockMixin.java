package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmlandBlock.class)
public abstract class FarmBlockMixin extends Block {
    public FarmBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "canPlaceAt", at = @At("TAIL"), cancellable = true)
    public void canSurviveGiantCrop(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if(world.getBlockState(pos.up()).isIn(ModTags.ModBlockTags.GIANT_CROPS)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "hasCrop", at = @At("TAIL"), cancellable = true)
    private static void shouldMaintainFarmlandGiantCrop(BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if(world.getBlockState(pos.up()).isIn(ModTags.ModBlockTags.GIANT_CROPS)) {
            info.setReturnValue(true);
        }
    }
}
