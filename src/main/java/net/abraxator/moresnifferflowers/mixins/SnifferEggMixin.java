package net.abraxator.moresnifferflowers.mixins;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnifferEggBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SnifferEggBlock.class)
public class SnifferEggMixin extends Block {
    public SnifferEggMixin(Properties pProperties) {
        super(pProperties);
    }
    
    @ModifyVariable(method = "onPlace", at = @At(value = "STORE"), ordinal = 0) 
    public int injected(int x) {
        return x / 2;
    }
}
