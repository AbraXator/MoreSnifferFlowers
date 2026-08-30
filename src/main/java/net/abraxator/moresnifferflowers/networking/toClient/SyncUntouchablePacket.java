package net.abraxator.moresnifferflowers.networking.toClient;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.MSFClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public record SyncUntouchablePacket() implements MSFClientPacket {
    public static CustomPacketPayload.Type<SyncUntouchablePacket> TYPE = new CustomPacketPayload.Type<>(MoreSnifferFlowers.loc("sync_untouchable"));
    public static final StreamCodec<FriendlyByteBuf, SyncUntouchablePacket> STREAM_CODEC = StreamCodec.of(
            (buf, pkt) -> {},
            buf -> new SyncUntouchablePacket());

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleClientPacket(Player player, Level level) {
        player.getData(MSFDataAttachments.UNTOUCHABLE).onAttacked();
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
