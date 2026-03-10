package net.abraxator.moresnifferflowers.mixins;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.irisshaders.iris.gl.blending.AlphaTest;
import net.irisshaders.iris.gl.state.FogMode;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.shaderpack.loading.ProgramId;
import net.irisshaders.iris.shaderpack.programs.ProgramFallbackResolver;
import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import net.irisshaders.iris.targets.RenderTargets;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(IrisRenderingPipeline.class)
public interface IrisPipelineAccessor {

    @Invoker("createShader")
    ShaderInstance MSFCreateShader(String name, ProgramSource source, ProgramId programId, AlphaTest fallbackAlpha, VertexFormat vertexFormat, FogMode fogMode, boolean isIntensity, boolean isFullbright, boolean isGlint, boolean isText, boolean isIE);

    @Accessor("resolver")
    ProgramFallbackResolver MSFGetResolver();

    @Accessor("renderTargets")
    RenderTargets MSFGetrenderTargets();

    @Accessor("flippedAfterPrepare")
    ImmutableSet<Integer> MSFGetflippedAfterPrepare();

    @Accessor("flippedAfterTranslucent")
    ImmutableSet<Integer> MSFGetflippedAfterTranslucent();

    @Accessor("loadedShaders")
    Set<ShaderInstance> MSFGetloadedShaders();

}
