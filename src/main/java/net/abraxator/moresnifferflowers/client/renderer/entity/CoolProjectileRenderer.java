package net.abraxator.moresnifferflowers.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.client.model.entity.CorruptedProjectileModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public abstract class CoolProjectileRenderer<T extends Entity> extends EntityRenderer<T> {
    private final CorruptedProjectileModel model;

    protected CoolProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new CorruptedProjectileModel(context.bakeLayer(ModModelLayerLocations.CORRUPTED_PROJECTILE));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(projectileCameraCheck(entity, this.entityRenderDispatcher)) {
            projectileRendering(entity, partialTick, poseStack, buffer, packedLight, this.model, getTextureLocation(entity));
            super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
        }
    }

    public static void projectileRendering(Entity entity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, Model model, ResourceLocation location) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, entity.yRotO, entity.getYRot()) - 180F));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(pPartialTick, entity.xRotO, entity.getXRot())));
        pPoseStack.translate(0, -0.5, 0);
        float scale = 0.6F;
        pPoseStack.scale(scale, scale, scale);
        model.renderToBuffer(
                pPoseStack,
                pBufferSource.getBuffer(model.renderType(location)),
                pPackedLight,
                OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();
    }

    public static boolean projectileCameraCheck(Entity entity, EntityRenderDispatcher renderer ) {
        return entity.tickCount >= 2 || !(renderer.camera.getEntity().distanceToSqr(entity) < 12.25);
    }
}
