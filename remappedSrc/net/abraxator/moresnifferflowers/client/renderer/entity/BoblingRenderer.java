package net.abraxator.moresnifferflowers.client.renderer.entity;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.client.model.entity.BoblingModel;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class BoblingRenderer extends MobEntityRenderer<Bobling, BoblingModel<Bobling>> {
    public static final Identifier RESOURCE_LOCATION = MoreSnifferFlowers.loc("textures/entity/bobling.png");

    public BoblingRenderer(EntityRendererFactory.Context pContext) {
        super(pContext, new BoblingModel<>(pContext.getPart(ModModelLayerLocations.BOBLING)), 0.4F);
    }

    @Override
    public Identifier getTextureLocation(Bobling pEntity) {
        return RESOURCE_LOCATION;
    }

}
