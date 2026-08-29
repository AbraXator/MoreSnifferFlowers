package net.abraxator.moresnifferflowers.client.renderer.entity;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.CorruptedProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CorruptedProjectileRenderer extends CoolProjectileRenderer<CorruptedProjectile> {
    public static final ResourceLocation TEXTURE = MoreSnifferFlowers.loc("textures/entity/corrupted_projectile.png");

    public CorruptedProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CorruptedProjectile entity) {
        return TEXTURE;
    }
}
