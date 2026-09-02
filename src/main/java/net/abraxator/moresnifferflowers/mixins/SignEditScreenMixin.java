package net.abraxator.moresnifferflowers.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.abraxator.moresnifferflowers.blocks.ColorableVivicusBlock;
import net.abraxator.moresnifferflowers.components.Colorable;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFStateProperties;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.world.level.block.state.BlockState;
import net.nikdo53.nikdocolor.NikdoColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSignEditScreen.class)
public class SignEditScreenMixin {

    @WrapOperation(method = "renderSign", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractSignEditScreen;renderSignBackground(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/level/block/state/BlockState;)V"))
    private void colorVivicusWrap(AbstractSignEditScreen instance, GuiGraphics guiGraphics, BlockState state, Operation<Void> original) {
        if (state.is(MSFBlocks.VIVICUS_SIGN.get()) || state.is(MSFBlocks.VIVICUS_WALL_SIGN.get()) || state.is(MSFBlocks.VIVICUS_HANGING_SIGN.get()) || state.is(MSFBlocks.VIVICUS_WALL_HANGING_SIGN.get())) {
            NikdoColor.RGB color = NikdoColor.fromHex(((ColorableVivicusBlock) state.getBlock()).colorValues().get(state.getValue(MSFStateProperties.COLOR)));

            float[] originalColor = RenderSystem.getShaderColor();
            guiGraphics.setColor(color.getRed(), color.getGreen(), color.getBlue(), originalColor[3]);
            original.call(instance, guiGraphics, state);
            guiGraphics.setColor(originalColor[0], originalColor[1], originalColor[2], originalColor[3]);
        } else
            original.call(instance, guiGraphics, state);
    }

}
