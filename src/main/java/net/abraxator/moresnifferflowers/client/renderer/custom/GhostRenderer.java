package net.abraxator.moresnifferflowers.client.renderer.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.client.RenderUtils;
import net.abraxator.moresnifferflowers.components.RenderOffsetType;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GhostRenderer {
    public static List<GhostRenderer> RENDERERS = new ArrayList<>();

    protected BlockPos pos;
    protected int ticksRemaining;
    protected final int maxTicksRemaining;
    protected RenderOffsetType renderOffsetType = RenderOffsetType.SCALED;
    protected float red = 1f;
    protected float green = 1f;
    protected float blue = 1f;
    protected float alpha = 1f;

    protected float maxAlpha = 1;
    protected Integer fadeOutTicks = null;

    protected GhostRenderer(BlockPos pos, int ticksRemaining) {
        this.pos = pos;
        this.ticksRemaining = ticksRemaining;
        this.maxTicksRemaining = ticksRemaining;
    }

    public void addToRenderList() {
        maxAlpha = alpha;
        RENDERERS.add(this);
    }

    public static void renderAll(float partialTick, Frustum frustum, Camera camera, Level level, PoseStack poseStack){
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        poseStack.pushPose();
        RenderUtils.translateToCamera(poseStack, camera);

        List<GhostRenderer> renderers = new ArrayList<>(RENDERERS);
        renderers.forEach(renderer -> renderer.prepareAndRender(partialTick, frustum, camera, level, poseStack, buffer));

        poseStack.popPose();
    }

    public static void tickAll(){
        RENDERERS.removeIf(renderer -> {
            renderer.ticksRemaining--;
            return renderer.ticksRemaining < 0;
        });
    }

    public void prepareAndRender(float partialTick, Frustum frustum, Camera camera, Level level, PoseStack poseStack, MultiBufferSource.BufferSource buffer){
        poseStack.pushPose();
        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
        renderOffsetType.applyTransforms(poseStack);

        if (fadeOutTicks != null) doFadeOut(partialTick);

        render(partialTick, frustum, camera, level, poseStack, buffer);

        buffer.endLastBatch();
        poseStack.popPose();
    }

    private void doFadeOut(float partialTick) {
        if (ticksRemaining <= fadeOutTicks){
            float smoothTicks = (ticksRemaining - partialTick) /  fadeOutTicks;
            if (smoothTicks < 0) smoothTicks = 0;

            this.setARGB(red, green, blue, smoothTicks * maxAlpha);
        }
    }

    public abstract void render(float partialTick, Frustum frustum, Camera camera, Level level, PoseStack poseStack, MultiBufferSource.BufferSource buffer);

    public GhostRenderer setRenderOffsetType(RenderOffsetType renderOffsetType) {
        this.renderOffsetType = renderOffsetType;
        return this;
    }

    public GhostRenderer setARGB(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        return this;
    }

    public GhostRenderer enableFadeOut(int fadeOutTicks) {
        if (fadeOutTicks <= ticksRemaining){
            this.fadeOutTicks = fadeOutTicks;
        } else {
            MoreSnifferFlowers.LOGGER.error(this + " fadeOutTicks can't be larger than remaining ticks");
        }
        return this;
    }
}
