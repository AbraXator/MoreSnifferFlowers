package net.abraxator.moresnifferflowers.networking;

import net.abraxator.moresnifferflowers.networking.toClient.*;
import net.abraxator.moresnifferflowers.networking.toServer.DyespriaModePacket;
import net.abraxator.moresnifferflowers.networking.toServer.PatternspriaModePacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class MSFNetworking {
    public MSFNetworking(IEventBus modEventBus, int version) {
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            PayloadRegistrar registrar = event.registrar(String.valueOf(version));
            registerClientToServer(new ModPacketRegistrar(registrar, true));
            registerServerToClient(new ModPacketRegistrar(registrar, false));
        });
    }
    
    protected void registerClientToServer(ModPacketRegistrar registrar) {
        registrar.play(DyespriaModePacket.TYPE, DyespriaModePacket.STREAM_CODEC);
        registrar.play(PatternspriaModePacket.TYPE, PatternspriaModePacket.STREAM_CODEC);
    }
    
    protected void registerServerToClient(ModPacketRegistrar registrar) {
        registrar.play(CorruptedSludgeParticlePacket.TYPE, CorruptedSludgeParticlePacket.STREAM_CODEC);
        registrar.play(CorruptionParticlePacket.TYPE, CorruptionParticlePacket.STREAM_CODEC);
        registrar.play(SaltemoneParticlePacket.TYPE, SaltemoneParticlePacket.STREAM_CODEC);
        registrar.play(SyncSlipperyPacket.TYPE, SyncSlipperyPacket.STREAM_CODEC);
        registrar.play(SyncUntouchablePacket.TYPE, SyncUntouchablePacket.STREAM_CODEC);
        registrar.play(RebuildChunkSectionPacket.TYPE, RebuildChunkSectionPacket.STREAM_CODEC);
        registrar.play(SyncBerootCauldronPacket.TYPE, SyncBerootCauldronPacket.STREAM_CODEC);

    }

    public static MSFNetworking register(IEventBus iEventBus, int version) {
        return new MSFNetworking(iEventBus, version);
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
