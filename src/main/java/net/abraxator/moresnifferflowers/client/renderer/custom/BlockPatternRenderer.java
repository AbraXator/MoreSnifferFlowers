package net.abraxator.moresnifferflowers.client.renderer.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.BlockPatternCapability;
import net.abraxator.moresnifferflowers.client.ClientRegistration;
import net.abraxator.moresnifferflowers.components.BlockPattern;
import net.abraxator.moresnifferflowers.init.ModDataAttachments;
import net.abraxator.moresnifferflowers.init.config.ModClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.lighting.QuadLighter;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Stream;

public class BlockPatternRenderer {
    public static final BlockPatternRenderer.CameraTracker CAMERA_TRACKER = new BlockPatternRenderer.CameraTracker();

    private boolean dirty = true;
    private final List<RenderQuad> cachedQuads = new ArrayList<>();

    public static void cacheAndRender(Frustum frustum, Camera camera, Level level, Minecraft minecraft, PoseStack poseStack) {
        double camX = camera.getPosition().x;
        double camY = camera.getPosition().y;
        double camZ = camera.getPosition().z;

        BlockPatternRenderer BUFFER_MANAGER = ClientRegistration.getBlockPatternRenderer();
        if (level == null || minecraft.player == null) return;

        if (CAMERA_TRACKER.hasMoved(camera)) {
            BUFFER_MANAGER.markDirty();
        }
        int chunkRenderDistance = Math.min(ModClientConfig.getBlockPatternRenderDistance(), minecraft.options.getEffectiveRenderDistance());

        poseStack.pushPose();
        poseStack.translate(-camX, -camY, -camZ);

        List<LevelChunk> levelChunks = new ArrayList<>();

        ChunkPos playerChunkPos = minecraft.player.chunkPosition();
        for (int x = -chunkRenderDistance; x < chunkRenderDistance ; x++) {
            for (int z = -chunkRenderDistance; z < chunkRenderDistance ; z++) {
                levelChunks.add(level.getChunk(x + playerChunkPos.x,z + playerChunkPos.z));
            }
        }

        BUFFER_MANAGER.cachePatterns(level, camX, camY, camZ, levelChunks, frustum);
        BUFFER_MANAGER.render(poseStack, Minecraft.getInstance().renderBuffers().bufferSource());

        poseStack.popPose();
    }

    public void markDirty() {
        this.dirty = true;
    }

    public void cachePatterns(Level level, double camX, double camY, double camZ, List<LevelChunk> levelChunks, Frustum frustum) {
        if (!dirty) return;
        dirty = false;
        cachedQuads.clear();

        frustum.prepare(camX, camY, camZ);

        for (LevelChunk chunk : levelChunks) {
            BlockPatternCapability storage = chunk.getData(ModDataAttachments.BLOCK_PATTERNS);

                Stream<BlockPos> patternPositions = storage.getPatterns().keySet().stream();

                patternPositions.forEach(pos -> {
                    BlockPatternCapability.PatternData data = storage.getPattern(pos);

                    if (data != null && frustum.isVisible(new AABB(pos))) {

                        ResourceLocation resourceLocation = MoreSnifferFlowers.loc("block/block_pattern/" + BlockPattern.fromId(data.patternId()).getSerializedName());
                        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(resourceLocation);
                        BlockState state = level.getBlockState(pos);

                        for (Direction dir : Direction.values()) {
                            if (state.isFaceSturdy(level, pos, dir) && (!level.getBlockState(pos.relative(dir)).isFaceSturdy(level, pos.relative(dir), dir.getOpposite()) || !level.getBlockState(pos.relative(dir)).canOcclude() )) {
                                float[] brightness = new float[]{1,1,1,1};
                                int[] lightmap;
                                boolean smoothLighting = ModClientConfig.BLOCK_PATTERN_SMOOTH_LIGHTING.get();

                                if (smoothLighting) {
                                    ModelBlockRenderer.AmbientOcclusionFace aoFace = new ModelBlockRenderer.AmbientOcclusionFace();
                                aoFace.calculate(level, state, pos.relative(dir), dir, new float[Direction.values().length * 2], new BitSet(3), true);
                                    brightness = aoFace.brightness;
                                    lightmap = aoFace.lightmap;

                                } else {
                                    int packed = getPackedLight(level, pos.relative(dir));
                                    if (data.isGlowing()) packed = LightTexture.FULL_BRIGHT;
                                    lightmap = new int[]{packed,packed,packed,packed};
                                }

                                cachedQuads.add(RenderQuad.create(pos, dir, data.color(), sprite, smoothLighting, data.direction(), data.isGlowing(), brightness, lightmap));
                            }
                        }
                    }
                });
        }
    }


    public record RenderQuad(BlockPos pos, Direction direction, int color, TextureAtlasSprite sprite, boolean smoothLighting, Direction rotation, boolean isGlowing, float[] brightness, int[] lightmap) {

        public static RenderQuad create(BlockPos pos, Direction face, int color, TextureAtlasSprite sprite, boolean smoothLighting, Direction rotation, boolean isGlowing, float[] brightness, int[] lightmap) {
            return new RenderQuad(pos.immutable(), face, color, sprite, smoothLighting, rotation, isGlowing, brightness, lightmap);
        }

        private void render(PoseStack poseStack, VertexConsumer buffer) {
            poseStack.pushPose();
            poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
            translateToFace(poseStack, direction, pos);

            Vec3i n = direction.getNormal();
            float nx = n.getX(), ny = n.getY(), nz = n.getZ();

            int rgb = color;
            float r = ((rgb >> 16) & 0xFF) / 255f;
            float g = ((rgb >> 8) & 0xFF) / 255f;
            float b = (rgb & 0xFF) / 255f;

            if (!smoothLighting) {
                float ao = QuadLighter.calculateShade(nx, ny, nz, false);

                if (!isGlowing) {
                    r *= ao;
                    g *= ao;
                    b *= ao;
                }

            }

            Matrix4f pose = poseStack.last().pose();

            /*  if (direction == Direction.UP) {
                   r = 1F;
                   g = 1F;
                   b = 1F;
               }*/

            float u0 = sprite.getU1();
            float u1 = sprite.getU0();
            float u2 = sprite.getU0();
            float u3 = sprite.getU1();

            float v0 = sprite.getV1();
            float v1 = sprite.getV1();
            float v2 = sprite.getV0();
            float v3 = sprite.getV0();

            if (rotation == Direction.EAST) {
                u0 = sprite.getU0();
                u1 = sprite.getU0();
                u2 = sprite.getU1();
                u3 = sprite.getU1();
                v0 = sprite.getV0();
                v1 = sprite.getV1();
                v2 = sprite.getV1();
                v3 = sprite.getV0();
            }
            if (rotation == Direction.SOUTH) {
                u0 = sprite.getU0();
                u1 = sprite.getU1();
                u2 = sprite.getU1();
                u3 = sprite.getU0();
                v0 = sprite.getV0();
                v1 = sprite.getV0();
                v2 = sprite.getV1();
                v3 = sprite.getV1();
            }
            if (rotation == Direction.WEST) {
                u0 = sprite.getU1();
                u1 = sprite.getU1();
                u2 = sprite.getU0();
                u3 = sprite.getU0();
                v0 = sprite.getV1();
                v1 = sprite.getV0();
                v2 = sprite.getV0();
                v3 = sprite.getV1();
            }

            float brightness0 = brightness[0];
            float brightness1 = brightness[1];
            float brightness2 = brightness[2];
            float brightness3 = brightness[3];

            if (isGlowing) {
                brightness0 = 1f;
                brightness1 = 1f;
                brightness2 = 1f;
                brightness3 = 1f;

                lightmap[0] = LightTexture.FULL_BRIGHT;
                lightmap[1] = LightTexture.FULL_BRIGHT;
                lightmap[2] = LightTexture.FULL_BRIGHT;
                lightmap[3] = LightTexture.FULL_BRIGHT;
            }

            buffer.addVertex(pose, 1, 0, 0).setColor(r * brightness0, g * brightness0, b * brightness0, 1f).setUv(u1, v2).setLight(lightmap[0]).setNormal(poseStack.last(), nx, ny, nz);
            buffer.addVertex(pose, 1, 0, 1).setColor(r * brightness1, g * brightness1, b * brightness1, 1f).setUv(u2, v1).setLight(lightmap[1]).setNormal(poseStack.last(), nx, ny, nz);
            buffer.addVertex(pose, 0, 0, 1).setColor(r * brightness2, g * brightness2, b * brightness2, 1f).setUv(u3, v0).setLight(lightmap[2]).setNormal(poseStack.last(), nx, ny, nz);
            buffer.addVertex(pose, 0, 0, 0).setColor(r * brightness3, g * brightness3, b * brightness3, 1f).setUv(u0, v3).setLight(lightmap[3]).setNormal(poseStack.last(), nx, ny, nz);

            poseStack.popPose();
        }
    }

    public void render(PoseStack stack, MultiBufferSource.BufferSource bufferSource) {
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.cutoutMipped());

        if (ModClientConfig.CLIENT_CONFIG.isLoaded() && ModClientConfig.BLOCK_PATTERN_TRANSPARENCY.get()){
            buffer = bufferSource.getBuffer(RenderType.translucent());
        }

        for (RenderQuad quad : cachedQuads) {
            quad.render(stack, buffer);
        }

        bufferSource.endLastBatch();
    }

    private static void translateToFace(PoseStack stack, Direction face, BlockPos pos) {
        double configOffset = 0.001f;
        float distance = (float) (configOffset * (Math.abs((pos.getX() + pos.getY() + pos.getZ()) % 4) + 1));
        float scale = distance*2;
        switch (face) {
            case UP -> {
              //  stack.mulPose(Axis.XP.rotationDegrees(90));
              //  stack.translate(0, 0, -1 - distance);
                stack.translate(1, 1 + distance, 0);
                stack.mulPose(Axis.YP.rotationDegrees(180));
                stack.mulPose(Axis.XP.rotationDegrees(180));
                stack.scale(1+scale, 1, 1+scale);
                stack.translate(-distance, 0, -distance);
            }
            case DOWN -> {
                stack.translate(1, 0 - distance, 1);
                stack.mulPose(Axis.YP.rotationDegrees(180));
                stack.scale(1+scale, 1, 1+scale);
                stack.translate(-distance, 0, -distance);

            }
            case NORTH -> {
                stack.translate(0, 1, -0 - distance);
                stack.mulPose(Axis.XP.rotationDegrees(90));
                stack.scale(1+scale, 1, 1+scale);
                stack.translate(-distance,0, -distance);

            }
            case SOUTH -> {
                stack.translate(1, 1, 1 + distance);
                stack.mulPose(Axis.XN.rotationDegrees(90));
                stack.mulPose(Axis.YP.rotationDegrees(180));
                stack.scale(1+scale, 1, 1+scale);
                stack.translate(-distance,0, -distance);
            }
            case WEST -> {
                stack.translate(0 - distance, 1, 1);
                stack.mulPose(Axis.XP.rotationDegrees(90));
                stack.mulPose(Axis.ZP.rotationDegrees(-90));
                stack.scale(1+scale, 1, 1+scale);
                stack.translate(-distance,0, -distance);

            }
            case EAST -> {
                stack.translate(1 + distance, 1, 0);
                stack.mulPose(Axis.XP.rotationDegrees(90));
                stack.mulPose(Axis.ZP.rotationDegrees(90));
                stack.scale(1+scale, 1, 1+scale);
                stack.translate(-distance,0, -distance);

            }
        }
    }

    public static int getPackedLight(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }

    public static class CameraTracker {
        private Vec3 lastPosition = Vec3.ZERO;
        private float lastYaw = 0f;
        private float lastPitch = 0f;

        private static final double MOVE_THRESHOLD = 0.01f; // blocks
        private static final float ROTATE_THRESHOLD = 0.2f; // degrees

        public boolean hasMoved(Camera camera) {
            Vec3 currentPos = camera.getPosition();
            float yaw = camera.getYRot();
            float pitch = camera.getXRot();

            double dx = currentPos.x - lastPosition.x;
            double dy = currentPos.y - lastPosition.y;
            double dz = currentPos.z - lastPosition.z;

            double distSq = dx * dx + dy * dy + dz * dz;
            float deltaYaw = Math.abs(yaw - lastYaw);
            float deltaPitch = Math.abs(pitch - lastPitch);

            update(camera);

            return distSq > MOVE_THRESHOLD * MOVE_THRESHOLD ||
                    deltaYaw > ROTATE_THRESHOLD ||
                    deltaPitch > ROTATE_THRESHOLD;
        }

        public void update(Camera camera) {
            this.lastPosition = camera.getPosition();
            this.lastYaw = camera.getYRot();
            this.lastPitch = camera.getXRot();
        }
    }
}
