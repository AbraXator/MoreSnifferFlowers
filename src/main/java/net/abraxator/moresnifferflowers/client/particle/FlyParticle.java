package net.abraxator.moresnifferflowers.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class FlyParticle extends SpriteBillboardParticle {
    protected FlyParticle(ClientWorld pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        this.setBoundingBoxSpacing(0.1F, 0.1F);
        this.gravityStrength = 0.005F;
        this.maxAge = 300 + random.nextInt(40);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteSet;

        public Provider(SpriteProvider spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            FlyParticle flyParticle = new FlyParticle(pLevel, pX, pY, pZ);
            flyParticle.setSprite(spriteSet);
            return flyParticle;
        }
    }
}
