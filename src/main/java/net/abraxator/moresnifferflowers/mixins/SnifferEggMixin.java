package net.abraxator.moresnifferflowers.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.SnifferEggBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SnifferEggBlock.class)
public class SnifferEggMixin extends Block {
    public SnifferEggMixin(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "onBlockAdded", at = @At(value = "STORE"), ordinal = 0)
    public int moresniffeflowers$injected(int x) {
        return x / 2;
    }
}
