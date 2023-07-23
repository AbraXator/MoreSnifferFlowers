package net.abraxator.moresnifferflowers.client.model.entity;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class BoblingModel<T extends Bobling> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MoreSnifferFlowers.MOD_ID, "boblingmodel"), "main");
	private final ModelPart bobling;

	public BoblingModel(ModelPart root) {
		this.bobling = root.getChild("bobling");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bobling = partdefinition.addOrReplaceChild("bobling", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = bobling.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso_lower = body.addOrReplaceChild("torso_lower", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -4.0F, -2.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition torso_upper = body.addOrReplaceChild("torso_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso_upper_r1 = torso_upper.addOrReplaceChild("torso_upper_r1", CubeListBuilder.create().texOffs(17, 24).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -6.0F, 0.5F, 0.0F, 0.9599F, 0.0F));

		PartDefinition legs = body.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_feet = legs.addOrReplaceChild("right_feet", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(12, 16).addBox(-0.5F, 1.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.0F, 0.5F));

		PartDefinition left_feet = legs.addOrReplaceChild("left_feet", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 1.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -1.0F, 0.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 16).addBox(-2.5F, -11.0F, -1.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leaves = head.addOrReplaceChild("leaves", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leaf2_r1 = leaves.addOrReplaceChild("leaf2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.5F, 0.0F, 16.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5303F, -14.5F, 0.5303F, 0.0F, 0.7854F, 0.0F));

		PartDefinition leaf1_r1 = leaves.addOrReplaceChild("leaf1_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-8.0F, -18.0F, 0.75F, 16.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bobling.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return null;
	}
}