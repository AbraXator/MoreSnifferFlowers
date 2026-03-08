package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;
import java.util.function.Function;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, value = Dist.CLIENT)
public class ModRenderTypes extends RenderType {
    public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    static ShaderInstance goldItemShader;

    public static ShaderInstance getGoldItemShader() {
        return goldItemShader;
    }


    public static final TagKey<Block> CRUMBLING_RENDER_TAG = TagKey.create(Registries.BLOCK, MoreSnifferFlowers.loc("crumbling_render"));
    public static final TagKey<Block> GOLD_RENDER_TAG = TagKey.create(Registries.BLOCK, MoreSnifferFlowers.loc("gold_render"));


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

    public static final RenderType RENDER_TYPE_GOLD = create(
            MoreSnifferFlowers.loc("gold_item").toString(),
            DefaultVertexFormat.BLOCK,
            VertexFormat.Mode.QUADS,
            1536,
            true,
            false,
            RenderType.CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(new RenderStateShard.ShaderStateShard(ModRenderTypes::getGoldItemShader))
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .createCompositeState(true)
    );

    @SubscribeEvent
    static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MoreSnifferFlowers.loc("gold_item"), DefaultVertexFormat.BLOCK),  (shader) -> goldItemShader = shader);
    }

}
