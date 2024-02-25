package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, DefaultParticleType> FLY = PARTICLES.register("fly", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, DefaultParticleType> CARROT = PARTICLES.register("carrot", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, DefaultParticleType> AMBUSH = PARTICLES.register("ambush", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, DefaultParticleType> GIANT_CROP = PARTICLES.register("giant_crop", () -> new SimpleParticleType(false));
}
