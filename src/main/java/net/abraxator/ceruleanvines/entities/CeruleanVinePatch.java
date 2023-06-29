package net.abraxator.ceruleanvines.entities;

import net.abraxator.ceruleanvines.init.ModBlocks;
import net.abraxator.ceruleanvines.init.ModEntities;
import net.abraxator.ceruleanvines.init.ModItems;
import net.abraxator.ceruleanvines.init.ModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

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
}
