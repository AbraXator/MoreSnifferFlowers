package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final DefaultParticleType AMBUSH_PARTICLE = registerParticle("ambush_particle", FabricParticleTypes.simple());

    private static DefaultParticleType registerParticle(String name, DefaultParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(MoreSnifferFlowers.MOD_ID, name), particleType);
    }
    public static void registerParticles() {
        MoreSnifferFlowers.LOGGER.info("Registering Particles for" + MoreSnifferFlowers.MOD_ID);
    }
}
