package net.abraxator.moresnifferflowers.networking.toClient;

import io.netty.buffer.ByteBuf;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.SlipperyCapability;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.MSFPacket;
import net.abraxator.moresnifferflowers.networking.MSFToClientPacket;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record SyncSlipperyPacket(SlipperyCapability capability, int entityId) implements MSFToClientPacket {
    public static final CustomPacketPayload.Type<SyncSlipperyPacket> TYPE = MSFPacket.makeType("sync_slippery", SyncSlipperyPacket.class);
    public static final StreamCodec<ByteBuf, SyncSlipperyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(SlipperyCapability.CODEC), SyncSlipperyPacket::capability,
            ByteBufCodecs.INT, SyncSlipperyPacket::entityId,
            SyncSlipperyPacket::new
    );

    @Override
    public void handleClientPacket(Player player, Level level) {
        Entity entity = level.getEntity(entityId);

        if (entity instanceof LivingEntity livingEntity) {

            SlipperyCapability cap = livingEntity.getData(MSFDataAttachments.SLIPPERY);

            cap.isFallen = capability.isFallen;
            cap.fallenTicks = capability.fallenTicks;
            cap.maxFallenTicks = capability.maxFallenTicks;

            if (!cap.isFallen){
                cap.getUp(livingEntity);
            }
        }

    }


    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
