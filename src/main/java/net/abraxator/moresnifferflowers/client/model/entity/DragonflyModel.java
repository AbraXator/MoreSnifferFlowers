package net.abraxator.moresnifferflowers.client.model.entity;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class DragonflyModel extends Model {
	private final ModelPart root;
	private final ModelPart wing1;
	private final ModelPart wing2;

	public DragonflyModel(ModelPart root) {
		super(RenderType::entityCutout);
		this.root = root.getChild("root");
		this.wing1 = this.root.getChild("wing1");
		this.wing2 = this.root.getChild("wing2");
	}
	
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0984F, 16.7023F, -0.5624F, 1.5708F, 0.0F, 3.1416F));
		PartDefinition wing2_r1 = root.addOrReplaceChild("wing2", CubeListBuilder.create().texOffs(0, 11).mirror().addBox(-4.5F, -8.5F, 0.0F, 9.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1528F, 0.2996F, 0.0624F, 2.0416F, -1.5272F, -3.1416F));
		PartDefinition cube_r1 = root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0224F, -0.0258F, -5.7004F, 0.0309F, 0.0308F, -2.3557F));
		PartDefinition cube_r2 = root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, 0.25F, -1.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.508F, 0.151F, -5.7004F, 0.0309F, -0.0308F, -0.7859F));
		PartDefinition wing1_r1 = root.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(0, 11).addBox(-5.0F, -8.0F, 0.0F, 9.0F, 17.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.492F, -0.074F, -0.2004F, -1.1F, 1.5272F, 0.0F));
		PartDefinition cube_r3 = root.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(10, 0).addBox(-1.0F, -1.0F, -1.0F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.008F, 1.151F, 5.1996F, 0.0F, -0.829F, 0.0F));
		PartDefinition cube_r4 = root.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -6.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.492F, 3.151F, 0.7996F, 0.0F, -0.0436F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public void animate(float pPartialTick) {
		this.wing1.zRot = (float) (Mth.cos(pPartialTick * 74.48451F * Mth.PI / 180.0F) * Mth.PI * 0.25);
		this.wing2.zRot = (float) -(Mth.cos(pPartialTick * 74.48451F * Mth.PI / 180.0F) * Mth.PI * 0.25);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}