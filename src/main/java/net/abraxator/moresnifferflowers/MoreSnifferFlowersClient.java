package net.abraxator.moresnifferflowers;


import net.abraxator.moresnifferflowers.client.renderer.block.AmbushBlockEntityRenderer;
import net.abraxator.moresnifferflowers.client.renderer.block.GiantCropBlockEntityRenderer;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class MoreSnifferFlowersClient implements ModInitializer {
    @Override
    public void onInitialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DAWNBERRY_VINE, RenderLayer.getCutout());

        BlockEntityRendererFactories.register(ModBlockEntities.AMBUSH_BLOCK_ENTITY, AmbushBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.GIANT_CROP_BLOCK_ENTITY, GiantCropBlockEntityRenderer::new);
    }
}
