package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.abraxator.moresnifferflowers.blockentities.DyespriaPlantBlockEntity;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DyespriaPlantBlockEntityRenderer implements BlockEntityRenderer<DyespriaPlantBlockEntity> {
    private final EntityRenderDispatcher entityRenderDispatcher;
    
    public DyespriaPlantBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        entityRenderDispatcher = pContext.getEntityRenderer();
    }

    @Override
    public void render(DyespriaPlantBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        if(pBlockEntity.getBlockState().getValue(ModStateProperties.AGE_3) != 3 && pBlockEntity.dye.isEmpty()) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        DyeItem dyeItem = DyeItem.byColor(pBlockEntity.getBlockState().getValue(ModStateProperties.COLOR));
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.9375, 0.5);
        pPoseStack.mulPose(entityRenderDispatcher.cameraOrientation());
        pPoseStack.scale(0.35F, 0.35F, 0.35F);
        itemRenderer.renderStatic(new ItemStack(dyeItem), ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pBlockEntity.getLevel(), ((int) pBlockEntity.getBlockPos().asLong()));
        pPoseStack.popPose();
    }
}
