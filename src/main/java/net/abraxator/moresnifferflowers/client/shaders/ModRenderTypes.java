package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.mixins.IrisPipelineAccessor;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.gl.blending.AlphaTest;
import net.irisshaders.iris.gl.blending.AlphaTests;
import net.irisshaders.iris.gl.framebuffer.GlFramebuffer;
import net.irisshaders.iris.gl.state.FogMode;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.pipeline.programs.FallbackShader;
import net.irisshaders.iris.pipeline.programs.ShaderCreator;
import net.irisshaders.iris.pipeline.programs.ShaderKey;
import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import net.irisshaders.iris.vertices.IrisVertexFormats;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, value = Dist.CLIENT)
public class ModRenderTypes extends RenderType {
    public ModRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    static ShaderInstance goldItemShader;
    static ShaderInstance goldItemShaderExtended;


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
            CompositeState.builder()
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
            CompositeState.builder()
                    .setLightmapState(LIGHTMAP)
                    .setShaderState(new ShaderStateShard(ModRenderTypes::getGoldItemShader))
                    .setTextureState(BLOCK_SHEET_MIPPED)
                    .createCompositeState(true)
    );
/*

    private static @NotNull ShaderInstance getGetGoldItemShaderThingy() {
        if (Iris.getPipelineManager().getPipeline().isPresent() && Iris.getPipelineManager().getPipeline().get() instanceof IrisRenderingPipeline pipeline) {
            IrisPipelineAccessor accessor = (IrisPipelineAccessor) pipeline;
            Optional<ProgramSource> resolve = accessor.MSFGetResolver().resolve(MoreSnifferFlowers.goldProgramId);
            if (goldItemShaderExtended == null) {
                resolve.ifPresent(programSource -> goldItemShaderExtended = accessor.MSFCreateShader("moresnifferflowers:gold_item", programSource, MoreSnifferFlowers.goldProgramId,
                        AlphaTests.OFF, IrisVertexFormats.TERRAIN, FogMode.PER_VERTEX, false,
                        false, false, false, false));

                try {
                    goldItemShaderExtended = createFallbackShader(pipeline, "moresnifferflowers:gold_item");
                } catch (IOException e) {
                    MoreSnifferFlowers.LOGGER.error(e.toString());
                }
            }

            return goldItemShaderExtended;
        }

        return goldItemShader;
    }

    private static ShaderInstance createFallbackShader(IrisRenderingPipeline pipeline, String name) throws IOException {
        IrisPipelineAccessor accessor = (IrisPipelineAccessor) pipeline;
        GlFramebuffer beforeTranslucent = accessor.MSFGetrenderTargets().createGbufferFramebuffer(accessor.MSFGetflippedAfterPrepare(), new int[]{0});
        GlFramebuffer afterTranslucent = accessor.MSFGetrenderTargets().createGbufferFramebuffer(accessor.MSFGetflippedAfterTranslucent(), new int[]{0});

        FallbackShader shader = ShaderCreator.createFallback(name, beforeTranslucent, afterTranslucent,
                AlphaTests.OFF, IrisVertexFormats.TERRAIN, null, pipeline, FogMode.PER_VERTEX,
                false, false, false, false, false);

        accessor.MSFGetloadedShaders().add(shader);

        return shader;
    }
*/


    @SubscribeEvent
    static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MoreSnifferFlowers.loc("gold_item"), DefaultVertexFormat.BLOCK),  (shader) -> goldItemShader = shader);
    }

}
