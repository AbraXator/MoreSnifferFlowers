package net.abraxator.moresnifferflowers.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class GiantCropParticle extends SpriteBillboardParticle {
    protected GiantCropParticle(ClientWorld pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
        this.scale(15);
        this.setMaxAge(50);
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
            GiantCropParticle giantCropParticle = new GiantCropParticle(pLevel, pX, pY, pZ);
            giantCropParticle.setSprite(spriteSet);
            return giantCropParticle;
        }
    }
}
