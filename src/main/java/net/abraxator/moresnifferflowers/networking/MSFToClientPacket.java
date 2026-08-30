package net.abraxator.moresnifferflowers.networking;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface MSFToClientPacket extends MSFPacket {
    @Override
    default void handle(IPayloadContext context) {
        context.enqueueWork(() -> handleClientPacket(context.player(), context.player().level()));
    }


    void handleClientPacket(Player player, Level level);
}


