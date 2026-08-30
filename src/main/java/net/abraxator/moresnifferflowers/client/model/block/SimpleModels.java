package net.abraxator.moresnifferflowers.client.model.block;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public record SimpleModels(ModelPart root) {
    public static final ModelLayerLocation SIMPLE_CUBE = new ModelLayerLocation(MoreSnifferFlowers.loc("simple_cube"), "main");
    public static final ModelLayerLocation INVERTED_CUBE = new ModelLayerLocation(MoreSnifferFlowers.loc("inverted_cube"), "main");

    public SimpleModels(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition simpleCube() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


    public static LayerDefinition invertedCube() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(-22, -15).addBox(-8.0F, 0.0F, -8.0F, 16.0F, -16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

}
