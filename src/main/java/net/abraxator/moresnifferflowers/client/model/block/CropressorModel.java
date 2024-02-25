package net.abraxator.moresnifferflowers.client.model.block;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.model.geom.builders.*;

public class CropressorModel {
    public static TexturedModelData createBodyLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData nejsemvidet = root.addChild("nejsemvidet", ModelPartBuilder.create().uv(0, 44).cuboid(-3.0F, -10.0F, 2.0F, 12.0F, 8.0F, 0.0F, new Dilation(0.0F))
                .uv(0, 24).cuboid(9.0F, -10.0F, 2.0F, 0.0F, 8.0F, 12.0F, new Dilation(0.0F))
                .uv(24, 14).cuboid(-3.0F, -10.0F, 2.0F, 12.0F, 0.0F, 12.0F, new Dilation(0.0F))
                .uv(24, 36).cuboid(-3.0F, -10.0F, 14.0F, 12.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 0.0F, -8.0F));

        ModelPartData bone = root.addChild("bone", ModelPartBuilder.create().uv(50, 27).cuboid(9.0F, -7.0F, 13.0F, 5.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(47, 1).cuboid(9.0F, -7.0F, 2.0F, 5.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.0F, -2.0F, 2.0F, 17.0F, 2.0F, 12.0F, new Dilation(0.0F))
                .uv(48, 33).cuboid(-13.0F, -4.0F, 1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 44).cuboid(-11.0F, -14.0F, 6.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(42, 38).cuboid(-12.0F, -15.0F, 5.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-15.0F, -10.0F, 2.0F, 12.0F, 10.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 0.0F, -8.0F));

        ModelPartData pistons = root.addChild("pistons", ModelPartBuilder.create(), ModelTransform.pivot(8.0F, 0.0F, -8.0F));

        ModelPartData piston2 = pistons.addChild("piston_2", ModelPartBuilder.create().uv(46, 7).cuboid(0.0F, -6.0F, 2.1F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 6).cuboid(-2.0F, -8.0F, 6.1F, 5.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData piston1 = pistons.addChild("piston_1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.5F, -1.75F, 5.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 21).cuboid(-0.5F, -0.5F, -0.75F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -5.5F, 10.65F));

        ModelPartData piston3 = pistons.addChild("piston_3", ModelPartBuilder.create().uv(0, 14).cuboid(6.0F, -9.9F, 7.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(40, 45).cuboid(5.0F, -4.9F, 6.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(meshdefinition, 128, 128);
    }
}
