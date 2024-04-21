package net.abraxator.moresnifferflowers;


import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class MoreSnifferFlowersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ModBlocks.DAWNBERRY_VINE,
                ModBlocks.AMBUSH,
                ModBlocks.CAULORFLOWER,
                ModBlocks.BONMEELIA,
                ModBlocks.GIANT_BEETROOT,
                ModBlocks.GIANT_CARROT,
                ModBlocks.GIANT_NETHERWART,
                ModBlocks.GIANT_POTATO,
                ModBlocks.GIANT_WHEAT,
                ModBlocks.MORE_SNIFFER_FLOWER);

    }
}
