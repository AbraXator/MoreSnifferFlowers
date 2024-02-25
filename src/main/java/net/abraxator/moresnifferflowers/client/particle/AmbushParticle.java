package net.abraxator.moresnifferflowers.client.particle;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import javax.annotation.Nullable;

public class AmbushParticle extends AnimatedParticle {
    private final double xModifier;
    private final double zModifier;

    protected AmbushParticle(ClientWorld pLevel, double pX, double pY, double pZ, SpriteProvider pSprites) {
        super(pLevel, pX, pY, pZ, pSprites, -0.125F);
        this.scale(0.95F);
        this.setMaxAge(50);
        this.setSpriteForAge(pSprites);
        this.xModifier = pLevel.random.nextDouble();
        this.zModifier = pLevel.random.nextDouble();
    }

    @Override
    public void tick() {
        super.tick();
        this.x = x + MathHelper.sin((float) (world.getTime() * 0.5)) * xModifier;
        this.z = z + MathHelper.cos((float) (world.getTime() * 0.5)) * zModifier;
    }

    public static class Provider implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Provider(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType pType, ClientWorld pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new AmbushParticle(pLevel, pX, pY, pZ, sprites);
        }
    }
}
