package net.abraxator.moresnifferflowers.client.renderer.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.blockentities.MultiBlockEntity;
import net.abraxator.moresnifferflowers.blocks.multiblock.PreviewableMultiblock;
import net.abraxator.moresnifferflowers.components.PreviewMode;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;

public class MultiblockPreviewRenderer {

    public static void renderMultiblockPreviews(DeltaTracker partialTick, Minecraft minecraft, Level level, Camera camera, PoseStack poseStack) {
        LocalPlayer player = minecraft.player;
        assert player != null;
        if (player.getMainHandItem().getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof PreviewableMultiblock multiBlock && blockItem.getBlock() instanceof EntityBlock block) {
            HitResult hitResult = minecraft.hitResult;

            if (hitResult instanceof BlockHitResult blockHitResult){
                boolean placeOnWater = false;

                if (blockItem instanceof PlaceOnWaterBlockItem) {
                    blockHitResult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
                    placeOnWater = level.isWaterAt(blockHitResult.getBlockPos());
                };

                Direction hitDirection = blockHitResult.getDirection();
                BlockPos hitPos = blockHitResult.getBlockPos();
                BlockPos pos =  hitPos.relative(hitDirection);

                BlockState state = multiBlock.getDefaultStateForPreviews(player.getDirection());
                BlockEntity entity = block.newBlockEntity(pos, state);

                boolean shouldShowPreview = level.getBlockState(pos).canBeReplaced() && (!level.getBlockState(hitPos).isAir() || placeOnWater);
                if (entity instanceof MultiBlockEntity multiBlockEntity && shouldShowPreview) {
                    entity.setLevel(level);

                    PreviewMode previewMode = multiBlock.canPlace(level, pos, state) ? PreviewMode.PREVIEW : PreviewMode.INVALID;
                    multiBlockEntity.previewMode = previewMode;

                    if (level.getBlockState(hitPos).canBeReplaced() && !placeOnWater) pos = pos.relative(hitDirection.getOpposite());

                    poseStack.pushPose();

                    double camX = camera.getPosition().x;
                    double camY = camera.getPosition().y;
                    double camZ = camera.getPosition().z;
                    poseStack.translate(pos.getX() - camX,pos.getY() - camY,pos.getZ() - camZ);


                    MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
                    BlockEntityRenderer<BlockEntity> entityRender = minecraft.getBlockEntityRenderDispatcher().getRenderer(entity);

                    if (entityRender != null) entityRender.render(entity, partialTick.getRealtimeDeltaTicks(), poseStack, buffer, 0xFFFFFF, OverlayTexture.NO_OVERLAY);

                    if (!multiBlock.skipJsonRendering()) renderJsonModels(minecraft, level, poseStack, multiBlock, pos, state, buffer, previewMode);

                    poseStack.popPose();

                }
            }
        }
    }

    private static void renderJsonModels(Minecraft minecraft, Level level, PoseStack poseStack, PreviewableMultiblock multiBlock, BlockPos originalPos, BlockState stateOriginal, MultiBufferSource.BufferSource buffer, PreviewMode previewMode) {
        BlockRenderDispatcher blockRenderer = minecraft.getBlockRenderer();
        poseStack.translate(0.0001,0.0001,0.0001);

        multiBlock.getPreviewStates(originalPos, stateOriginal).forEach(pair -> {

            BlockState state = pair.getB();
            BlockPos pos = pair.getA().immutable();

            if (!state.getRenderShape().equals(RenderShape.MODEL)) return;

            BlockPos offset = pos.subtract(originalPos).immutable();
            poseStack.translate(offset.getX(),  offset.getY(), offset.getZ());

            VertexConsumer tintedConsumer = new VertexConsumerWrapper(buffer.getBuffer(RenderType.TRANSLUCENT)) {
                @Override
                public void putBulkData(PoseStack.Pose pose, BakedQuad quad, float[] brightness, float red, float green, float blue, float alpha, int[] lightmap, int packedOverlay, boolean readAlpha){
                    super.putBulkData(pose, quad, brightness, red * previewMode.red, green * previewMode.green, blue * previewMode.blue, alpha * previewMode.alpha, lightmap, packedOverlay, readAlpha);
                }
            };

            blockRenderer.renderBatched(state, pos, level, poseStack, tintedConsumer, false, minecraft.level.getRandom());

            buffer.endLastBatch();

            poseStack.translate(-offset.getX(),  -offset.getY(), -offset.getZ());
        });
    }

}
