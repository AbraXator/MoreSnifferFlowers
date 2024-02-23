package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.data.ModelData;

public class AmbushBlockEntityRenderer implements BlockEntityRenderer<AmbushBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public AmbushBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(AmbushBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.getBlockState().getValue(AmbushBlock.HALF) == DoubleBlockHalf.UPPER) {
            BlockState state = ModBlocks.AMBER.get().defaultBlockState();
            pPoseStack.pushPose();
            float progress = Math.min(pBlockEntity.growProgress, 1);
            float translate = 0.5f -(progress  * 0.5f);
            pPoseStack.translate(translate, 0.4f, translate);
            pPoseStack.scale(progress, progress, progress);
            this.blockRenderer.renderSingleBlock(state, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, ModelData.EMPTY, RenderType.translucent());
            pPoseStack.popPose();
        }
    }
}
