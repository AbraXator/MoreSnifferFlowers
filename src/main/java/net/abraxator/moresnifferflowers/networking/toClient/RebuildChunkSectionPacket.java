package net.abraxator.moresnifferflowers.networking.toClient;

import com.mojang.serialization.Codec;
import net.abraxator.moresnifferflowers.networking.MSFPacket;
import net.abraxator.moresnifferflowers.networking.MSFToClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record RebuildChunkSectionPacket(int sectionX, int sectionY, int sectionZ) implements MSFToClientPacket {
    public static final CustomPacketPayload.Type<RebuildChunkSectionPacket> TYPE = MSFPacket.makeType("rebuild_chunk_section", RebuildChunkSectionPacket.class);

    public static final StreamCodec<RegistryFriendlyByteBuf, RebuildChunkSectionPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, RebuildChunkSectionPacket::sectionX,
        ByteBufCodecs.INT, RebuildChunkSectionPacket::sectionY,
        ByteBufCodecs.INT, RebuildChunkSectionPacket::sectionZ,
        RebuildChunkSectionPacket::new
);

    @Override
    public void handleClientPacket(Player player, Level level) {
        Proxy.handle(player, level, this);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    interface Proxy{
        static void handle(Player player, Level level, RebuildChunkSectionPacket packet) {
            Minecraft.getInstance().levelRenderer.setSectionDirty(packet.sectionX, packet.sectionY, packet.sectionZ);
        }
    }
}
