package net.abraxator.moresnifferflowers.client.model.block;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CropressorModel {
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root.json", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition nejsemvidet = root.addOrReplaceChild("nejsemvidet", CubeListBuilder.create().texOffs(0, 44).addBox(-3.0F, -10.0F, 2.0F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(9.0F, -10.0F, 2.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(24, 14).addBox(-3.0F, -10.0F, 2.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(24, 36).addBox(-3.0F, -10.0F, 14.0F, 12.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(50, 27).addBox(9.0F, -7.0F, 13.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(47, 1).addBox(9.0F, -7.0F, 2.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -2.0F, 2.0F, 17.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(48, 33).addBox(-13.0F, -4.0F, 1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 44).addBox(-11.0F, -14.0F, 6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(42, 38).addBox(-12.0F, -15.0F, 5.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-15.0F, -10.0F, 2.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition pistons = root.addOrReplaceChild("pistons", CubeListBuilder.create(), PartPose.offset(8.0F, 0.0F, -8.0F));

        PartDefinition piston2 = pistons.addOrReplaceChild("piston_2", CubeListBuilder.create().texOffs(46, 7).addBox(0.0F, -6.0F, 2.1F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(-2.0F, -8.0F, 6.1F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition piston1 = pistons.addOrReplaceChild("piston_1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -2.5F, -1.75F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -5.5F, 10.65F));

        PartDefinition piston3 = pistons.addOrReplaceChild("piston_3", CubeListBuilder.create().texOffs(0, 14).addBox(6.0F, -9.9F, 7.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 45).addBox(5.0F, -4.9F, 6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
