package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.client.RenderUtils;
import net.abraxator.moresnifferflowers.client.renderer.custom.BlockPatternRenderer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.Map;
import java.util.Set;

import static net.abraxator.moresnifferflowers.client.RenderUtils.*;

public class ShaderTagRenderer {

    public static void render(PoseStack poseStack, Camera camera, RenderLevelStageEvent.Stage stage) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
        assert level != null;

        poseStack.pushPose();
        translateToCamera(poseStack, camera);
        poseStack.translate(0.01, 0.01, 0.01);

        for (Map.Entry<ShaderTagRegistry.RenderContext, Set<BlockPos>> entry : ShaderTagAttachment.ClientCache.get().entrySet()) {
            ShaderTagRegistry.RenderContext context = entry.getKey();
            Set<BlockPos> set = entry.getValue();

            if (context.stage() != stage) continue;
            VertexConsumer vertexConsumer = bufferSource.getBuffer(context.renderType());

            for (BlockPos pos : set) {
                poseStack.pushPose();
                translateToPos(poseStack, pos);

                minecraft.getBlockRenderer().renderBatched(level.getBlockState(pos), pos, level, poseStack, vertexConsumer, true, level.random);

                poseStack.popPose();
            }

            bufferSource.endLastBatch();
        }

        poseStack.popPose();
    }
}
