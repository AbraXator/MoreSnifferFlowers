package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.BondripiaBlockEntity;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.components.PreviewMode;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BondripiaBlockEntityRenderer<T extends BondripiaBlockEntity> implements BlockEntityRenderer<T>, MultiblockRender {
    private ModelPart model;
    private static final Material BONDRIPIA_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/bondripia"));
    private static final Material ACIDRIPIA_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/acidripia"));

    public BondripiaBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = context.bakeLayer(ModModelLayerLocations.BONDRIPIA);
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if(blockEntity.getBlockState().getValue(ModStateProperties.CENTER) && blockEntity.getBlockState().getValue(ModStateProperties.AGE_2) >= 2) {

            poseStack.translate(0.5, 1.5, 0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            PreviewMode previewMode = blockEntity.previewMode;

            VertexConsumer consumer = getConsumer(buffer, blockEntity, BONDRIPIA_TEXTURE, ACIDRIPIA_TEXTURE, ModBlocks.ACIDRIPIA.get());

            render(model, poseStack, consumer, packedLight, packedOverlay, previewMode);
        }
    }

    public int getViewDistance() {
        return 256;
    }

    @Override
    public @NotNull AABB getRenderBoundingBox(T blockEntity) {
        return new AABB(blockEntity.center).inflate(1);
    }
}
