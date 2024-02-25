package net.abraxator.moresnifferflowers.client.model.block;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.model.geom.builders.*;

public class GiantCropModels {
    public static TexturedModelData createGiantCarrotLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-19.0F, -7.0F, -19.0F, 38.0F, 8.0F, 38.0F, new Dilation(0.0F)).uv(0, 46).cuboid(-15.0F, -30.0F, -15.0F, 30.0F, 24.0F, 30.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData rocks = root.addChild("rocks", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData cube_r1 = rocks.addChild("cube_r1", ModelPartBuilder.create().uv(10, 10).cuboid(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-14.6F, -1.0F, -18.7F, -2.7314F, -0.2112F, -2.9601F));
        ModelPartData cube_r2 = rocks.addChild("cube_r2", ModelPartBuilder.create().uv(0, 6).cuboid(-8.0F, -2.0F, -3.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-21.0F, -2.0F, 8.0F, 0.6562F, -1.4259F, -0.3124F));
        ModelPartData cube_r3 = rocks.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-22.0F, -3.0F, 7.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(13.6F, -0.9F, 20.1F, 0.2199F, -0.3979F, -0.192F));
        ModelPartData cube_r4 = rocks.addChild("cube_r4", ModelPartBuilder.create().uv(10, 10).cuboid(7.0F, -2.0F, 3.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(19.7F, -1.4F, -8.0F, -3.07F, 1.1222F, 2.8449F));
        ModelPartData cube_r5 = rocks.addChild("cube_r5", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -5.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(22.0F, -2.0F, 21.0F, 0.3491F, 1.1345F, 0.0F));
        ModelPartData cube_r6 = rocks.addChild("cube_r6", ModelPartBuilder.create().uv(0, 6).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(20.0F, -1.2F, 1.9F, 0.2845F, 1.4731F, -0.9268F));
        ModelPartData leaves = root.addChild("leaves", ModelPartBuilder.create().uv(84, 100).cuboid(-8.0F, -33.0F, -8.0F, 16.0F, 4.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData leaf_2_r1 = leaves.addChild("leaf_2_r1", ModelPartBuilder.create().uv(0, 58).cuboid(0.0F, -19.5F, -21.5F, 0.0F, 18.0F, 42.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -31.5F, 0.5F, 0.0F, -0.7854F, 0.0F));
        ModelPartData leaf_1_r1 = leaves.addChild("leaf_1_r1", ModelPartBuilder.create().uv(90, 46).cuboid(-20.5F, -19.5F, 0.0F, 42.0F, 18.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -31.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        return TexturedModelData.of(meshdefinition, 256, 256);
    }

    public static TexturedModelData createGiantPotatoLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-15.0F, -34.0F, -15.0F, 30.0F, 35.0F, 30.0F, new Dilation(0.0F))
                .uv(62, 65).cuboid(-13.0F, -38.0F, -13.0F, 26.0F, 4.0F, 26.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(0, 89).cuboid(-2.5F, -3.5F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-7.3861F, -28.1887F, 17.0546F, -2.0591F, -0.9234F, 1.5556F));
        ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(0, 89).cuboid(-1.5F, -2.5F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-17.6225F, -7.4366F, -7.7957F, 0.032F, 0.0349F, -0.7966F));
        ModelPartData cube_r3 = root.addChild("cube_r3", ModelPartBuilder.create().uv(0, 89).cuboid(-1.5F, -3.5F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.9364F, -36.922F, -14.2465F, 0.5726F, -0.126F, 0.5276F));
        ModelPartData cube_r4 = root.addChild("cube_r4", ModelPartBuilder.create().uv(0, 89).cuboid(-3.5F, -3.0F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(18.25F, -9.5F, 7.5F, -0.3491F, 0.0F, 0.48F));
        ModelPartData cube_r5 = root.addChild("cube_r5", ModelPartBuilder.create().uv(12, 89).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(17.25F, -27.0F, -9.5F, 0.2182F, 0.0F, 0.48F));
        ModelPartData cube_r6 = root.addChild("cube_r6", ModelPartBuilder.create().uv(0, 21).cuboid(-0.25F, -11.5F, -22.5F, 0.0F, 12.0F, 44.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -38.5F, 0.5F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r7 = root.addChild("cube_r7", ModelPartBuilder.create().uv(0, 77).cuboid(-21.5F, -11.5F, -0.25F, 44.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -38.5F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r8 = root.addChild("cube_r8", ModelPartBuilder.create().uv(0, 14).cuboid(-3.5F, -2.5F, -2.5F, 5.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-18.4128F, -3.5302F, 16.9959F, -0.0875F, -0.0151F, -0.1715F));
        ModelPartData cube_r9 = root.addChild("cube_r9", ModelPartBuilder.create().uv(0, 14).cuboid(0.0F, -2.0F, -2.0F, 5.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -4.0F, -20.0F, 0.1745F, 0.0F, 0.0F));
        ModelPartData smlpotat2 = root.addChild("smlpotat2", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0328F, 4.0F, -2.9899F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(10.0328F, -9.75F, 21.9899F, -0.2618F, 0.0F, 0.0F));
        ModelPartData cube_r10 = smlpotat2.addChild("cube_r10", ModelPartBuilder.create().uv(90, 0).cuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData cube_r11 = smlpotat2.addChild("cube_r11", ModelPartBuilder.create().uv(90, 0).cuboid(-3.1F, -4.5F, 0.5F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.2828F, 0.5F, -0.9899F, 0.0F, -0.7854F, 0.0F));
        ModelPartData smlpotat = root.addChild("smlpotat", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0328F, 4.0F, -2.9899F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-17.9672F, -10.0F, -20.0101F, 0.1307F, 0.0F, -0.0875F));
        ModelPartData cube_r12 = smlpotat.addChild("cube_r12", ModelPartBuilder.create().uv(90, 0).cuboid(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData cube_r13 = smlpotat.addChild("cube_r13", ModelPartBuilder.create().uv(90, 0).cuboid(-3.1F, -4.5F, 0.5F, 8.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.2828F, 0.5F, -0.9899F, 0.0F, -0.7854F, 0.0F));

        return TexturedModelData.of(meshdefinition, 256, 256);
    }

    public static TexturedModelData createNetherwartLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create().uv(0, 16).cuboid(14.0F, -7.0F, -20.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(80, 90).cuboid(11.0F, -19.0F, -23.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-17.0F, -44.0F, -17.0F, 34.0F, 20.0F, 34.0F, new Dilation(0.0F))
                .uv(0, 86).cuboid(-10.0F, -20.0F, -10.0F, 20.0F, 20.0F, 20.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-23.0F, -23.0F, 16.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 54).cuboid(-21.0F, -15.0F, 18.0F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F))
                .uv(84, 58).cuboid(-14.0F, -24.0F, -14.0F, 28.0F, 4.0F, 28.0F, new Dilation(0.0F))
                .uv(84, 64).cuboid(-16.5F, -9.0F, -17.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(18, 16).cuboid(-15.0F, -4.0F, -16.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(11.0F, -4.0F, 16.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(84, 54).cuboid(9.5F, -9.0F, 14.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 54).cuboid(-14.0F, -48.0F, -14.0F, 28.0F, 4.0F, 28.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 256, 256);
    }

    public static TexturedModelData createBeetrootLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-14.0F, -25.0F, -14.0F, 28.0F, 21.0F, 28.0F, new Dilation(0.0F))
                .uv(80, 74).cuboid(-12.0F, -28.0F, -12.0F, 24.0F, 3.0F, 24.0F, new Dilation(0.0F))
                .uv(84, 0).cuboid(-11.0F, -4.0F, -11.0F, 22.0F, 5.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(0, 75).cuboid(-25.0F, -22.0F, -0.75F, 52.0F, 23.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -29.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-0.75F, -22.0F, -27.0F, 0.0F, 23.0F, 52.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -29.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData sml2 = root.addChild("sml2", ModelPartBuilder.create().uv(84, 0).cuboid(-2.0F, 0.6667F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-17.0F, -1.6667F, 2.0F));
        ModelPartData cube_r3 = sml2.addChild("cube_r3", ModelPartBuilder.create().uv(15, 21).cuboid(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.3333F, 0.0F, 0.0F, -2.3562F, 0.0F));
        ModelPartData cube_r4 = sml2.addChild("cube_r4", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.3333F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData sml1 = root.addChild("sml1", ModelPartBuilder.create().uv(84, 0).cuboid(-2.0F, 0.6667F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(17.0F, -1.6667F, -17.0F, 0.0F, -0.7418F, 0.0F));
        ModelPartData cube_r5 = sml1.addChild("cube_r5", ModelPartBuilder.create().uv(15, 21).cuboid(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.3333F, 0.0F, 0.0F, -2.3562F, 0.0F));
        ModelPartData cube_r6 = sml1.addChild("cube_r6", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -2.0F, 0.0F, 6.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.3333F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData smol3 = root.addChild("smol3", ModelPartBuilder.create().uv(0, 0).cuboid(-3.2929F, 3.5F, -3.7071F, 7.0F, 4.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 21).cuboid(-2.2929F, 2.5F, -2.7071F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-16.7071F, -6.5F, -17.2929F, 0.0F, -1.0036F, 0.0F));
        ModelPartData cube_r7 = smol3.addChild("cube_r7", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r8 = smol3.addChild("cube_r8", ModelPartBuilder.create().uv(0, 6).cuboid(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData smol2 = root.addChild("smol2", ModelPartBuilder.create().uv(0, 0).cuboid(-3.2929F, 3.5F, -3.7071F, 7.0F, 4.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 21).cuboid(-2.2929F, 2.5F, -2.7071F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(-3.7071F, -6.5F, 18.7071F, 0.0F, -0.2182F, 0.0F));
        ModelPartData cube_r9 = smol2.addChild("cube_r9", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r10 = smol2.addChild("cube_r10", ModelPartBuilder.create().uv(0, 6).cuboid(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
        ModelPartData smol1 = root.addChild("smol1", ModelPartBuilder.create().uv(0, 0).cuboid(-3.2929F, 3.5F, -3.7071F, 7.0F, 4.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 21).cuboid(-2.2929F, 2.5F, -2.7071F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(19.2929F, -6.5F, 3.7071F));
        ModelPartData cube_r11 = smol1.addChild("cube_r11", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        ModelPartData cube_r12 = smol1.addChild("cube_r12", ModelPartBuilder.create().uv(0, 6).cuboid(0.0F, -2.5F, -5.0F, 0.0F, 5.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        return TexturedModelData.of(meshdefinition, 256, 256);
    }

    public static TexturedModelData createBodyLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData bb_main = partdefinition.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -48.0F, 0.0F, 1.0F, 48.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 149).cuboid(-10.0F, -47.0F, -10.0F, 20.0F, 48.0F, 20.0F, new Dilation(0.0F))
                .uv(188, 51).cuboid(-23.0F, -50.0F, -11.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F))
                .uv(188, 0).cuboid(-23.0F, -50.0F, 11.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F))
                .uv(172, 149).cuboid(-23.0F, -50.0F, 17.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F))
                .uv(80, 149).cuboid(-23.0F, -50.0F, -17.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F))
                .uv(94, 52).cuboid(-11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F))
                .uv(94, 1).cuboid(-17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F))
                .uv(0, 52).cuboid(17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F))
                .uv(0, 1).cuboid(11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 512, 512);
    }

    public static TexturedModelData createWheatLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create().uv(0, 149).cuboid(-10.0F, -47.0F, -10.0F, 20.0F, 48.0F, 20.0F, new Dilation(0.0F))
                .uv(188, 51).cuboid(-23.0F, -50.0F, -11.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F))
                .uv(188, 0).cuboid(-23.0F, -50.0F, 11.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F))
                .uv(172, 149).cuboid(-23.0F, -50.0F, 17.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F))
                .uv(94, 52).cuboid(-11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F))
                .uv(94, 1).cuboid(-17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F))
                .uv(0, 1).cuboid(11.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F))
                .uv(0, 52).cuboid(17.0F, -50.0F, -23.0F, 0.0F, 51.0F, 46.0F, new Dilation(0.0F))
                .uv(80, 149).cuboid(-23.0F, -50.0F, -17.0F, 46.0F, 51.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 512, 512);
    }
}
