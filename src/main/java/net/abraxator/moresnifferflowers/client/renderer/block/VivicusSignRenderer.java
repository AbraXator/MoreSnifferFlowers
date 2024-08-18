package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VivicusSignRenderer extends SignRenderer {
    public VivicusSignRenderer(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void renderSignWithText(SignBlockEntity pSignEntity, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay, BlockState pState, SignBlock pSignBlock, WoodType pWoodType, Model pModel) {
        super.renderSignWithText(pSignEntity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pState, pSignBlock, pWoodType, pModel);
        
    }
    
    /*private void renderVivicusSign(Vivicus PoseStack pPoseStack, ) {
        pPoseStack.pushPose();
        float f = this.getSignModelRenderScale();
        pPoseStack.scale(f, -f, -f);
        Material material = this.getSignMaterial(pWoodType);
        VertexConsumer vertexconsumer = material.buffer(pBuffer, pModel::renderType);
        this.renderSignModel(pPoseStack, pPackedLight, pPackedOverlay, pModel, vertexconsumer);
        pPoseStack.popPose();
    }*/
}
