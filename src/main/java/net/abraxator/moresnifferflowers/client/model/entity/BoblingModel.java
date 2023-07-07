package net.abraxator.moresnifferflowers.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class BoblingModel<T extends Bobling> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(MoreSnifferFlowers.MOD_ID, "boblingmodel"), "main");
	private final ModelPart bobling;
	private final ModelPart head;

	public BoblingModel(ModelPart root) {
		this.bobling = root.getChild("bobling");
		this.head = bobling.getChild("body").getChild("head");
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
		this.bobling.getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(BoblingAnimation.BOBLING_WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
		this.animate(entity.sitDownAnimationState, BoblingAnimation.BOBLING_SIT_DOWN, ageInTicks, 1);
		this.animate(entity.sitPoseAnimationState, BoblingAnimation.BOBLING_SIT_POSE, ageInTicks, 1);
		this.animate(entity.standUpAnimationState, BoblingAnimation.BOBLING_STAND_UP, ageInTicks, 1);
	}

	private void applyHeadRotation(T pEntity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bobling.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.bobling;
	}
}