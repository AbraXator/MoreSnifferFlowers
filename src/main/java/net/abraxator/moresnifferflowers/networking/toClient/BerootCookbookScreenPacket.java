package net.abraxator.moresnifferflowers.networking.toClient;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.client.gui.screen.cookbook.CookbookScreen;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.MSFClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public record BerootCookbookScreenPacket() implements MSFClientPacket {
    public static final CustomPacketPayload.Type<BerootCookbookScreenPacket> TYPE = new CustomPacketPayload.Type<>(MoreSnifferFlowers.loc("cauldron_screen"));
    public static final StreamCodec<FriendlyByteBuf, BerootCookbookScreenPacket> STREAM_CODEC = StreamCodec.of(
                    (buf, pkt) -> {},
                    buf -> new BerootCookbookScreenPacket());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleClientPacket(Player player, Level level) {
        Minecraft.getInstance().setScreen(new CookbookScreen(player.getData(MSFDataAttachments.NUTRITION).getItems()));
    }
}
