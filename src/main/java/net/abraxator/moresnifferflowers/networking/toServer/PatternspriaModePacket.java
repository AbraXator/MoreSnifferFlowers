package net.abraxator.moresnifferflowers.networking.toServer;

import io.netty.buffer.ByteBuf;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.PatternspriaItem;
import net.abraxator.moresnifferflowers.networking.MSFPacket;
import net.abraxator.moresnifferflowers.networking.MSFToServerPacket;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record PatternspriaModePacket(int amount) implements MSFToServerPacket {
    public static final Type<PatternspriaModePacket> TYPE = MSFPacket.makeType("patternspria_mode", PatternspriaModePacket.class);
    public static final StreamCodec<ByteBuf, PatternspriaModePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, PatternspriaModePacket::amount,
            PatternspriaModePacket::new
    );

    @Override
    public void handlePacket(Player player, Level level) {
        var stack = player.getMainHandItem();
        if(stack.getItem() instanceof PatternspriaItem dyespriaItem) {
            dyespriaItem.changeMode((ServerPlayer) player, stack, amount);
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
