package net.abraxator.moresnifferflowers.client.renderer.entity;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.client.model.entity.BoblingModel;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BoblingRenderer extends MobRenderer<Bobling, BoblingModel<Bobling>> {
    public static final ResourceLocation RESOURCE_LOCATION = MoreSnifferFlowers.loc("textures/entity/bobling.png");

    public BoblingRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BoblingModel<>(pContext.bakeLayer(BoblingModel.LAYER_LOCATION)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(Bobling pEntity) {
        return RESOURCE_LOCATION;
    }

}
