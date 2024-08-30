package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.abraxator.moresnifferflowers.blockentities.BondripiaBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BondripiaBlockEntityRenderer implements BlockEntityRenderer<BondripiaBlockEntity> {
    private ModelPart model;

    public BondripiaBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        
    }

    @Override
    public void render(BondripiaBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        
    }
}
