package net.abraxator.moresnifferflowers.networking.toClient;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.HardenedMouthCapability;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.MSFClientPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public record SyncMouthSlotsPacket(HardenedMouthCapability capability) implements MSFClientPacket {
    public static final CustomPacketPayload.Type<SyncMouthSlotsPacket> TYPE = new CustomPacketPayload.Type<>(MoreSnifferFlowers.loc("sync_mouth"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncMouthSlotsPacket> STREAM_CODEC = StreamCodec.composite(
            HardenedMouthCapability.STREAM_CODEC, SyncMouthSlotsPacket::capability,
            SyncMouthSlotsPacket::new
    );

    @Override
    public void handleClientPacket(Player player, Level level) {
        HardenedMouthCapability cap = player.getData(MSFDataAttachments.HARDENED_MOUTH);

        cap.setAllItems(capability.getMouthSlotItems());
        cap.setCooldown(capability.getCooldown());

    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
