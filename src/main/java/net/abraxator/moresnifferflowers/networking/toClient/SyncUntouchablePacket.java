package net.abraxator.moresnifferflowers.networking.toClient;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.MSFPacket;
import net.abraxator.moresnifferflowers.networking.MSFToClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record SyncUntouchablePacket() implements MSFToClientPacket {
    public static final CustomPacketPayload.Type<SyncUntouchablePacket> TYPE = MSFPacket.makeType("sync_untouchable", SyncUntouchablePacket.class);
    public static final StreamCodec<FriendlyByteBuf, SyncUntouchablePacket> STREAM_CODEC = StreamCodec.of(
            (buf, pkt) -> {},
            buf -> new SyncUntouchablePacket());

    @Override
    public void handleClientPacket(Player player, Level level) {
        player.getData(MSFDataAttachments.UNTOUCHABLE).onAttacked();
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
