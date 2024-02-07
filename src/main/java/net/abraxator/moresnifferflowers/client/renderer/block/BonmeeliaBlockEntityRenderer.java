package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.blockentities.BonmeeliaBlockEntity;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.AtlasSet;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BonmeeliaBlockEntityRenderer implements BlockEntityRenderer<BonmeeliaBlockEntity> {
    private final Material TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/empty_jar"));
    private ModelPart root;
    
    public BonmeeliaBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        ModelPart carrotModelPart = pContext.bakeLayer(ModModelLayerLocations.GIANT_CARROT);
        this.root = carrotModelPart.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void render(BonmeeliaBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        VertexConsumer vertexConsumer = TEXTURE.buffer(pBufferSource, RenderType::entityCutout);
        if(pBlockEntity.hasHint) {
            root.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
        }
    }
}
