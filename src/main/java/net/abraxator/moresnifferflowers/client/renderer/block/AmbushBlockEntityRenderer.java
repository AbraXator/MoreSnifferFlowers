package net.abraxator.moresnifferflowers.client.renderer.block;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class AmbushBlockEntityRenderer implements BlockEntityRenderer<AmbushBlockEntity> {
    private final BlockRenderManager blockRenderer;

    public AmbushBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        this.blockRenderer = context.getRenderManager();
    }

    @Override
    public void render(AmbushBlockEntity pBlockEntity, float pPartialTick, MatrixStack pPoseStack, VertexConsumerProvider pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.getCachedState().get(AmbushBlock.HALF) == DoubleBlockHalf.UPPER) {
            BlockState state = ModBlocks.AMBER.getDefaultState();
            pPoseStack.push();
            float progress = Math.min(pBlockEntity.growProgress, 1);
            float translate = 0.5f -(progress  * 0.5f);
            pPoseStack.translate(translate, translate, translate);
            pPoseStack.scale(progress, progress, progress);
            this.blockRenderer.renderBlockAsEntity(state, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);
            pPoseStack.pop();
        }
    }
}
