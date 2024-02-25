package net.abraxator.moresnifferflowers.client.renderer.block;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.CropressorBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.CropressorBlockEntity;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;

public class CropressorBlockEntityRenderer implements BlockEntityRenderer<CropressorBlockEntity> {
    private static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, MoreSnifferFlowers.loc("block/cropressor"));
    private final ModelPart root;
    private final ModelPart pistons;
    private final ModelPart piston_1;
    private final ModelPart piston_2;
    private final ModelPart piston_3;

    public CropressorBlockEntityRenderer(BlockEntityRendererFactory.Context pContext) {
        ModelPart modelPart = pContext.getLayerModelPart(ModModelLayerLocations.CROPRESSOR);
        this.root = modelPart.getChild("root");
        this.pistons = root.getChild("pistons");
        this.piston_1 = pistons.getChild("piston_1");
        this.piston_2 = pistons.getChild("piston_2");
        this.piston_3 = pistons.getChild("piston_3");
    }

    @Override
    public void render(CropressorBlockEntity pBlockEntity, float pPartialTick, MatrixStack pPoseStack, VertexConsumerProvider pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState blockState = pBlockEntity.getCachedState();
        VertexConsumer vertexConsumer = TEXTURE.getVertexConsumer(pBufferSource, RenderLayer::getEntityCutout);

        if(blockState.isIn(ModBlocks.CROPRESSOR.get()) && blockState.get(CropressorBlock.PART) == CropressorBlock.Part.SHLONGADOODLE) {
            pPoseStack.push();
            pPoseStack.translate(0.5, 1.5, 0.5);
            root.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
            pPoseStack.pop();
        }
    }
}
