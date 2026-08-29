package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface MSFParticles {
    DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MoreSnifferFlowers.MOD_ID);

    DeferredHolder<ParticleType<?>, SimpleParticleType> FLY = PARTICLES.register("fly", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> CARROT = PARTICLES.register("carrot", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> AMBUSH = PARTICLES.register("ambush", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> GIANT_CROP = PARTICLES.register("giant_crop", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> GARBUSH = PARTICLES.register("garbush", () -> new SimpleParticleType(false));

    DeferredHolder<ParticleType<?>, SimpleParticleType> BONDRIPIA_DRIP = PARTICLES.register("bondripia_drip", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> BONDRIPIA_FALL = PARTICLES.register("bondripia_fall", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> BONDRIPIA_LAND = PARTICLES.register("bondripia_land", () -> new SimpleParticleType(false));

    DeferredHolder<ParticleType<?>, SimpleParticleType> ACIDRIPIA_DRIP = PARTICLES.register("acidripia_drip", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> ACIDRIPIA_FALL = PARTICLES.register("acidripia_fall", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>, SimpleParticleType> ACIDRIPIA_LAND = PARTICLES.register("acidripia_land", () -> new SimpleParticleType(false));

    DeferredHolder<ParticleType<?>,SimpleParticleType> TORCHFLAME = PARTICLES.register("torchflame", () -> new SimpleParticleType(false));
    DeferredHolder<ParticleType<?>,SimpleParticleType> BUBBLE = PARTICLES.register("bubble", () -> new SimpleParticleType(false));


}
