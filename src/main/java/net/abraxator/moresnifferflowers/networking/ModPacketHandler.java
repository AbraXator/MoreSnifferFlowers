package net.abraxator.moresnifferflowers.networking;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPacketHandler {
    private static final String VERSION = "1";

    SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(MoreSnifferFlowers.loc("channel"))
            .networkProtocolVersion(() -> VERSION)
            .clientAcceptedVersions(VERSION::equals)
            .serverAcceptedVersions(VERSION::equals)
            .simpleChannel();

    public static void register(){
        int id = 0;

        MoreSnifferFlowers.LOGGER.info("{} registered {} packets", MoreSnifferFlowers.MOD_ID, id);
    }
}
