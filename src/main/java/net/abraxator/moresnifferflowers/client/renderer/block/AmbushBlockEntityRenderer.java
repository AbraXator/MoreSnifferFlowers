package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.abraxator.moresnifferflowers.blockentities.XbushBlockEntity;
import net.abraxator.moresnifferflowers.blocks.xbush.AbstractXBushBlockUpper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

public class AmbushBlockEntityRenderer implements BlockEntityRenderer<XbushBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public AmbushBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(XbushBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if(blockEntity.getBlockState().getBlock() instanceof AbstractXBushBlockUpper bushBlockUpper) {
            BlockState state = bushBlockUpper.getDropBlock().defaultBlockState();
            poseStack.pushPose();
            float progress = Math.min(blockEntity.growProgress, 1);
            float translate = 0.5f -(progress  * 0.5f);
            poseStack.translate(translate, translate, translate);
            poseStack.scale(progress, progress, progress);
            this.blockRenderer.renderSingleBlock(state, poseStack, bufferSource, packedLight, packedOverlay, ModelData.EMPTY, RenderType.translucent());
            poseStack.popPose();
        }
    }
}