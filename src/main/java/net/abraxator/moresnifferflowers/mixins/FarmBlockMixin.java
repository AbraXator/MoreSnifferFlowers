package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmBlock.class)
public abstract class FarmBlockMixin extends Block {
    public FarmBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "canSurvive", at = @At("TAIL"), cancellable = true)
    public void canSurviveGiantCrop(@NotNull BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if(level.getBlockState(pos.above()).is(ModTags.ModBlockTags.GIANT_CROPS)) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "shouldMaintainFarmland", at = @At("TAIL"), cancellable = true)
    private static void shouldMaintainFarmlandGiantCrop(@NotNull BlockGetter blockGetter, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if(blockGetter.getBlockState(pos.above()).is(ModTags.ModBlockTags.GIANT_CROPS)) {
            info.setReturnValue(true);
        }
    }
}
