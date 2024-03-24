package net.abraxator.moresnifferflowers.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.BaseCropressorBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.CropressorBlockEntity;
import net.abraxator.moresnifferflowers.client.model.ModModelLayerLocations;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CropressorBlockEntityRenderer implements BlockEntityRenderer<CropressorBlockEntity> {
    private static final Material TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, MoreSnifferFlowers.loc("block/cropressor"));
    private final ModelPart root;
    private final ModelPart pistons;
    private final ModelPart piston_1;
    private final ModelPart piston_2;
    private final ModelPart piston_3;

    public CropressorBlockEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        ModelPart modelPart = pContext.bakeLayer(ModModelLayerLocations.CROPRESSOR);
        this.root = modelPart.getChild("root");
        this.pistons = root.getChild("pistons");
        this.piston_1 = pistons.getChild("piston_1");
        this.piston_2 = pistons.getChild("piston_2");
        this.piston_3 = pistons.getChild("piston_3");
    }

    @Override
    public void render(CropressorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState blockState = pBlockEntity.getBlockState();
        VertexConsumer vertexConsumer = TEXTURE.buffer(pBufferSource, RenderType::entityCutout);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Direction direction = pBlockEntity.getBlockState().getValue(BaseCropressorBlock.FACING).getOpposite();
        Vec3 scale = switch (direction) {
            case NORTH -> new Vec3(0, 0, -1/40);
            case EAST -> new Vec3(1/40, 0, 0);
            case SOUTH -> new Vec3(0, 0, 1/40);
            default -> new Vec3(-1/40, 0, 0);
        };

        if(blockState.is(ModBlocks.CROPRESSOR_OUT.get()) && !pBlockEntity.canInteract()) {
            pPoseStack.pushPose();
            pPoseStack.translate(scale.x, 0, scale.z);
            itemRenderer.renderStatic(pBlockEntity.inv.get(0), ItemDisplayContext.FIXED, pPackedLight, pPackedOverlay, pPoseStack, pBufferSource, pBlockEntity.getLevel(), ((int) pBlockEntity.getBlockPos().asLong()));
            pPoseStack.popPose();
        }
    }
}
