package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.client.RenderUtils;
import net.abraxator.moresnifferflowers.init.ModDataAttachments;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.*;
import java.util.stream.Collectors;

public record ShaderTagAttachment(HashSet<BlockPos> positions, boolean isDirty) {

    public static final Codec<ShaderTagAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.listOf().xmap(HashSet::new, ArrayList::new).fieldOf("pos").forGetter(ShaderTagAttachment::positions),
            Codec.BOOL.fieldOf("dirty").forGetter(ShaderTagAttachment::isDirty)

    ).apply(instance, ShaderTagAttachment::new));

    public static final StreamCodec<FriendlyByteBuf, ShaderTagAttachment> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()).map(HashSet::new, ArrayList::new), ShaderTagAttachment::positions,
            ByteBufCodecs.BOOL, ShaderTagAttachment::isDirty,

            ShaderTagAttachment::new
    );

    public ShaderTagAttachment(HashSet<BlockPos> positions) {
        this(positions, true);
    }

    public ShaderTagAttachment setDirty(boolean isDirty) {
        return new ShaderTagAttachment(this.positions, isDirty);
    }

    public static class ClientCache {
        private static final Map<ShaderTagRegistry.RenderContext, Set<BlockPos>> CACHE = new HashMap<>();

        public static void generateIfDirty() {
            for (LevelChunk chunk : RenderUtils.getVisibleChunks()) {
                boolean isDirty = chunk.getData(ModDataAttachments.SHADER_BLOCKS).isDirty();
                if (isDirty) {
                    generateChunk(chunk);
                }
            }
        }

        private static void generateChunk(LevelChunk chunk) {
            Map<BlockPos, Set<ShaderTagRegistry.RenderContext>> posMap = new HashMap<>();

            ShaderTagAttachment data = chunk.getData(ModDataAttachments.SHADER_BLOCKS);
            for (BlockPos pos : data.positions) {
                BlockState state = chunk.getBlockState(pos);

                var set = ShaderTagRegistry.getRenderMap().entrySet().stream()
                        .filter(entry -> state.is(entry.getKey()))
                        .map(Map.Entry::getValue)
                        .collect(Collectors.toSet());

                posMap.put(pos, set);
            }

            for (Map.Entry<BlockPos, Set<ShaderTagRegistry.RenderContext>> entry : posMap.entrySet()) {
                for (ShaderTagRegistry.RenderContext context : entry.getValue()) {
                    CACHE.computeIfAbsent(context, ctx -> new HashSet<>()).add(entry.getKey());
                }
            }

            chunk.setData(ModDataAttachments.SHADER_BLOCKS, data.setDirty(false));
        }

        public static Map<ShaderTagRegistry.RenderContext, Set<BlockPos>> get() {
            generateIfDirty();

            return CACHE;
        }
    }
}
