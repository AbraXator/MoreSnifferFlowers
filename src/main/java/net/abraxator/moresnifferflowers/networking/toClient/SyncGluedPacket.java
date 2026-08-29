package net.abraxator.moresnifferflowers.networking.toClient;

import io.netty.buffer.ByteBuf;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.GluedCapability;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.MSFClientPacket;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public record SyncGluedPacket(boolean isGlued, int entityId) implements MSFClientPacket {
    public static final CustomPacketPayload.Type<SyncGluedPacket> TYPE = new CustomPacketPayload.Type<>(MoreSnifferFlowers.loc("sync_glued"));
    public static final StreamCodec<ByteBuf, SyncGluedPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, SyncGluedPacket::isGlued,
            ByteBufCodecs.INT, SyncGluedPacket::entityId,
            SyncGluedPacket::new
    );

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleClientPacket(Player player, Level level) {
        Entity entity = level.getEntity(entityId);

        if (entity instanceof LivingEntity living) {
            GluedCapability.playSound(level, entity);
            living.getData(MSFDataAttachments.GLUED).isGlued = isGlued;
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
