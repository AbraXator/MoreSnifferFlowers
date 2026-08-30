package net.abraxator.moresnifferflowers.networking;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.networking.toClient.SyncUntouchablePacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public interface MSFPacket extends CustomPacketPayload {
    void handle(IPayloadContext context);

    //class is used in 1.20.1
    static <T extends CustomPacketPayload> Type<T> makeType(String name, Class<T> clazz) {
        return new CustomPacketPayload.Type<>(MoreSnifferFlowers.loc(name));
    }
}
