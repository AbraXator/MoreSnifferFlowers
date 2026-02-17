package net.abraxator.moresnifferflowers.client.shaders;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.HashMap;
import java.util.Map;

public class ShaderTagRegistry {
    public static final TagKey<Block> CUSTOM_RENDER = TagKey.create(Registries.BLOCK, MoreSnifferFlowers.loc("custom_render"));
    public static final TagKey<Block> NO_RENDERING = TagKey.create(Registries.BLOCK, MoreSnifferFlowers.loc("no_rendering"));

    private static final Map<TagKey<Block>, RenderContext> RENDER_MAP = new HashMap<>();

    public static Map<TagKey<Block>, RenderContext> getRenderMap(){
        return new HashMap<>(RENDER_MAP);
    }

    public static void register(TagKey<Block> tag, RenderContext renderContext){
        RENDER_MAP.put(tag, renderContext);
    }

    public static void registerBuiltIn(){
        register(ModRenderTypes.CRUMBLING_RENDER_TAG, new RenderContext(ModRenderTypes.CRUMBLING_BLOCK));

    }

    public record RenderContext(RenderType renderType, RenderLevelStageEvent.Stage stage) {

        public RenderContext(RenderType renderType) {
            this(renderType, RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RenderContext(RenderType type, RenderLevelStageEvent.Stage stage1))) return false;
            return this.stage == stage1 && type == this.renderType;
        }
    }
}
