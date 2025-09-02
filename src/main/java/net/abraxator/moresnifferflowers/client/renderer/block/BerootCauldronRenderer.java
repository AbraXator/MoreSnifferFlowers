package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.BerootCauldronBlockEntity;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.components.PreviewMode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.function.Function;

public class BerootCauldronRenderer<T extends BerootCauldronBlockEntity> implements BlockEntityRenderer<T>, MultiblockRender {
    private final ModelPart cauldron;
    private final ModelPart spoon;

    public BerootCauldronRenderer(BlockEntityRendererProvider.Context context) {
        this.cauldron = context.bakeLayer(ModModelLayerLocations.BEROOT_CAULDRON);
        this.spoon = context.bakeLayer(ModModelLayerLocations.BEROOT_SPOON);
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        final Material CAULDRON_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/beroot_cauldron"));
        final Material SPOON_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/beroot_spoon"));

        PreviewMode previewMode = blockEntity.previewMode;
        Function<ResourceLocation, RenderType> renderType = getRenderTypeFunction(previewMode);

        final VertexConsumer cauldron_consumer = CAULDRON_TEXTURE.buffer(buffer, renderType);
        final VertexConsumer spoon_consumer = SPOON_TEXTURE.buffer(buffer, renderType);
        final RandomSource randomSource = level().getRandom();
        final Direction direction = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);

        if(blockEntity.canRender()) {
            //CAULDRON
            poseStack.pushPose();
            poseStack.translate(1, 1.5, 0);
            poseStack.mulPose(Axis.XN.rotationDegrees(-180));
            rotate(poseStack, direction, false);
            render(cauldron, poseStack, cauldron_consumer, packedLight, packedOverlay, previewMode);
            poseStack.popPose();

            //SOUP
            poseStack.pushPose();
            poseStack.translate(1, 0.5, 0);
            poseStack.mulPose(Axis.XN.rotationDegrees(-180));
            rotate(poseStack, direction, false);
            PoseStack.Pose pose = poseStack.last();
            Matrix4f matrix4f = pose.pose();
            Matrix3f matrix3f = pose.normal();
            int soupCount = blockEntity.soupCount;
            float size = 1.5F;
            float halfSize = size / 2.0F;
            float minX = -halfSize;
            float maxX = halfSize;
            float minZ = -halfSize;
            float maxZ = halfSize;
            float r = (float) (blockEntity.color().x / 255);
            float g = (float) (blockEntity.color().y / 255);
            float b = (float) (blockEntity.color().z / 255);
            float y = -((float) 1 / 6 * soupCount);
            float soupScale =0.332f;

            if (soupCount > 0) {
                poseStack.scale(1 + soupScale, 1, 1 + soupScale);
                poseStack.translate(-soupScale * 0.565, 0, soupScale * 0.565);
                renderFace(matrix4f, pose, buffer.getBuffer(RenderType.cutoutMipped()),
                        r, g, b, 1F,
                        minX, maxX, y, minZ, maxZ, packedLight, blockEntity.isCrafted);
            }
            poseStack.popPose();

            //SPOON
            {
                float rot = -blockEntity.getSpoonRotation(partialTick);
                poseStack.pushPose();
                poseStack.translate(1, 1.5, 0);
                poseStack.mulPose(Axis.XN.rotationDegrees(-180));
                rotate(poseStack, direction, false);
                poseStack.mulPose((new Quaternionf()).rotationY((float) (rot * (Math.PI / 180))));
                render(spoon ,poseStack, spoon_consumer, packedLight, packedOverlay, previewMode);
                poseStack.popPose();
            }
            
            //ITEMS
            for (int i = 0; i < blockEntity.ingredients.getValidSize(); i++) {
                poseStack.pushPose();
                rotate(poseStack, direction, true);
                ItemStack itemStack = blockEntity.ingredients.get(i);
                float speed = (float) randomSource.nextIntBetweenInclusive(50, 100) / 100;
                float rot = (float) (blockEntity.getItemsRotation(partialTick) * ((i + 1) * 0.1));
                //poseStack.translate(i / 0.2 + 0.1, absoluteY, i / 0.2 + 0.1);
                float a = (float) (i * 0.05);
                poseStack.translate(0.8 + a, (float) -y + 0.51F, 0.0 - a);
                poseStack.mulPose(new Quaternionf().rotationY((float) (rot * (Math.PI / 180))));
                poseStack.translate(0.25, 0, 0.25);
                poseStack.mulPose(new Quaternionf().rotationY((float) ((rot * 0.2) * (Math.PI / 180))));
                //poseStack.translate(randomSource.nextFloat(), 0, randomSource.nextFloat());
                poseStack.scale(0.5F, 0.5F, 0.5F);
                Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, blockEntity.getLevel(), (int) (blockEntity.getBlockPos().asLong() + i));
                poseStack.popPose();
            }
        }
    }

    private void rotate(PoseStack poseStack, Direction direction, Boolean isItems){
        if (!isItems) {
            switch (direction) {
                case EAST -> poseStack.translate(0, 0, -1);
                case WEST -> poseStack.translate(-1, 0, 0);
                case SOUTH -> poseStack.translate(-1, 0, -1);
            }
        } else {
            switch (direction) {
                case EAST -> poseStack.translate(0, 0, 1);
                case WEST -> poseStack.translate(-1, 0, 0);
                case SOUTH -> poseStack.translate(-1, 0, 1);
            }
            poseStack.translate(0, 0, 0.1);
        }
        if (!isItems) poseStack.mulPose(direction.getRotation());
        if (!isItems) poseStack.mulPose(Axis.XN.rotationDegrees(90));
    }

    private void renderFace(Matrix4f pose, PoseStack.Pose normal, VertexConsumer consumer, float red, float green, float blue, float alpha, float x0, float x1, float y, float z0, float z1, int light, boolean isCrafted) {

        String name = isCrafted ? "beroot_soup1" : "beroot_soup";
        ResourceLocation resourceLocation = MoreSnifferFlowers.loc("block/" + name);
        TextureAtlasSprite sprite = Minecraft.getInstance().getModelManager().getAtlas(TextureAtlas.LOCATION_BLOCKS).getSprite(resourceLocation);

        consumer.addVertex(pose, x1, y, z0).setColor(red, green, blue, alpha).setUv(sprite.getU0(), sprite.getV0()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(normal, 0.0F, 1.0F, 0.0F);
        consumer.addVertex(pose, x1, y, z1).setColor(red, green, blue, alpha).setUv(sprite.getU0(), sprite.getV1()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(normal, 0.0F, 1.0F, 0.0F);
        consumer.addVertex(pose, x0, y, z1).setColor(red, green, blue, alpha).setUv(sprite.getU1(), sprite.getV1()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(normal, 0.0F, 1.0F, 0.0F);
        consumer.addVertex(pose, x0, y, z0).setColor(red, green, blue, alpha).setUv(sprite.getU1(), sprite.getV0()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(light).setNormal(normal, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return new AABB(blockEntity.center).inflate(1);
    }
}
