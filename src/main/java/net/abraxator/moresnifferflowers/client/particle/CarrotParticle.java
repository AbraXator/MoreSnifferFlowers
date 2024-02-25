package net.abraxator.moresnifferflowers.client.particle;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import javax.annotation.Nullable;

public class CarrotParticle extends AnimatedParticle {
    protected CarrotParticle(ClientWorld pLevel, double pX, double pY, double pZ, SpriteProvider pSprites) {
        super(pLevel, pX, pY, pZ, pSprites, 0);
        this.scale(1.5F);
        this.setMaxAge(20);
        this.setSpriteForAge(pSprites);
    }

    public static class Provider implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Provider(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new CarrotParticle(pLevel, pX, pY, pZ, sprites);
        }
    }
}
