package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;

public class ShaderTagRegistry {
    public static final TagKey<Block> CUSTOM_RENDER = TagKey.create(Registries.BLOCK, MoreSnifferFlowers.loc("custom_render"));

    private static final Map<TagKey<Block>, RenderType> RENDER_MAP = new HashMap<>();


    public static Map<TagKey<Block>, RenderType> getRenderMap(){
        return new HashMap<>(RENDER_MAP);
    }

    public static void register(TagKey<Block> tag, RenderType renderType){
        RENDER_MAP.put(tag, renderType);
    }

    public static void registerBuiltIn(){

    }
}
