package net.abraxator.moresnifferflowers.client.particle;


import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class AmbushParticle extends AnimatedParticle {
    private final double xModifier;
    private final double zModifier;

    public AmbushParticle(ClientWorld pLevel, double pX, double pY, double pZ, SpriteProvider pSprites) {
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

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteProvider) {
            this.sprites = spriteProvider;
        }
        public Particle createParticle(DefaultParticleType particleType, ClientWorld clientWorld, double x, double y,
                                       double z, double xd, double yd, double zd){
            return new AmbushParticle(clientWorld, x, y, z, this.sprites);
        }
    }
}
