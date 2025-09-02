package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.components.PreviewMode;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.pipeline.VertexConsumerWrapper;
import org.joml.Quaternionf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GiantCropBlockEntityRenderer<T extends GiantCropBlockEntity> implements BlockEntityRenderer<T>, MultiblockRender {
	private final Map<Block, ModelPart> modelPartMap = new HashMap<>();


    public GiantCropBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart carrot = context.bakeLayer(ModModelLayerLocations.GIANT_CARROT).getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_CARROT.get(), carrot);
        ModelPart potato = context.bakeLayer(ModModelLayerLocations.GIANT_POTATO).getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_POTATO.get(), potato);
        ModelPart netherwart = context.bakeLayer(ModModelLayerLocations.GIANT_NETHERWART).getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_NETHERWART.get(), netherwart);
        ModelPart beetroot = context.bakeLayer(ModModelLayerLocations.GIANT_BEETROOT).getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_BEETROOT.get(), beetroot);
        ModelPart wheat = context.bakeLayer(ModModelLayerLocations.GIANT_WHEAT).getChild("root");
		this.modelPartMap.put(ModBlocks.GIANT_WHEAT.get(), wheat);

        ModelPart onion = context.bakeLayer(ModModelLayerLocations.GIANT_ONION).getChild("root");
        this.modelPartMap.put(ModBlocks.GIANT_ONION.get(), onion);
        ModelPart tomato = context.bakeLayer(ModModelLayerLocations.GIANT_TOMATO).getChild("root");
        this.modelPartMap.put(ModBlocks.GIANT_TOMATO.get(), tomato);
        ModelPart cabbage = context.bakeLayer(ModModelLayerLocations.GIANT_CABBAGE).getChild("root");
        this.modelPartMap.put(ModBlocks.GIANT_CABBAGE.get(), cabbage);
        ModelPart rice = context.bakeLayer(ModModelLayerLocations.GIANT_RICE).getChild("root");
        this.modelPartMap.put(ModBlocks.GIANT_RICE.get(), rice);

    }

	@Override
	public void render(GiantCropBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
		BlockState blockState = blockEntity.getBlockState();
		String path = blockState.getBlock().getDescriptionId().replace("block." + MoreSnifferFlowers.MOD_ID + ".", "");
		Material TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/" + path));

		PreviewMode previewMode = blockEntity.previewMode;
		Function<ResourceLocation, RenderType> renderType = getRenderTypeFunction(previewMode);
		VertexConsumer vertexConsumer = TEXTURE.buffer(buffer, renderType);

		double growProgress = previewMode.equals(PreviewMode.PLACED) ? blockEntity.growProgress : 1;
		float coolPartialTick = (growProgress < 1 && blockState.is(ModTags.ModBlockTags.GIANT_CROPS) && blockState.getValue(ModStateProperties.CENTER)) ? partialTick : 0;
		float coolGrowProgress = level().getGameTime() - blockEntity.staticGameTime;

		if(growProgress > 0 && blockState.is(ModTags.ModBlockTags.GIANT_CROPS) && blockState.getValue(ModStateProperties.CENTER)) {
			float yCord = 0.5F;
			float yScale = 1;

			if (!previewMode.equals(PreviewMode.PLACED)) yCord++;

			if(growProgress < 1) {
				yCord = (coolGrowProgress + coolPartialTick) / 4 - 2;
				yScale = Mth.lerp((coolGrowProgress + coolPartialTick) / 10, 0, 1);
			}

			poseStack.pushPose();
			poseStack.translate(0.5, yCord, 0.5);
			poseStack.scale(1, yScale, 1);
			poseStack.mulPose(new Quaternionf().rotateX((float) (Math.PI)));

            if (blockState.is(ModTags.ModBlockTags.NO_SHADING)) {
                vertexConsumer = new VertexConsumerWrapper(vertexConsumer) {
                    @Override
                    public VertexConsumer setNormal(float x, float y, float z) {
                        return super.setNormal(1, 1, 1);
                    }
                };
            }

            render(modelPartMap.get(blockState.getBlock()), poseStack, vertexConsumer, packedLight, packedOverlay, blockEntity.previewMode);

			poseStack.popPose();
		}
	}

	@Override
	public int getViewDistance() {
		return 256;
	}

	@Override
	public AABB getRenderBoundingBox(T blockEntity) {
		return new AABB(blockEntity.center).inflate(1.1);
	}
}