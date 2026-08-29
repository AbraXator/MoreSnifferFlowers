package net.abraxator.moresnifferflowers.networking.toClient;

import io.netty.buffer.ByteBuf;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFParticles;
import net.abraxator.moresnifferflowers.networking.MSFClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public record SaltemoneParticlePacket(Vector3f pos) implements MSFClientPacket {
    public static final CustomPacketPayload.Type<SaltemoneParticlePacket> TYPE = new CustomPacketPayload.Type<>(MoreSnifferFlowers.loc("saltemone_particle"));
    public static final StreamCodec<ByteBuf, SaltemoneParticlePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, SaltemoneParticlePacket::pos,
            SaltemoneParticlePacket::new
    );


    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleClientPacket(Player player, Level level) {
        RandomSource random = level.random;

        for (int i = 0; i < 5; i++) {
            Particle particle = Minecraft.getInstance().particleEngine.createParticle(MSFParticles.BUBBLE.get(),
                    pos.x + random.nextDouble() - 0.5, pos.y + random.nextDouble() - 0.5, pos.z + random.nextDouble() - 0.5, (random.nextDouble()  - 0.5 )/2, (random.nextDouble()  - 0.5 )*2, (random.nextDouble()  - 0.5 )/2);
            if (particle != null) {
                particle.scale(0.5F + random.nextFloat());
                particle.setLifetime(random.nextIntBetweenInclusive(15, 25));
            }
        }

    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
