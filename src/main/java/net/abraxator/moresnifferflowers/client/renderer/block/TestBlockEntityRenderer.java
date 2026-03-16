package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.abraxator.moresnifferflowers.blockentities.TestBlockEntity;
import net.abraxator.moresnifferflowers.client.shaders.ModRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.Blocks;

public class TestBlockEntityRenderer<T extends TestBlockEntity> implements BlockEntityRenderer<T> {
    public TestBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(TestBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getLevel() != null) {
            Minecraft.getInstance().getBlockRenderer().renderBatched(
                    Blocks.OAK_WOOD.defaultBlockState(),
                    blockEntity.getBlockPos(),
                    blockEntity.getLevel(),
                    poseStack,
                    bufferSource.getBuffer(ModRenderTypes.RENDER_TYPE_GOLD),
                    true,
                    blockEntity.getLevel().random
            );

            bufferSource.getBuffer(RenderType.cutoutMipped());
        }
    }

    @Override
    public boolean shouldRenderOffScreen(T blockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 1000;
    }
}
