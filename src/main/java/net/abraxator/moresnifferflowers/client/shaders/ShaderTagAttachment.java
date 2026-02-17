package net.abraxator.moresnifferflowers.client.shaders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.ChunkPos;

import java.util.*;

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


   public static class ClientCache{
       public static final Map<ChunkPos, Map<BlockPos, RenderType>> CACHE = new HashMap<>();
   }
}
