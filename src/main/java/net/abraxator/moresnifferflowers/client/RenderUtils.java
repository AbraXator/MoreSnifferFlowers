package net.abraxator.moresnifferflowers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RenderUtils {
    public static @NotNull List<LevelChunk> getVisibleChunks() {
        return getVisibleChunks(Minecraft.getInstance().options.getEffectiveRenderDistance());
    }

    public static @NotNull List<LevelChunk> getVisibleChunks(int chunkRenderDistance) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;

        assert level != null;
        assert minecraft.player != null;

        List<LevelChunk> levelChunks = new ArrayList<>();

        ChunkPos playerChunkPos = minecraft.player.chunkPosition();
        for (int x = -chunkRenderDistance; x < chunkRenderDistance; x++) {
            for (int z = -chunkRenderDistance; z < chunkRenderDistance; z++) {
                levelChunks.add(level.getChunk(x + playerChunkPos.x,z + playerChunkPos.z));
            }
        }
        return levelChunks;
    }

    public static void translateToCamera(PoseStack poseStack, Camera camera) {
        Vec3 pos = camera.getPosition();
        poseStack.translate(-pos.x(), -pos.y(), -pos.z());
    }

    public static void translateToPos(PoseStack poseStack, Vec3i pos){
        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
    }

    public static void translateToPos(PoseStack poseStack, Vec3 pos){
        poseStack.translate(pos.x(), pos.y(), pos.z());
    }


}
