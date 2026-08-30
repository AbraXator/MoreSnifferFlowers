package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.DyespriaPlantBlockEntity;
import net.abraxator.moresnifferflowers.client.MSFColorHandler;
import net.abraxator.moresnifferflowers.client.model.block.DyespriaModel;
import net.abraxator.moresnifferflowers.components.Colorable;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.init.MSFStateProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DyespriaPlantBlockEntityRenderer implements BlockEntityRenderer<DyespriaPlantBlockEntity> {
    private final EntityRenderDispatcher entityRenderDispatcher;
    private final ModelPart modelPart;

    public DyespriaPlantBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        entityRenderDispatcher = context.getEntityRenderer();
        modelPart = context.bakeLayer(DyespriaModel.DYESPRIA);
    }

    @Override
    public void render(DyespriaPlantBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        var isGrown = state.getValue(MSFStateProperties.AGE_3) >= 3;
        Dye dye = blockEntity.dye;
        var hasDye = !dye.isEmpty();

        if (isGrown){
            boolean isModdedDye = Colorable.isModdedDye(dye.color());
            boolean hasInvalidDye = dye.isEmpty() || isModdedDye;

            String colorName = hasInvalidDye ? "white" : dye.color().getName();
            Material TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/dyespria/dyespria_top_" + colorName));

            poseStack.pushPose();

            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.translate(0.5D, -1.5D, -0.5D);

            if (isRotated(blockEntity.getBlockPos()))
                poseStack.mulPose(Axis.YP.rotationDegrees(45));

            float r = 1f;
            float g = 1f;
            float b = 1f;

            if (isModdedDye){
             float[] rgb = MSFColorHandler.hexToRGB(dye.color().getTextColor());

             r = rgb[0];
             g = rgb[1];
             b = rgb[2];
            }

            modelPart.render(poseStack, TEXTURE.buffer(buffer, RenderType::entityCutout), packedLight, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1f, r, g, b));

            poseStack.popPose();
        }

        if(isGrown && hasDye && !state.getValue(MSFStateProperties.SHEARED)) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            DyeItem dyeItem = DyeItem.byColor(state.getValue(MSFStateProperties.COLOR));
            poseStack.pushPose();
            poseStack.translate(0.5, 0.9375, 0.5);
            poseStack.mulPose(entityRenderDispatcher.cameraOrientation());
            poseStack.scale(0.35F, 0.35F, 0.35F);
            itemRenderer.renderStatic(new ItemStack(dyeItem), ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, buffer, blockEntity.getLevel(), ((int) blockEntity.getBlockPos().asLong()));
            poseStack.popPose();
        }

    }

    public static boolean isRotated(BlockPos pos){
        long total = pos.getX() + pos.getY() + pos.getZ();
        return total % 2 == 0;
    }
}
