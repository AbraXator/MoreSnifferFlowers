package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;

public class GiantCarrotBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
	public static final ModelLayerLocation GIANT_CARROT = new ModelLayerLocation(new ResourceLocation(MoreSnifferFlowers.MOD_ID, "giant_carrot"), "main");
	public static final Material TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/giant_carrot"));
	private final ModelPart root;

	public GiantCarrotBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
		ModelPart modelPart = pContext.bakeLayer(GIANT_CARROT);
		this.root = modelPart.getChild("root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-19.0F, -7.0F, -19.0F, 38.0F, 8.0F, 38.0F, new CubeDeformation(0.0F)).texOffs(0, 46).addBox(-15.0F, -30.0F, -15.0F, 30.0F, 24.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition rocks = root.addOrReplaceChild("rocks", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r1 = rocks.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(10, 10).addBox(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.6F, -1.0F, -18.7F, -2.7314F, -0.2112F, -2.9601F));
		PartDefinition cube_r2 = rocks.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 6).addBox(-8.0F, -2.0F, -3.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.0F, -2.0F, 8.0F, 0.6562F, -1.4259F, -0.3124F));
		PartDefinition cube_r3 = rocks.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-22.0F, -3.0F, 7.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.6F, -0.9F, 20.1F, 0.2199F, -0.3979F, -0.192F));
		PartDefinition cube_r4 = rocks.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(10, 10).addBox(7.0F, -2.0F, 3.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.7F, -1.4F, -8.0F, -3.07F, 1.1222F, 2.8449F));
		PartDefinition cube_r5 = rocks.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -5.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(22.0F, -2.0F, 21.0F, 0.3491F, 1.1345F, 0.0F));
		PartDefinition cube_r6 = rocks.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0F, -1.2F, 1.9F, 0.2845F, 1.4731F, -0.9268F));
		PartDefinition leaves = root.addOrReplaceChild("leaves", CubeListBuilder.create().texOffs(84, 100).addBox(-8.0F, -33.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition leaf_2_r1 = leaves.addOrReplaceChild("leaf_2_r1", CubeListBuilder.create().texOffs(0, 58).addBox(0.0F, -19.5F, -21.5F, 0.0F, 18.0F, 42.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -31.5F, 0.5F, 0.0F, -0.7854F, 0.0F));
		PartDefinition leaf_1_r1 = leaves.addOrReplaceChild("leaf_1_r1", CubeListBuilder.create().texOffs(90, 46).addBox(-20.5F, -19.5F, 0.0F, 42.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -31.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
		BlockState blockState = pBlockEntity.getBlockState();
		VertexConsumer vertexConsumer = TEXTURE.buffer(pBufferSource, RenderType::entityCutout);

		if(blockState.is(ModBlocks.GIANT_CARROT.get()) && GiantCropBlock.isCenter(blockState)) {
			pPoseStack.pushPose();
			pPoseStack.translate(0.5, 1.5, 0.5);
			pPoseStack.mulPose(new Quaternionf().rotateX((float) (Math.PI)));
			this.root.render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
			pPoseStack.popPose();
		}
	}
}