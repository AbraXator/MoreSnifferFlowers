package net.abraxator.moresnifferflowers.client.model.entity;// Made with Blockbench 4.7.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BoblingModel<T extends Bobling> extends SinglePartEntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart bobling;

	public BoblingModel(ModelPart root) {
		this.bobling = root.getChild("bobling");
	}

	public static TexturedModelData createBodyLayer() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();

		ModelPartData bobling = partdefinition.addChild("bobling", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = bobling.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData torso_lower = body.addChild("torso_lower", ModelPartBuilder.create().uv(0, 16).cuboid(-3.0F, -4.0F, -2.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

		ModelPartData torso_upper = body.addChild("torso_upper", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData torso_upper_r1 = torso_upper.addChild("torso_upper_r1", ModelPartBuilder.create().uv(17, 24).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -6.0F, 0.5F, 0.0F, 0.9599F, 0.0F));

		ModelPartData legs = body.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_feet = legs.addChild("right_feet", ModelPartBuilder.create().uv(0, 16).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(12, 16).cuboid(-0.5F, 1.0F, -1.5F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -1.0F, 0.5F));

		ModelPartData left_feet = legs.addChild("left_feet", ModelPartBuilder.create().uv(0, 16).cuboid(-0.5F, 1.0F, -1.5F, 1.0F, 0.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 17).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, -1.0F, 0.5F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(20, 16).cuboid(-2.5F, -11.0F, -1.5F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leaves = head.addChild("leaves", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leaf2_r1 = leaves.addChild("leaf2_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -4.5F, 0.0F, 16.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5303F, -14.5F, 0.5303F, 0.0F, 0.7854F, 0.0F));

		ModelPartData leaf1_r1 = leaves.addChild("leaf1_r1", ModelPartBuilder.create().uv(0, 8).cuboid(-8.0F, -18.0F, 0.75F, 16.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return TexturedModelData.of(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bobling.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return null;
	}
}