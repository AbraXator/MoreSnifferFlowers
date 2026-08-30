package net.abraxator.moresnifferflowers.networking.toClient;

import net.abraxator.moresnifferflowers.blockentities.BerootCauldronBlockEntity;
import net.abraxator.moresnifferflowers.networking.MSFPacket;
import net.abraxator.moresnifferflowers.networking.MSFToClientPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record SyncBerootCauldronPacket(BlockPos pos, BerootCauldronBlockEntity.Data data) implements MSFToClientPacket {
    public static final CustomPacketPayload.Type<SyncBerootCauldronPacket> TYPE = MSFPacket.makeType("sync_beroot_cauldron", SyncBerootCauldronPacket.class);

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBerootCauldronPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SyncBerootCauldronPacket::pos,
            BerootCauldronBlockEntity.DATA_STREAM_CODEC, SyncBerootCauldronPacket::data,
            SyncBerootCauldronPacket::new
    );

    @Override
    public void handleClientPacket(Player player, Level level) {
        if (level.getBlockEntity(pos) instanceof BerootCauldronBlockEntity cauldron) {
            cauldron.loadData(data);
        }
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
