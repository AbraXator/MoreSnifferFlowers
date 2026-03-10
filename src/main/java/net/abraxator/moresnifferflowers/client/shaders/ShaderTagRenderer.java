package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.abraxator.moresnifferflowers.client.RenderUtils;
import net.abraxator.moresnifferflowers.client.renderer.custom.BlockPatternRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static net.abraxator.moresnifferflowers.client.RenderUtils.*;

public class ShaderTagRenderer {
    public static ShaderTagRenderer INSTANCE = new ShaderTagRenderer();

    VertexBuffer vbo;

    public void render(PoseStack poseStack, Camera camera, RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) return;

        RenderType renderType = ModRenderTypes.RENDER_TYPE_GOLD;

        if (cacheIfDirty(poseStack, renderType)) {
            ShaderTagAttachment.ClientCache.clearDirty();

            poseStack.pushPose();

            //my own helper method
            translateToCamera(poseStack, camera);

            renderType.setupRenderState();

            Matrix4f viewMatrix = new Matrix4f(event.getModelViewMatrix()).mul(poseStack.last().pose());

            vbo.bind();
            vbo.drawWithShader(
                    viewMatrix,
                    event.getProjectionMatrix(),
                    Objects.requireNonNull(RenderSystem.getShader())
            );

            VertexBuffer.unbind();

            renderType.clearRenderState();

            poseStack.popPose();
        }
    }

    private boolean cacheIfDirty(PoseStack poseStack, RenderType renderType) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        assert level != null;

        if (vbo == null) {
            vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
        }

        if (ShaderTagAttachment.ClientCache.isDirty()) {
            poseStack.pushPose();

            // a class implementing VertexConsumer
            BufferBuilder vertexConsumer = Tesselator.getInstance().begin(renderType.mode, renderType.format);

            //iterate over all block positions
            for (BlockPos pos : ShaderTagAttachment.ClientCache.justGetAllBlocks()) {
                poseStack.pushPose();

                //my own helper method
                translateToPos(poseStack, pos);

                minecraft.getBlockRenderer().renderBatched(
                        level.getBlockState(pos), // the block to render
                        pos,
                        level,
                        poseStack,
                        vertexConsumer,
                        true, // hides faces that aren't visible for extra performance
                        level.random
                );
                poseStack.popPose();
            }

            MeshData meshData = vertexConsumer.build();
            if (meshData == null) {
                return false;
            }

            vbo.bind();
            vbo.upload(meshData);
            VertexBuffer.unbind();

            return true;
        }

        return true;
    }
}
