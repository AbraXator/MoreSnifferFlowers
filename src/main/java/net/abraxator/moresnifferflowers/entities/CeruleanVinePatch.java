package net.abraxator.moresnifferflowers.entities;

/*
public class CeruleanVinePatch extends ThrowableItemProjectile {
    public CeruleanVinePatch(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public CeruleanVinePatch(LivingEntity pShooter, Level pLevel) {
        super(ModEntities.CERULEAN_VINE_PATCH.get(), pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CERULEAN_VINE_PATCH.get();
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if(pId == 3){
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if(entity instanceof LivingEntity livingEntity){
            livingEntity.addEffect(new MobEffectInstance(ModMobEffects.CERULEANLY_VINED.get(), 60));
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if(!this.level().isClientSide){
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }
}*/
