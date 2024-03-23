package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.blockentities.CropressorBlockEntity;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.BlockState;

public class CropressorBlockEntityRenderer implements BlockEntityRenderer<CropressorBlockEntity> {
    private static final Material TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/cropressor"));
    private final ModelPart root;
    private final ModelPart pistons;
    private final ModelPart piston_1;
    private final ModelPart piston_2;
    private final ModelPart piston_3;

    public CropressorBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        ModelPart modelPart = pContext.bakeLayer(ModModelLayerLocations.CROPRESSOR);
        this.root = modelPart.getChild("root");
        this.pistons = root.getChild("pistons");
        this.piston_1 = pistons.getChild("piston_1");
        this.piston_2 = pistons.getChild("piston_2");
        this.piston_3 = pistons.getChild("piston_3");
    }

    @Override
    public void render(CropressorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState blockState = pBlockEntity.getBlockState();
        VertexConsumer vertexConsumer = TEXTURE.buffer(pBufferSource, RenderType::entityCutout);

        /*if(blockState.is(ModBlocks.CROPRESSOR.get()) && blockState.getValue(CropressorBlock.PART) == CropressorBlock.Part.CENTER) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.5, 1.5, 0.5);
            root.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
            pPoseStack.popPose();
        }*/
    }
}
