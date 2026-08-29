package net.abraxator.moresnifferflowers.networking;

import net.abraxator.moresnifferflowers.networking.toClient.*;
import net.abraxator.moresnifferflowers.networking.toServer.BerootCauldronCraftPacket;
import net.abraxator.moresnifferflowers.networking.toServer.DyespriaModePacket;
import net.abraxator.moresnifferflowers.networking.toServer.PatternspriaModePacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPacketHandler {
    public ModPacketHandler(IEventBus modEventBus, int version) {
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            PayloadRegistrar registrar = event.registrar(String.valueOf(version));
            registerClientToServer(new ModPacketRegistrar(registrar, true));
            registerServerToClient(new ModPacketRegistrar(registrar, false));
        });
    }
    
    protected void registerClientToServer(ModPacketRegistrar registrar) {
        registrar.play(DyespriaModePacket.TYPE, DyespriaModePacket.STREAM_CODEC);
        registrar.play(PatternspriaModePacket.TYPE, PatternspriaModePacket.STREAM_CODEC);
        registrar.play(BerootCauldronCraftPacket.TYPE, BerootCauldronCraftPacket.STREAM_CODEC);
    }
    
    protected void registerServerToClient(ModPacketRegistrar registrar) {
        registrar.play(BerootCauldronSuckPacket.TYPE, BerootCauldronSuckPacket.STREAM_CODEC);
        registrar.play(BerootCookbookScreenPacket.TYPE, BerootCookbookScreenPacket.STREAM_CODEC);
        registrar.play(CorruptedSludgePacket.TYPE, CorruptedSludgePacket.STREAM_CODEC);
        registrar.play(CorruptionParticlePacket.TYPE, CorruptionParticlePacket.STREAM_CODEC);
        registrar.play(DyespriaDisplayModeChangePacket.TYPE, DyespriaDisplayModeChangePacket.STREAM_CODEC);
        registrar.play(SaltemoneParticlePacket.TYPE, SaltemoneParticlePacket.STREAM_CODEC);
        registrar.play(SyncGluedPacket.TYPE, SyncGluedPacket.STREAM_CODEC);
        registrar.play(SyncMouthSlotsPacket.TYPE, SyncMouthSlotsPacket.STREAM_CODEC);
        registrar.play(SyncSlipperyPacket.TYPE, SyncSlipperyPacket.STREAM_CODEC);
        registrar.play(SyncUntouchablePacket.TYPE, SyncUntouchablePacket.STREAM_CODEC);
    }

    public static ModPacketHandler register(IEventBus iEventBus, int version) {
        return new ModPacketHandler(iEventBus, version);
    } 
    
    protected record ModPacketRegistrar(PayloadRegistrar registrar, boolean toServer) {
        public <MSG extends MSFPacket> void play(CustomPacketPayload.Type<MSG> type, StreamCodec<? super RegistryFriendlyByteBuf, MSG> reader) {
            if (toServer) {
                registrar.playToServer(type, reader, MSFPacket::handle);
            } else {
                registrar.playToClient(type, reader, MSFPacket::handle);
            }
        }
    }
}
