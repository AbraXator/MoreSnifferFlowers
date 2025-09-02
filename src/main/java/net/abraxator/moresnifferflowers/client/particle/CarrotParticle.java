package net.abraxator.moresnifferflowers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nullable;

public class CarrotParticle extends SimpleAnimatedParticle {
    protected CarrotParticle(ClientLevel level, double pX, double pY, double pZ, SpriteSet pSprites) {
        super(level, pX, pY, pZ, pSprites, 0);
        this.scale(2);
        this.setLifetime(200);
        this.setSpriteFromAge(pSprites);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel level, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CarrotParticle(level, pX, pY, pZ, sprites);
        }
    }
}
