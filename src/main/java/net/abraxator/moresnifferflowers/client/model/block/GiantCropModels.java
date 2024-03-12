package net.abraxator.moresnifferflowers.client.model.block;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class GiantCropModels {
    public static LayerDefinition createGiantCarrotLayer() {
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

    public static LayerDefinition createGiantPotatoLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-15.0F, -34.0F, -15.0F, 30.0F, 35.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(62, 65).addBox(-13.0F, -38.0F, -13.0F, 26.0F, 4.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition cube_r1 = root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 89).addBox(-2.5F, -3.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.3861F, -28.1887F, 17.0546F, -2.0591F, -0.9234F, 1.5556F));
        PartDefinition cube_r2 = root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 89).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.6225F, -7.4366F, -7.7957F, 0.032F, 0.0349F, -0.7966F));
        PartDefinition cube_r3 = root.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 89).addBox(-1.5F, -3.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9364F, -36.922F, -14.2465F, 0.5726F, -0.126F, 0.5276F));
        PartDefinition cube_r4 = root.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 89).addBox(-3.5F, -3.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.25F, -9.5F, 7.5F, -0.3491F, 0.0F, 0.48F));
        PartDefinition cube_r5 = root.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(12, 89).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.25F, -27.0F, -9.5F, 0.2182F, 0.0F, 0.48F));
        PartDefinition cube_r6 = root.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 21).addBox(-0.25F, -11.5F, -22.5F, 0.0F, 12.0F, 44.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -38.5F, 0.5F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r7 = root.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 77).addBox(-21.5F, -11.5F, -0.25F, 44.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -38.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r8 = root.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 14).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.4128F, -3.5302F, 16.9959F, -0.0875F, -0.0151F, -0.1715F));
        PartDefinition cube_r9 = root.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -2.0F, -2.0F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -4.0F, -20.0F, 0.1745F, 0.0F, 0.0F));
        PartDefinition smlpotat2 = root.addOrReplaceChild("smlpotat2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0328F, 4.0F, -2.9899F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0328F, -9.75F, 21.9899F, -0.2618F, 0.0F, 0.0F));
        PartDefinition cube_r10 = smlpotat2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(90, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition cube_r11 = smlpotat2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(90, 0).addBox(-3.1F, -4.5F, 0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2828F, 0.5F, -0.9899F, 0.0F, -0.7854F, 0.0F));
        PartDefinition smlpotat = root.addOrReplaceChild("smlpotat", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0328F, 4.0F, -2.9899F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.9672F, -10.0F, -20.0101F, 0.1307F, 0.0F, -0.0875F));
        PartDefinition cube_r12 = smlpotat.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(90, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition cube_r13 = smlpotat.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(90, 0).addBox(-3.1F, -4.5F, 0.5F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2828F, 0.5F, -0.9899F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public static LayerDefinition createNetherwartLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 16).addBox(14.0F, -7.0F, -20.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(80, 90).addBox(11.0F, -19.0F, -23.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-17.0F, -44.0F, -17.0F, 34.0F, 20.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(0, 86).addBox(-10.0F, -20.0F, -10.0F, 20.0F, 20.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-23.0F, -23.0F, 16.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-21.0F, -15.0F, 18.0F, 4.0F, 15.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(84, 58).addBox(-14.0F, -24.0F, -14.0F, 28.0F, 4.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(84, 64).addBox(-16.5F, -9.0F, -17.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(18, 16).addBox(-15.0F, -4.0F, -16.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(11.0F, -4.0F, 16.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(84, 54).addBox(9.5F, -9.0F, 14.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 54).addBox(-14.0F, -48.0F, -14.0F, 28.0F, 4.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public static LayerDefinition createBeetrootLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -25.0F, -14.0F, 28.0F, 21.0F, 28.0F, new CubeDeformation(0.0F))
                .texOffs(80, 74).addBox(-12.0F, -28.0F, -12.0F, 24.0F, 3.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(84, 0).addBox(-11.0F, -4.0F, -11.0F, 22.0F, 5.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition cube_r1 = root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 75).addBox(-25.0F, -22.0F, -0.75F, 52.0F, 23.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -29.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r2 = root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -22.0F, -27.0F, 0.0F, 23.0F, 52.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition sml2 = root.addOrReplaceChild("sml2", CubeListBuilder.create().texOffs(84, 0).addBox(-2.0F, 0.6667F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-17.0F, -1.6667F, 2.0F));
        PartDefinition cube_r3 = sml2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(15, 21).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.3333F, 0.0F, 0.0F, -2.3562F, 0.0F));
        PartDefinition cube_r4 = sml2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 49).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.3333F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition sml1 = root.addOrReplaceChild("sml1", CubeListBuilder.create().texOffs(84, 0).addBox(-2.0F, 0.6667F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0F, -1.6667F, -17.0F, 0.0F, -0.7418F, 0.0F));
        PartDefinition cube_r5 = sml1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(15, 21).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.3333F, 0.0F, 0.0F, -2.3562F, 0.0F));
        PartDefinition cube_r6 = sml1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 49).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.3333F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition smol3 = root.addOrReplaceChild("smol3", CubeListBuilder.create().texOffs(0, 0).addBox(-3.2929F, 3.5F, -3.7071F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-2.2929F, 2.5F, -2.7071F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.7071F, -6.5F, -17.2929F, 0.0F, -1.0036F, 0.0F));
        PartDefinition cube_r7 = smol3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r8 = smol3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition smol2 = root.addOrReplaceChild("smol2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.2929F, 3.5F, -3.7071F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-2.2929F, 2.5F, -2.7071F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.7071F, -6.5F, 18.7071F, 0.0F, -0.2182F, 0.0F));
        PartDefinition cube_r9 = smol2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r10 = smol2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        PartDefinition smol1 = root.addOrReplaceChild("smol1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.2929F, 3.5F, -3.7071F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-2.2929F, 2.5F, -2.7071F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(19.2929F, -6.5F, 3.7071F));
        PartDefinition cube_r11 = smol1.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        PartDefinition cube_r12 = smol1.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 6).addBox(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -48.0F, 0.0F, 1.0F, 48.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 149).addBox(-10.0F, -47.0F, -10.0F, 20.0F, 48.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(188, 51).addBox(-23.0F, -50.0F, -11.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(188, 0).addBox(-23.0F, -50.0F, 11.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(172, 149).addBox(-23.0F, -50.0F, 17.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(80, 149).addBox(-23.0F, -50.0F, -17.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, 52).addBox(-11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(94, 1).addBox(-17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    public static LayerDefinition createWheatLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 149).addBox(-10.0F, -47.0F, -10.0F, 20.0F, 48.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(188, 51).addBox(-23.0F, -50.0F, -11.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(188, 0).addBox(-23.0F, -50.0F, 11.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(172, 149).addBox(-23.0F, -50.0F, 17.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(94, 52).addBox(-11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(94, 1).addBox(-17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new CubeDeformation(0.0F))
                .texOffs(80, 149).addBox(-23.0F, -50.0F, -17.0F, 46.0F, 51.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }
}
