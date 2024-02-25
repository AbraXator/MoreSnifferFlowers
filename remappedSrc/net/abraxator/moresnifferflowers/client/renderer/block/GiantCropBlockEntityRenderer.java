package net.abraxator.moresnifferflowers.client.renderer.block;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

public class GiantCropBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
	private final Map<Block, ModelPart> modelPartMap = new HashMap<>();
	private final ModelPart carrot;
	private final ModelPart potato;
	private final ModelPart netherwart;
	private final ModelPart beetroot;
	private final ModelPart wheat;

	public GiantCropBlockEntityRenderer(BlockEntityRendererFactory.Context pContext) {
		ModelPart carrotModelPart = pContext.getLayerModelPart(ModModelLayerLocations.GIANT_CARROT);
		this.carrot = carrotModelPart.getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_CARROT.get(), this.carrot);
		ModelPart potatoModelPart = pContext.getLayerModelPart(ModModelLayerLocations.GIANT_POTATO);
		this.potato = potatoModelPart.getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_POTATO.get(), this.potato);
		ModelPart netherwartModelPart = pContext.getLayerModelPart(ModModelLayerLocations.GIANT_NETHERWART);
		this.netherwart = netherwartModelPart.getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_NETHERWART.get(), this.netherwart);
		ModelPart beetrootModelPart = pContext.getLayerModelPart(ModModelLayerLocations.GIANT_BEETROOT);
		this.beetroot = beetrootModelPart.getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_BEETROOT.get(), this.beetroot);
		ModelPart wheatModelPart = pContext.getLayerModelPart(ModModelLayerLocations.GIANT_WHEAT);
		this.wheat = wheatModelPart.getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_WHEAT.get(), this.wheat);
	}

	@Override
	public void render(T pBlockEntity, float pPartialTick, MatrixStack pPoseStack, VertexConsumerProvider pBufferSource, int pPackedLight, int pPackedOverlay) {
		BlockState blockState = pBlockEntity.getCachedState();
		String path = blockState.getBlock().getTranslationKey().replace("block." + MoreSnifferFlowers.MOD_ID + ".", "");
		SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, MoreSnifferFlowers.loc("block/" + path));
		VertexConsumer vertexConsumer = TEXTURE.getVertexConsumer(pBufferSource, RenderLayer::getEntityCutout);

		if(blockState.isIn(ModTags.ModBlockTags.GIANT_CROPS) && GiantCropBlock.isCenter(blockState)) {
			pPoseStack.push();
			pPoseStack.translate(0.5, 1.5, 0.5);
			pPoseStack.multiply(new Quaternionf().rotateX((float) (Math.PI)));
			modelPartMap.get(blockState.getBlock()).render(pPoseStack, vertexConsumer, pPackedLight, pPackedOverlay);
			pPoseStack.pop();
		}
	}
}