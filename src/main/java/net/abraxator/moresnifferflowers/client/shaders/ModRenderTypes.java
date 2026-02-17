package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModRenderTypes extends RenderType {
    public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    public static final TagKey<Block> CRUMBLING_RENDER_TAG = TagKey.create(Registries.BLOCK, MoreSnifferFlowers.loc("crumbling_render"));

    public static final RenderType CRUMBLING_BLOCK = create(
            MoreSnifferFlowers.loc("crumbling_block").toString(),
            DefaultVertexFormat.BLOCK,
            VertexFormat.Mode.QUADS,
            41943,
            true,
            false,
            RenderType.CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(RENDERTYPE_SOLID_SHADER)
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .setTransparencyState(CRUMBLING_TRANSPARENCY)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .createCompositeState(true)
    );

}
