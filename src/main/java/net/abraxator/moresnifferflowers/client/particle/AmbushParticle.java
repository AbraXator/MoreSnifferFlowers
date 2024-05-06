package net.abraxator.moresnifferflowers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class AmbushParticle extends SimpleAnimatedParticle {
    private final double xModifier;
    private final double zModifier;

    protected AmbushParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pSprites, -0.125F);
        this.scale(0.95F);
        this.setLifetime(30);
        this.setSpriteFromAge(pSprites);
        this.xModifier = pLevel.random.nextDouble();
        this.zModifier = pLevel.random.nextDouble();
    }

    @Override
    public void tick() {
        super.tick();
        this.x = x + Mth.sin((float) (level.getGameTime() * 0.5)) * xModifier;
        this.z = z + Mth.cos((float) (level.getGameTime() * 0.5)) * zModifier;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AmbushParticle(pLevel, pX, pY, pZ, sprites);
        }
    }
}
