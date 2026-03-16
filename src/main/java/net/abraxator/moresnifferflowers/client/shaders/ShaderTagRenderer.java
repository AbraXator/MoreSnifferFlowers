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
import net.minecraft.world.level.block.Blocks;
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

    public void render(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) return;

        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        RenderType renderType = ModRenderTypes.RENDER_TYPE_GOLD;

        if (cacheIfDirty(poseStack, renderType)) {
            ShaderTagAttachment.ClientCache.clearDirty();

            poseStack.pushPose();

            translateToCamera(poseStack, camera);

            // Use all of this RenderTypes's setup calls
            renderType.setupRenderState();

            // Minecraft doesn't automatically compensate for camera rotation here,
            // requiring an additional calculation
            Matrix4f viewMatrix = new Matrix4f(
                    event.getModelViewMatrix()).mul(poseStack.last().pose()
            );

            // Tell OpenGL this VAO
            vbo.bind();

            // Draw the VBO's contents
            vbo.drawWithShader(
                    viewMatrix,
                    event.getProjectionMatrix(),
                    Objects.requireNonNull(RenderSystem.getShader())
            );

            // Unbind VAO to prevent further changes
            VertexBuffer.unbind();

            // Reset state to default
            renderType.clearRenderState();

            poseStack.popPose();
        }
    }

    private boolean cacheIfDirty(PoseStack poseStack, RenderType renderType) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        assert level != null;

        if (vbo == null) {
            // create a new VBO and its VAO
            // "STATIC" tells OpenGL not to expect many changes to its contents,
            // allowing for internal optimizations and better drawing speed
             vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
        }

        if (ShaderTagAttachment.ClientCache.isDirty()) {
            poseStack.pushPose();

            // a class implementing VertexConsumer
            BufferBuilder vertexConsumer = Tesselator.getInstance().begin(renderType.mode, renderType.format);

            //iterate over all block positions
            for (BlockPos pos : ShaderTagAttachment.ClientCache.justGetAllBlocks()) {
                poseStack.pushPose();

                translateToPos(poseStack, pos);

                minecraft.getBlockRenderer().renderBatched(
                        Blocks.OAK_WOOD.defaultBlockState(),
                        pos,
                        level,
                        poseStack,
                        vertexConsumer,
                        true,
                        level.random
                );
                poseStack.popPose();
            }

            //store all vertices
            MeshData meshData = vertexConsumer.build();
            if (meshData == null) {
                // don't render when the mesh is empty, as that causes a crash
                return false;
            }

            vbo.bind();

            // upload vertices and their stored information, updating the format
            vbo.upload(meshData);

            VertexBuffer.unbind();

            return true;
        }

        return true;
    }

    public static void simpleRender(RenderLevelStageEvent event) {
        // only run during this one stage
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) return;

        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level; // the world
        PoseStack poseStack = event.getPoseStack();
        Camera camera = event.getCamera();
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();

        poseStack.pushPose();

        //my own helper method
        translateToCamera(poseStack, camera);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(ModRenderTypes.RENDER_TYPE_GOLD);

        for (BlockPos pos : ShaderTagAttachment.ClientCache.justGetAllBlocks()) {
            poseStack.pushPose();

            //my own helper method
            translateToPos(poseStack, pos);

            minecraft.getBlockRenderer().renderBatched(
                    Blocks.OAK_WOOD.defaultBlockState(), // the block to render
                    pos,
                    level,
                    poseStack,
                    vertexConsumer,
                    true, // hides faces that aren't visible for extra performance
                    level.random
            );
            poseStack.popPose();
        }

        bufferSource.endLastBatch();

        poseStack.popPose();
    }

}
