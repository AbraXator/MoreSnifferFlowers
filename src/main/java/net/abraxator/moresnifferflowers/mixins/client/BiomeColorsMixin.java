package net.abraxator.moresnifferflowers.mixins.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.abraxator.moresnifferflowers.client.MSFColorHandler;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    @ModifyReturnValue(method = "getAverageFoliageColor", at = @At(value = "RETURN"))
    private static int injectBlockColors(int original, @Local(argsOnly = true) BlockAndTintGetter level, @Local(argsOnly = true) BlockPos blockPos) {
        return MSFColorHandler.getTransformedLeavesColor(original, level, blockPos);
    }
}
