package net.abraxator.moresnifferflowers.mixins;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.abraxator.moresnifferflowers.client.shaders.ShaderTagRegistry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateMixin {
    @Shadow
    public abstract boolean is(TagKey<Block> tag);

    @WrapMethod(method = "getRenderShape")
    public RenderShape getRenderShape(Operation<RenderShape> original) {
        if (this.is(ShaderTagRegistry.CUSTOM_RENDER)){
            return RenderShape.INVISIBLE;
        }
        return original.call();
    }

}
