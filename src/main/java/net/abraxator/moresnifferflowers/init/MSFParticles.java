package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface MSFParticles {
    DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MoreSnifferFlowers.MOD_ID);

    Supplier<SimpleParticleType> AMBUSH = PARTICLES.register("ambush", () -> new SimpleParticleType(false));
    Supplier<SimpleParticleType> GIANT_CROP = PARTICLES.register("giant_crop", () -> new SimpleParticleType(false));
    Supplier<SimpleParticleType> GARBUSH = PARTICLES.register("garbush", () -> new SimpleParticleType(false));

    Supplier<SimpleParticleType> BONDRIPIA_DRIP = PARTICLES.register("bondripia_drip", () -> new SimpleParticleType(false));
    Supplier<SimpleParticleType> BONDRIPIA_FALL = PARTICLES.register("bondripia_fall", () -> new SimpleParticleType(false));
    Supplier<SimpleParticleType> BONDRIPIA_LAND = PARTICLES.register("bondripia_land", () -> new SimpleParticleType(false));

    Supplier<SimpleParticleType> ACIDRIPIA_DRIP = PARTICLES.register("acidripia_drip", () -> new SimpleParticleType(false));
    Supplier<SimpleParticleType> ACIDRIPIA_FALL = PARTICLES.register("acidripia_fall", () -> new SimpleParticleType(false));
    Supplier<SimpleParticleType> ACIDRIPIA_LAND = PARTICLES.register("acidripia_land", () -> new SimpleParticleType(false));

    Supplier<SimpleParticleType> TORCHFLAME = PARTICLES.register("torchflame", () -> new SimpleParticleType(false));
    Supplier<SimpleParticleType> BUBBLE = PARTICLES.register("bubble", () -> new SimpleParticleType(false));


}
