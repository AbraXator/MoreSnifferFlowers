package net.abraxator.moresnifferflowers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class GiantCropParticle extends TextureSheetParticle {
    protected GiantCropParticle(ClientLevel level, double pX, double pY, double pZ) {
        super(level, pX, pY, pZ);
        this.scale(5);
        this.setLifetime(25);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel level, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            GiantCropParticle giantCropParticle = new GiantCropParticle(level, pX, pY, pZ);
            giantCropParticle.pickSprite(spriteSet);
            return giantCropParticle;
        }
    }
}
