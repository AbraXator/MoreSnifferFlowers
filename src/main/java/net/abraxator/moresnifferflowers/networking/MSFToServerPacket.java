package net.abraxator.moresnifferflowers.networking;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface MSFToServerPacket extends MSFPacket {
    @Override
    default void handle(IPayloadContext context){
       context.enqueueWork(() -> this.handlePacket(context.player(), context.player().level()));
   };

   void handlePacket(Player player, Level level);
}
